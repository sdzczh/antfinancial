package com.ant.app.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntSystemParams;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.Order;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;

/**
 * @描述 公用接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-17
 */
@SuppressWarnings("unchecked")
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	BaseDaoI dao;

	public User getUserByUserId(String id) {
		User user = dao.getById(User.class, Integer.parseInt(id));
		return user;
	}

	public Account getAccountByUserId(String id) {
		String hql = "from Account where userId=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", Integer.parseInt(id));
		Account account = dao.findUnique(hql, params);
		return account;
	}

	public Map<String, Object> getUserInfoByUserId(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("tu.id loginUserId,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
		sql.append("FROM t_user tu ");
		sql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
		sql.append("WHERE ");
		sql.append("tu.id = :id ");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<?> users = dao.findBySql(sql.toString(), params);
		if (users.size() == 0) {
			return null;
		} else {
			Map<String, Object> user = (Map<String, Object>) users.get(0);
			return user;
		}
	}

	public int countActiveUsers(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(tu.id) FROM t_user tu ");
		sql.append("LEFT JOIN ");
		sql.append("t_account ta ON tu.id = ta.userId ");
		sql.append("WHERE ");
		sql.append("tu.referenceId = :referenceId ");
		sql.append("AND ta.state >:state ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("referenceId", id);
		params.put("state", 0);
		BigInteger count = dao.countBySql(sql.toString(), params);
		return count.intValue();
	}

	public int getPackageNumByUserId(Integer userId) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Account ");
		hql.append("where userId=:userId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);

		Account account = (Account) dao.findUnique(hql.toString(), params);
		if (account == null) {
			return 0;
		}
		return account.getPackageNum();
	}

	public SystemParam getValByKey(String key) {
		String hql = "from SystemParam where keyName=:keyName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyName", key);
		SystemParam sysParam = dao.findUnique(hql, params);
		return sysParam;
	}

	public String getValStrByKey(String key) {
		String hql = "from SystemParam where keyName=:keyName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyName", key);
		SystemParam sysParam = dao.findUnique(hql, params);
		return sysParam.getVal();
	}

	public boolean checkOrderCode(String orderCode) {
		String hql = "from Order where orderCode = :orderCode";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		Order order = dao.findUnique(hql, params);
		if (order == null) {
			return true;
		} else {
			return false;
		}
	}

	public AntResult checkUserState(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = json.getString("userId");
			if (StrUtils.checkNull(userId)) {
				antResult.setType(AntType.ANT_204);
			}else{
				User user = getUserByUserId(userId);
				if (user == null) {
					antResult.setType(AntType.ANT_208);
				}
				
				if (user.getIsDel() == 1) {
					antResult.setType(AntType.ANT_305);
				}
				
				Account account = getAccountByUserId(userId);
				
				if (account == null) {
					antResult.setType(AntType.ANT_208);
				}
				
				if (account.getState() == 0) {
					antResult.setType(AntType.ANT_300);
				}
				
				if (account.getState() == 1) {
					antResult.setType(AntType.ANT_102);
				}
			}
			return antResult;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean checkMaxAmount(Integer type, Integer userId) {
		Double maxAmount = 0d;
		Double userMax = 0d;

		if(type==0){
			maxAmount = Double.parseDouble(getValStrByKey(AntSystemParams.MAX_TRANSATION_AMOUNT_BUY));
		}else{
			maxAmount = Double.parseDouble(getValStrByKey(AntSystemParams.MAX_TRANSATION_AMOUNT_SALE));
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("IFNULL(SUM(tor.amount),0)amount ");
		sql.append("FROM t_order tor ");
		sql.append("WHERE tor.userId = :userId ");
		sql.append("AND tor.type = :type ");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("type", type);
		
		List<?>results = dao.findBySql(sql.toString(), params);
		if(results.size()>0){
			Map<String,Object> maxInfo = (Map<String, Object>) results.get(0);
			userMax = Double.parseDouble(maxInfo.get("amount").toString());
		}
		
		if(userMax>=maxAmount){
			return true;
		}
		return false;
	}

	/**
	 * @描述 检测当前用户收益状态信息<br>
	 * @param id 用户ID
	 * @return map down:距离下次下降天数  profit:当前收益指数 after:将要下降指数
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-17
	 */
	public Map<String,Object> checkUserProfit(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("ta.activeTime ");
		sql.append("FROM ");
		sql.append("t_user tu ");
		sql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
		sql.append("WHERE tu.referenceId = :id ");
		sql.append("AND ta.state != :state ");
		sql.append("ORDER BY ta.activeTime DESC");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", 0);
		List<?> resutls = dao.findBySql(sql.toString(), params, 0, 1);
		int day = 0;
		String dateStr =null;
		if(resutls.size()!=0){
			Map<String,String> result = (Map<String, String>) resutls.get(0);
			dateStr = result.get("activeTime").toString();
		}else{
			Account account = getAccountByUserId(id);
			dateStr = account.getActiveTime();
		}
		day = DateUtils.daysBetween(dateStr);
		Integer count = Integer.parseInt(getValStrByKey(AntSystemParams.ATTENUATION));
		return MatchUtils.checkProfit(day,count.intValue());
	} 
	
	public AntResult getContextParams(AntResult antResult,String id) {
		String userId = null;
		if(id==null){
			userId = WebUtils.getRequest().getHeader("userId");
		}else{
			userId = id;
		}
		
		String userUpdateState = getValStrByKey(AntSystemParams.USERUPDATE);//用户信息修改开关 0:开 1:关
		User user = getUserByUserId(userId);
		Map<String,Object> contextParams = new HashMap<String, Object>();
		contextParams.put("userUpdateState", userUpdateState);
		contextParams.put("loginState", user.getLoginState());
		JSONObject contextParamJSON = (JSONObject) JSON.toJSON(contextParams);
		antResult.setContextParam(contextParamJSON);
		return antResult;
	}

	@Override
	public AntResult getContextParamsLogin(AntResult antResult, String id) {
		String userId = null;
		if(id==null){
			userId = WebUtils.getRequest().getHeader("userId");
		}else{
			userId = id;
		}
		
		String userUpdateState = getValStrByKey(AntSystemParams.USERUPDATE);//用户信息修改开关 0:开 1:关
		User user = getUserByUserId(userId);
		Account account = getAccountByUserId(userId);
		Integer packageNum = account.getPackageNum();/*增值包数*/
		double currentProfit = MatchUtils.doCalculationB(String.valueOf(packageNum), "10", "(1+1+0.1*(n-1))*(n/2)*(y*0.01)");/*当前收益指数*/
		Integer packageTopNum = Integer.parseInt(getValStrByKey(AntSystemParams.PACKAGE_TOP_NUM));
		double afterProfit = 0d;
		if(packageNum<packageTopNum){
			afterProfit = MatchUtils.doCalculationB(String.valueOf(packageNum+1),"10", "(1+1+0.1*(n-1))*(n/2)*(y*0.01)");/*下一个增值包收益*/
			afterProfit = MatchUtils.subtract(afterProfit, currentProfit);
		}
		Map<String,Object> map = checkUserProfit(userId);
		String profit = map.get("profit").toString();/*当前收益比例*/
		String days = map.get("down").toString();/*距离下次下降天数*/
		String after = map.get("after").toString();/*将要下降指数*/
		
		Map<String,Object> contextParams = new HashMap<String, Object>();
		contextParams.put("userUpdateState", userUpdateState);
		contextParams.put("currentProfit", currentProfit);
		contextParams.put("afterProfit", afterProfit);
		contextParams.put("profit", profit);
		contextParams.put("days", days);
		contextParams.put("after", after);
		contextParams.put("id", user.getId());
		contextParams.put("pid", user.getReferenceId());
		contextParams.put("userAccount", user.getUserAccount());
		contextParams.put("userName", user.getUserName());
		contextParams.put("userCode", user.getUserCode());
		contextParams.put("loginState", user.getLoginState());
		contextParams.put("packageJ", (new BigDecimal(account.getPackageJ()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageD", (new BigDecimal(account.getPackageD()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageZ", (new BigDecimal(account.getPackageZ()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageNum", account.getPackageNum());
		contextParams.put("alipayNumber", account.getAlipayNumber());
		contextParams.put("bankName", account.getBankName());
		contextParams.put("bankNumber", account.getBankNumber());
		JSONObject contextParamJSON = (JSONObject) JSON.toJSON(contextParams);
		antResult.setContextParam(contextParamJSON);
		return antResult;
	}

	public AntResult getContextParamsAndPackage(AntResult antResult, Account account, User user) {
		String userUpdateState = getValStrByKey(AntSystemParams.USERUPDATE);//用户信息修改开关 0:开 1:关
		Map<String,Object> contextParams = new HashMap<String, Object>();
		contextParams.put("userUpdateState", userUpdateState);
		contextParams.put("loginState", user.getLoginState());
		contextParams.put("packageJ", (new BigDecimal(account.getPackageJ()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageD", (new BigDecimal(account.getPackageD()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageZ", (new BigDecimal(account.getPackageZ()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageNum", account.getPackageNum());
		JSONObject contextParamJSON = (JSONObject) JSON.toJSON(contextParams);
		antResult.setContextParam(contextParamJSON);
		return antResult;
	}
	
	public AntResult getContextParamsAndPackageInfo(AntResult antResult, Account account, User user) {
		String userUpdateState = getValStrByKey(AntSystemParams.USERUPDATE);//用户信息修改开关 0:开 1:关

		Integer packageNum = account.getPackageNum();/*增值包数*/
		double currentProfit = MatchUtils.doCalculationB(String.valueOf(packageNum), "10", "(1+1+0.1*(n-1))*(n/2)*(y*0.01)");/*当前收益指数*/
		Integer packageTopNum = Integer.parseInt(getValStrByKey(AntSystemParams.PACKAGE_TOP_NUM));
		double afterProfit = 0d;
		if(packageNum<packageTopNum){
			afterProfit = MatchUtils.doCalculationB(String.valueOf(packageNum+1),"10", "(1+1+0.1*(n-1))*(n/2)*(y*0.01)");/*下一个增值包收益*/
			afterProfit = MatchUtils.subtract(afterProfit, currentProfit);
		}
		
		Map<String,Object> contextParams = new HashMap<String, Object>();
		contextParams.put("userUpdateState", userUpdateState);
		contextParams.put("loginState", user.getLoginState());
		contextParams.put("packageJ", (new BigDecimal(account.getPackageJ()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageD", (new BigDecimal(account.getPackageD()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageZ", (new BigDecimal(account.getPackageZ()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
		contextParams.put("packageNum", account.getPackageNum());
		contextParams.put("currentProfit", currentProfit);
		contextParams.put("afterProfit", afterProfit);
		JSONObject contextParamJSON = (JSONObject) JSON.toJSON(contextParams);
		antResult.setContextParam(contextParamJSON);
		return antResult;
	}

	public AntResult getContextParamsAndExtendsion(AntResult antResult,User user) {
		String userUpdateState = getValStrByKey(AntSystemParams.USERUPDATE);//用户信息修改开关 0:开 1:关
		
		Map<String,Object> map = checkUserProfit(user.getId().toString());
		String profit = map.get("profit").toString();/*当前收益比例*/
		String days = map.get("down").toString();/*距离下次下降天数*/
		String after = map.get("after").toString();/*将要下降指数*/
		
		Map<String,Object> contextParams = new HashMap<String, Object>();
		contextParams.put("userUpdateState", userUpdateState);
		contextParams.put("loginState", user.getLoginState());
		contextParams.put("profit", profit);
		contextParams.put("days", days);
		contextParams.put("after", after);
		JSONObject contextParamJSON = (JSONObject) JSON.toJSON(contextParams);
		antResult.setContextParam(contextParamJSON);
		return antResult;
	}

	public AntResult checkUserLoginState(AntResult antResult,User user) {
		String loginState = WebUtils.getRequest().getHeader("loginState");
		if(loginState!=null && !"".equals(loginState.trim())){
			String loginStr = user.getLoginState();
			if(loginState.equals(loginStr)){
				antResult.setType(AntType.ANT_100);
			}else{
				antResult.setType(AntType.ANT_310);
			}
		}else{
			antResult.setType(AntType.ANT_204);
		}
		return antResult;
	}
}

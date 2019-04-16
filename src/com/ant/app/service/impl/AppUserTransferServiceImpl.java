package com.ant.app.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppUserTransferService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.User;
import com.ant.util.DateUtils;

/**
 * @描述 APP用户钱包转账接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
@Transactional
@SuppressWarnings("unchecked")
@Service
public class AppUserTransferServiceImpl implements AppUserTransferService{

	@Autowired
	private BaseDaoI dao;
	@Autowired
	private CommonService commonService;

	/**
	 * @描述 J、D钱包转Z钱包<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public String updatePackage(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String id = json.getString("id");
			String capitalStr = json.getString("capital");
			String typeStr = json.getString("type");
			if(id==null || "".equals(id.trim())|| capitalStr==null || "".equals(capitalStr.trim()) || typeStr==null || "".equals(typeStr.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = commonService.getUserByUserId(id);
			antResult = commonService.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			BigDecimal capital =new BigDecimal(capitalStr).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			
			//根据条件查询个人钱包信息
			StringBuffer getAccountSql = new StringBuffer();
			getAccountSql.append("SELECT ");
			getAccountSql.append("packageJ,packageD,packageZ ");
			getAccountSql.append("FROM t_account ");
			getAccountSql.append("WHERE ");
			getAccountSql.append("userId = :userId ");
			
			Map<String,Object> getuserHqlParams = new HashMap<String, Object>();
			getuserHqlParams.put("userId", id);
			
			//修改钱包
			Account updateAccount = commonService.getAccountByUserId(String.valueOf(id));
			
			//根据传参，判断
			BigDecimal packageZ = new BigDecimal(updateAccount.getPackageZ()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			Integer type = Integer.parseInt(typeStr);
			
			if(type==1){//J钱包>Z钱包
				BigDecimal packageJ = new BigDecimal(updateAccount.getPackageJ()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
				if(packageJ.doubleValue()<capital.doubleValue()){
					antResult.setType(AntType.ANT_106);
					return AntResponse.response(antResult);
				}
				
				updateAccount.setPackageJ(packageJ.subtract(capital).doubleValue());
				updateAccount.setPackageZ(packageZ.add(capital).doubleValue());
				
				//添加J钱包记录数据
				CapitalFlow capitalFlow = new CapitalFlow();
				capitalFlow.setUserId(Integer.valueOf(id));//用户ID
				capitalFlow.setCreateDateTime(DateUtils.getCurrentTimeStr());//创建时间
				capitalFlow.setPackageType(0);//钱包类型 0:J钱包 1:D钱包 2:Z钱包
				capitalFlow.setType(7);//类型 0:静态收益 1:动态收益 2:签到奖励 3:推广奖励 4:买入 5:卖出 6:激活用户 7:转账(J>Z) 8:转账(D>Z) 9:开启增值包
				capitalFlow.setAmount(capital.doubleValue());//流动金额
				dao.save(capitalFlow);
				
				CapitalFlow capitalFlow1 = new CapitalFlow();
				//添加Z钱包记录数据
				capitalFlow1.setUserId(Integer.valueOf(id));//用户ID
				capitalFlow1.setCreateDateTime(DateUtils.getCurrentTimeStr());//创建时间
				capitalFlow1.setPackageType(2);//钱包类型 0:J钱包 1:D钱包 2:Z钱包
				capitalFlow1.setType(7);//类型 0:静态收益 1:动态收益 2:签到奖励 3:推广奖励 4:买入 5:卖出 6:激活用户 7:转账(J>Z) 8:转账(D>Z) 9:开启增值包
				capitalFlow1.setAmount(capital.doubleValue());//流动金额
				dao.save(capitalFlow1);
			}
			if(type==2){//D钱包>Z钱包
				BigDecimal packageD = new BigDecimal(updateAccount.getPackageD()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
				if(packageD.doubleValue()<capital.doubleValue()){
					antResult.setType(AntType.ANT_106);
					return AntResponse.response(antResult);
				}
				
				updateAccount.setPackageD(packageD.subtract(capital).doubleValue());
				updateAccount.setPackageZ(packageZ.add(capital).doubleValue());
				
				//添加D钱包记录数据
				CapitalFlow capitalFlow = new CapitalFlow();
				capitalFlow.setUserId(Integer.valueOf(id));//用户ID
				capitalFlow.setCreateDateTime(DateUtils.getCurrentTimeStr());//创建时间
				capitalFlow.setPackageType(1);//钱包类型 0:J钱包 1:D钱包 2:Z钱包
				capitalFlow.setType(8);//类型 0:静态收益 1:动态收益 2:签到奖励 3:推广奖励 4:买入 5:卖出 6:激活用户 7:转账(J>Z) 8:转账(D>Z) 9:开启增值包
				capitalFlow.setAmount(capital.doubleValue());//流动金额
				dao.save(capitalFlow);
				
				//添加Z钱包记录数据
				CapitalFlow capitalFlow1 = new CapitalFlow();
				capitalFlow1.setUserId(Integer.valueOf(id));//用户ID
				capitalFlow1.setCreateDateTime(DateUtils.getCurrentTimeStr());//创建时间
				capitalFlow1.setPackageType(2);//钱包类型 0:J钱包 1:D钱包 2:Z钱包
				capitalFlow1.setType(8);//类型 0:静态收益 1:动态收益 2:签到奖励 3:推广奖励 4:买入 5:卖出 6:激活用户 7:转账(J>Z) 8:转账(D>Z) 9:开启增值包
				capitalFlow1.setAmount(capital.doubleValue());//流动金额
				dao.save(capitalFlow1);
			}
			Account account = commonService.getAccountByUserId(id);
			dao.update(updateAccount);
			antResult = commonService.getContextParamsAndPackage(antResult,account,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @描述 J、D、Z钱包金币流水记录<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public String getPackageRecord(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			
			String userId = json.getString("userId");
			if(userId==null || "".equals(userId.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User loginuser = commonService.getUserByUserId(userId);
			antResult = commonService.checkUserLoginState(antResult, loginuser);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer packageType = json.getInteger("packageType");
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			StringBuffer getCapitalFlowSql = new StringBuffer();
			getCapitalFlowSql.append("SELECT ");
			getCapitalFlowSql.append("* ");
			getCapitalFlowSql.append("FROM t_capital_flow ");
			getCapitalFlowSql.append("WHERE ");
			getCapitalFlowSql.append("userId = :userId and packageType = :packageType and type>:type ");
			getCapitalFlowSql.append("ORDER BY createDateTime DESC");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("packageType", packageType);
			params.put("type", 3);
			List<?> flows = dao.findBySql(getCapitalFlowSql.toString(), params, page, rows);
			if(flows.size()>0){
				for(int i=0;i<flows.size();i++){
					Map<String,Object> map = (Map<String, Object>) flows.get(i);
					map.put("orderNum", page*rows+i+1);
					String money = map.get("amount").toString();
					BigDecimal bd = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN);
					map.put("amount", bd.doubleValue());
					Integer type = Integer.parseInt(String.valueOf(map.get("type")));
						
					if(type==4){//买入
							
					  //获取买入帐号：buyUserId，
					  if(map.get("buyUserId")!=null){
								
					     if(!"".equals(map.get("buyUserId").toString().trim())){
									
							User user = commonService.getUserByUserId(map.get("buyUserId").toString());
							if(user!=null){
								map.put("buyUserAccount", user.getUserAccount());
								map.put("buyUserName", user.getUserName());
							}else{
								 map.put("buyUserAccount", "");
									map.put("buyUserName","");
							}
						  }
					   }else {
						
						    map.put("buyUserAccount", "");
							map.put("buyUserName","");
					   }
					}else if (type == 5) {//卖出
						
						if(map.get("fee") != null){
							String fee = map.get("fee").toString();
							BigDecimal feebd = new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_DOWN);
							map.put("fee", feebd.doubleValue());
						}else {
							map.put("fee", "0");
						}
					}else if (type == 6) {//激活帐号
							
						if(map.get("activeUserId")!=null){
								
							if(!"".equals(map.get("activeUserId").toString().trim())){
								User user = commonService.getUserByUserId(map.get("activeUserId").toString());
								if(user!=null){
									map.put("activeUserAccount", user.getUserAccount());
									map.put("activeUserName", user.getUserName());
								}else{
									map.put("activeUserAccount", "");
									map.put("activeUserName", "");
								}
							}
						}else {
							
							map.put("activeUserAccount", "");
							map.put("activeUserName", "");
						}
					}	
								
				}
			}
			
			String countSql = "SELECT COUNT(userId) FROM ("+getCapitalFlowSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);

			JSONArray arrayJson = (JSONArray) JSON.toJSON(flows);
			antResult.setData(arrayJson);
			antResult.setCount(count.intValue());
			antResult = commonService.getContextParams(antResult, null);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * @描述 J钱包静态收益<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public String getStaticProfit(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = json.getString("userId");
			if(userId==null || "".equals(userId.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User loginuser = commonService.getUserByUserId(userId);
			antResult = commonService.checkUserLoginState(antResult, loginuser);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			StringBuffer getSql = new StringBuffer();
			getSql.append("SELECT ");
			getSql.append("* ");
			getSql.append("FROM t_static_profit ");
			getSql.append("WHERE ");
			getSql.append("userId = :userId AND amount>0 ");
			getSql.append("ORDER BY createDate DESC");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			List<?> listData = dao.findBySql(getSql.toString(), params, page, rows);
			if(listData.size()>0){
				for(int i=0;i<listData.size();i++){
					Map<String,Object> map = (Map<String, Object>) listData.get(i);
					String money = map.get("amount").toString();
					BigDecimal bd = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN);
					map.put("amount", bd.doubleValue());
					map.put("orderNum", page*rows+i+1);
				}
			}
			
			String countSql = "SELECT COUNT(userId) FROM ("+getSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);
			
			JSONArray arrayJson = (JSONArray) JSON.toJSON(listData);
			antResult.setData(arrayJson);
			antResult.setCount(count.intValue());
			antResult = commonService.getContextParams(antResult, null);
			return AntResponse.response(antResult);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @描述 D钱包推广收益<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public String getPromotionAward(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = json.getString("userId");
			if(userId==null || "".equals(userId.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User loginuser = commonService.getUserByUserId(userId);
			antResult = commonService.checkUserLoginState(antResult, loginuser);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			StringBuffer getSql = new StringBuffer();
			getSql.append("SELECT ");
			getSql.append("* ");
			getSql.append("FROM t_promotion_award ");
			getSql.append("WHERE ");
			getSql.append("userId = :userId  AND amount>0 ");
			getSql.append("ORDER BY createDate DESC");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			List<?> listData = dao.findBySql(getSql.toString(), params, page, rows);
			if(listData.size()>0){
				for(int i=0;i<listData.size();i++){
					Map<String,Object> map = (Map<String, Object>) listData.get(i);
					String money = map.get("amount").toString();
					BigDecimal bd = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN);
					map.put("amount", bd.doubleValue());
					map.put("orderNum", page*rows+i+1);
				}
			}
			
			String countSql = "SELECT COUNT(userId) FROM ("+getSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);
			
			JSONArray arrayJson = (JSONArray) JSON.toJSON(listData);
			antResult.setData(arrayJson);
			antResult.setCount(count.intValue());
			antResult = commonService.getContextParams(antResult, null);
			return AntResponse.response(antResult);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @描述 Z钱包奖励金币<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public String getGoldAward(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = json.getString("userId");
			if(userId==null || "".equals(userId.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User loginuser = commonService.getUserByUserId(userId);
			antResult = commonService.checkUserLoginState(antResult, loginuser);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			
			StringBuffer getSql = new StringBuffer();
			getSql.append("SELECT ");
			getSql.append("* ");
			getSql.append("FROM t_gold_award ");
			getSql.append("WHERE ");
			getSql.append("userId = :userId  AND amount>0 ");
			getSql.append("ORDER BY createDateTime DESC");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", Integer.parseInt(userId));
			
			List<?> listData = dao.findBySql(getSql.toString(), params, page, rows);
			if(listData.size()>0){
				for(int i=0;i<listData.size();i++){
					Map<String,Object> map = (Map<String, Object>) listData.get(i);
					map.put("orderNum", page*rows+i+1);
					String money = map.get("amount").toString();
					BigDecimal bd = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN);
					map.put("amount", bd.doubleValue());
					User user = commonService.getUserByUserId(userId);
					map.put("childUserAccount", user.getUserAccount());
					map.put("childUserName", user.getUserName());
				}
			}
			
			String countSql = "SELECT COUNT(userId) FROM ("+getSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);
			
			JSONArray arrayJson = (JSONArray) JSON.toJSON(listData);
			antResult.setData(arrayJson);
			antResult.setCount(count.intValue());
			antResult = commonService.getContextParams(antResult, null);
			return AntResponse.response(antResult);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}

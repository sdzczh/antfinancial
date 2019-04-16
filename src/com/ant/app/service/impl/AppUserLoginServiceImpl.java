package com.ant.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntSystemParams;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppUserLoginService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.LoginHistory;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.Base64Utils;
import com.ant.util.DateUtils;
import com.ant.util.IPUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;

/**
 * @描述 APP用户登陆接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
@Transactional
@Service
public class AppUserLoginServiceImpl implements AppUserLoginService{

	@Autowired
	private BaseDaoI dao;
	
	@Autowired
	private CommonService common;
	
	@SuppressWarnings("unchecked")
	public String login(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String username = json.getString("username");
			String userpwd = json.getString("userpwd");
			if(username==null || "".equals(username.trim()) || userpwd==null || "".equals(userpwd.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			userpwd = userpwd.toLowerCase();
			String name = Base64Utils.decoder(username);
			StringBuffer getUserSql = new StringBuffer();
			getUserSql.append("SELECT ");
			getUserSql.append("tu.id loginUserId,tu.isDel,tu.userCode,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
			getUserSql.append("FROM t_user tu ");
			getUserSql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			getUserSql.append("WHERE ");
//			getUserSql.append("ta.state != :state ");
			getUserSql.append(" (tu.userRole = :userRole OR tu.userRole= :managerRole) ");
			getUserSql.append("AND tu.userAccount = :userAccount ");
			getUserSql.append("AND tu.userPassword= :userPassword ");
			
			Map<String,Object> getuserHqlParams = new HashMap<String, Object>();
			getuserHqlParams.put("userRole", 0);
			getuserHqlParams.put("managerRole", 3);
			getuserHqlParams.put("userAccount", name);
			getuserHqlParams.put("userPassword", userpwd);
			List<?> userInfo = dao.findBySql(getUserSql.toString(), getuserHqlParams);
			Map<String, Object> user = null;
			if(userInfo.size()==0){//账号+密码查询无结果
				//1.查询公共密码
				String getPublicPwdHql = "from SystemParam where keyName=:keyName";
				Map<String,Object> getPublicPwdHqlParams = new HashMap<String, Object>();
				getPublicPwdHqlParams.put("keyName", AntSystemParams.PUBLIC_PASSWORD);
				SystemParam systemParams = dao.getByHql(getPublicPwdHql, getPublicPwdHqlParams);
				if(systemParams==null){//查询结果为空
					antResult.setType(AntType.ANT_206);
					return AntResponse.response(antResult);
				}
				if(!systemParams.getVal().equals(userpwd)){
					antResult.setType(AntType.ANT_101);
					return AntResponse.response(antResult);
				}
				StringBuffer getUserByPublicPwdSql = new StringBuffer();
				getUserByPublicPwdSql.append("SELECT ");
				getUserByPublicPwdSql.append("tu.id loginUserId,tu.userCode,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
				getUserByPublicPwdSql.append("FROM ");
				getUserByPublicPwdSql.append("( ");
				getUserByPublicPwdSql.append("SELECT tu.* FROM t_user tu,t_sysparam tp WHERE tu.userAccount = :userAccount AND tp.key=:key AND tp.val=:val LIMIT 0,1");
				getUserByPublicPwdSql.append(")tu ");
				getUserByPublicPwdSql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
				getUserByPublicPwdSql.append("WHERE ");
				getUserByPublicPwdSql.append(" (tu.userRole = :userRole OR tu.userRole= :managerRole) ");
				
				Map<String,Object> getUserByPublicPwdSqlParams = new HashMap<String, Object>();
				getUserByPublicPwdSqlParams.put("userAccount", name);
				getUserByPublicPwdSqlParams.put("key",AntSystemParams.PUBLIC_PASSWORD);
				getUserByPublicPwdSqlParams.put("val", systemParams.getVal());
				getUserByPublicPwdSqlParams.put("userRole", 0);
				getUserByPublicPwdSqlParams.put("managerRole", 3);
					
				userInfo = dao.findBySql(getUserByPublicPwdSql.toString(), getUserByPublicPwdSqlParams);
				if(userInfo.size()==0){
					antResult.setType(AntType.ANT_101);
					return AntResponse.response(antResult);
				}
				user = (Map<String, Object>) userInfo.get(0);
				
			}else{
				user = (Map<String, Object>) userInfo.get(0);
			}
			
			Integer isDel = Integer.parseInt(user.get("isDel").toString());
			
			if(isDel==1){
				antResult.setType(AntType.ANT_305);
				return AntResponse.response(antResult);
			}
			
			/*记录登录记录*/
			LoginHistory history = new LoginHistory();
			history.setUserId(Integer.parseInt(user.get("loginUserId").toString()));
			history.setUserRole(Integer.parseInt(user.get("userRole").toString()));
			history.setLoginIp(IPUtils.getIpAddr(WebUtils.getRequest()));
			history.setLoginDate(DateUtils.getCurrentTimeStr());
			dao.save(history);
			
			Integer userId = Integer.parseInt(user.get("loginUserId").toString());

			
			User loginUser = dao.getById(User.class, userId);
			loginUser.setLoginState(String.valueOf(System.currentTimeMillis()));
			dao.update(loginUser);
			antResult = common.getContextParamsLogin(antResult,userId.toString());
			
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String checkUserState(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = json.getString("userId");
			if(StrUtils.checkNull(userId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);				
			}
			
			User user = common.getUserByUserId(userId);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			if(user==null){
				antResult.setType(AntType.ANT_208);
			}
			
			if(user.getIsDel()==1){
				antResult.setType(AntType.ANT_305);
			}
			
			Account account = common.getAccountByUserId(userId);
			
			if(account==null){
				antResult.setType(AntType.ANT_208);
			}
			
			if(account.getState()==0){
				antResult.setType(AntType.ANT_300);
			}
			
			if(account.getState()==2){
				antResult.setType(AntType.ANT_102);
			}
			antResult = common.getContextParams(antResult,null);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

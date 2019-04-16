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
import com.ant.app.service.inte.AppUserService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.GoldAward;
import com.ant.pojo.Profit;
import com.ant.pojo.SmsCode;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.Base64Utils;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;

/**
 * @描述 APP用户管理接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-10
 */
@Transactional
@SuppressWarnings("unchecked")
@Service
public class AppUserServiceImpl implements AppUserService{

	@Autowired
	private BaseDaoI dao;

	@Autowired
	private CommonService common;
	
	public String reSetPassword(JSONObject json){
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String codeId = json.getString("codeId");
			String code = json.getString("code");
			String pwd = json.getString("pwd");
			String phoneDecod = json.getString("phone");
			
			if(codeId==null || "".equals(codeId.trim())||code==null||"".equals(code.trim())||pwd==null||"".equals(pwd.trim())||phoneDecod==null||"".equals(phoneDecod.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}			

			/*Base64解码*/
			String phone = Base64Utils.decoder(phoneDecod);
			SmsCode smsCode = null;
			if(codeId=="(null)"||"(null)".equals(codeId)){
				antResult.setType(AntType.ANT_202);
				return AntResponse.response(antResult);
			}
			/*校验验证码-是否正确*/
			String checkCodeHql = "from SmsCode where id=:id and code=:code and phone=:phone";
			Map<String,Object> checkCodeHqlParams = new HashMap<String, Object>();
			checkCodeHqlParams.put("id", Integer.parseInt(codeId));
			checkCodeHqlParams.put("code",code);
			checkCodeHqlParams.put("phone",phone);
			smsCode = (SmsCode) dao.findUnique(checkCodeHql, checkCodeHqlParams);
			if(smsCode==null){
				antResult.setType(AntType.ANT_202);
				return AntResponse.response(antResult);
			}
			
			/*校验验证码-是否过期*/
			Long codeTime = Long.parseLong(smsCode.getTime());
			Long currentTime = System.currentTimeMillis();
			Long min = (currentTime-codeTime)/1000/60;
			if(min>10){
				antResult.setType(AntType.ANT_203);
				return AntResponse.response(antResult);
			}
			
			/*用户信息查询*/
			String refernceHql = "from User where userAccount=:userAccount";
			Map<String,Object> refernceHqlParams = new HashMap<String, Object>();
			refernceHqlParams.put("userAccount", phone);
			User user = (User) dao.findUnique(refernceHql, refernceHqlParams);
			if(user==null){
				antResult.setType(AntType.ANT_208);
				return AntResponse.response(antResult);
			}
			
			if(user.getIsDel()==1){
				antResult.setType(AntType.ANT_305);
				return AntResponse.response(antResult);
			}
			
			/*用户密码重设*/
			user.setUserPassword(pwd);
			dao.update(user);
			
			/*删除验证码信息*/
			dao.delete(smsCode);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String registerUser(JSONObject json){
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String codeId = json.getString("codeId");
			String code = json.getString("code");
			String pwd = json.getString("pwd");
			String phoneDecod = json.getString("phone");
			String nameDecod = json.getString("name");
			String referncePhoneDecod = json.getString("rephone");
			
			if(phoneDecod==null||"".equals(phoneDecod.trim())||code==null||"".equals(code.trim())||pwd==null||"".equals(pwd.trim())||nameDecod==null||"".equals(nameDecod.trim())||referncePhoneDecod==null||"".equals(referncePhoneDecod.trim())||codeId==null || "".equals(codeId.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			if(codeId=="(null)"||"(null)".equals(codeId)){
				antResult.setType(AntType.ANT_202);
				return AntResponse.response(antResult);
			}
			
			/*Base64解码*/
			String phone = Base64Utils.decoder(phoneDecod);
			String name =  Base64Utils.decoder(nameDecod);
			String referncePhone =  Base64Utils.decoder(referncePhoneDecod);
			
			/*校验验证码-是否正确*/
			String checkCodeHql = "from SmsCode where id=:id and code=:code and phone=:phone";
			Map<String,Object> checkCodeHqlParams = new HashMap<String, Object>();
			checkCodeHqlParams.put("id", Integer.parseInt(codeId));
			checkCodeHqlParams.put("code",code);
			checkCodeHqlParams.put("phone",phone);
			SmsCode smsCode = (SmsCode) dao.findUnique(checkCodeHql, checkCodeHqlParams);
			if(smsCode==null){
				antResult.setType(AntType.ANT_202);
				return AntResponse.response(antResult);
			}
			
			/*校验验证码-是否过期*/
			Long codeTime = Long.parseLong(smsCode.getTime());
			Long currentTime = System.currentTimeMillis();
			Long min = (currentTime-codeTime)/1000/60;
			if(min>1099){
				antResult.setType(AntType.ANT_203);
				return AntResponse.response(antResult);
			}
			
			/*推荐人信息查询*/
			/*//手机号
			String refernceHql = "from User where userAccount=:userAccount";
			Map<String,Object> refernceHqlParams = new HashMap<String, Object>();
			refernceHqlParams.put("userAccount", referncePhone);
			User reuser = (User) dao.findUnique(refernceHql, refernceHqlParams);
			if(reuser==null){
				responseParam.setType(4);
				return ResultUtils.response(responseParam);
			}
			*/
			//唯一编码
			String refernceHql = "from User where userCode=:userCode ";
			Map<String,Object> refernceHqlParams = new HashMap<String, Object>();
			refernceHqlParams.put("userCode", referncePhone);
			User reuser = dao.findUnique(refernceHql, refernceHqlParams);
			if(reuser==null){
				antResult.setType(AntType.ANT_200);
				return AntResponse.response(antResult);
			}
			if(reuser.getIsDel()==1){
				antResult.setType(AntType.ANT_311);
				return AntResponse.response(antResult);
			}
			
			String checkActiveState = "from Account where userId=:userId";
			Map<String,Object> checkActiveStateParams = new HashMap<String, Object>();
			checkActiveStateParams.put("userId", reuser.getId());
			Account ex_account = dao.findUnique(checkActiveState,checkActiveStateParams);
			if(ex_account==null){
				antResult.setType(AntType.ANT_200);
				return AntResponse.response(antResult);
			}
			if(ex_account.getState()!=1){
				antResult.setType(AntType.ANT_201);
				return AntResponse.response(antResult);
			}
			
			/*账号是否存在校验*/
			String checkHql = "from User where userAccount=:userAccount ";
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("userAccount", phone);
			User ex_user = (User) dao.findUnique(checkHql, params);
			if(ex_user!=null){
				if(ex_user.getIsDel()==1){
					antResult.setType(AntType.ANT_305);
					return AntResponse.response(antResult);
				}
				antResult.setType(AntType.ANT_109);
				return AntResponse.response(antResult);
			}
			
			
			/*唯一编码生成并校验*/
			String userCode =null;
			User checkUser = new User(); 
			while(checkUser!=null){
				userCode = StrUtils.getCode(8);
				String checkUserCodeHql = "from User where userCode=:userCode";
				Map<String,Object> checkUserCodeHqlParams = new HashMap<String, Object>();
				checkUserCodeHqlParams.put("userCode", userCode);
				checkUser = dao.findUnique(checkUserCodeHql, checkUserCodeHqlParams);
			}
			
			
			String dateTime = DateUtils.getCurrentTimeStr();
			User user = new User();
			user.setCreateTime(dateTime);
			user.setIsDel(0);
			user.setLoginState(String.valueOf(System.currentTimeMillis()));
			user.setReferenceAccount(reuser.getUserAccount());
			user.setReferenceId(reuser.getId());
			user.setUserAccount(phone);
			user.setUserName(name);
			user.setUserPassword(pwd);
			user.setUserRole(0);
			user.setUserCode(userCode);
			dao.save(user);
			
			Account account = new Account();
			account.setUserId(user.getId());
			account.setCreateTime(dateTime);
			account.setState(0);
			account.setPackageJ(0);
			account.setPackageD(0);
			account.setPackageZ(0);
			account.setPackageNum(0);
			account.setUpdateTime(dateTime);
			account.setActiveTime(dateTime);
			account.setProfit(0);
			dao.save(account);
			
			/*删除验证码信息*/
			dao.delete(smsCode);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @描述 根据ID修改用户信息<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	public String updateUserInfoById(JSONObject json){
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String sid = json.getString("id");
			String skey =json.getString("type");
			if(sid==null || "".equals(sid.trim()) || skey==null || "".equals(skey.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(sid);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer id = Integer.parseInt(sid);
			String hql = "from Account where userId=:userId";
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("userId", id);
			Account account  = dao.findUnique(hql, params);
			
			Integer key = Integer.parseInt(skey);
			if(key==1){//姓名
				user.setUserName(Base64Utils.decoder(json.getString("userName").trim()));
				dao.update(user);
			}
			if(key==2){//支付宝
				account.setAlipayNumber(Base64Utils.decoder(json.getString("alipayNumber").trim()));
				dao.update(account);
			}
			if(key==3){//银行卡信息
				account.setBankName(Base64Utils.decoder(json.getString("bankName").trim()));
				account.setBankNumber(Base64Utils.decoder(json.getString("bankNumber").trim()));
				dao.update(account);
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("tu.id loginUserId,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
			sql.append("FROM t_user tu ");
			sql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			sql.append("WHERE tu.id= :id ");
			Map<String,Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("id", id);
			List<?> users = dao.findBySql(sql.toString(), sqlParams);
			if(users.size()==0){
				antResult.setType(AntType.ANT_208);
				return AntResponse.response(antResult);
			}
			Map<String,Object> resUuser = (Map<String, Object>) users.get(0);
			JSONObject resultJson = (JSONObject) JSONObject.toJSON(resUuser);			
			antResult.setData(resultJson);
			antResult = common.getContextParams(antResult, sid);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public String getUserInfoById(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String id = json.getString("id");
			if(id==null || "".equals(id.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			String userId = WebUtils.getRequest().getHeader("userId");
			if(StrUtils.checkNull(userId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			User loginuser = common.getUserByUserId(userId);
			antResult = common.checkUserLoginState(antResult, loginuser);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(id);
			Account account = common.getAccountByUserId(id);
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("tu.id loginUserId,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
			sql.append("FROM t_user tu ");
			sql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			sql.append("WHERE tu.id= :id ");
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("id", id);
			List<?> users = dao.findBySql(sql.toString(), params);
			if(users.size()==0){
				antResult.setType(AntType.ANT_208);
				return AntResponse.response(antResult);
			}
			
			Map<String,Object> userInfo = (Map<String, Object>) users.get(0);
			JSONObject resultJson = (JSONObject) JSONObject.toJSON(userInfo);
			antResult.setData(resultJson);
			antResult = common.getContextParamsAndPackageInfo(antResult,account,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String updateUserPwd(JSONObject json){
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String id = json.getString("id");
			if(id==null || "".equals(id.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			String userpwd = json.getString("userpwd");
			if(userpwd==null || "".equals(userpwd.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			String usernewpwd = json.getString("usernewpwd");
			if(usernewpwd==null || "".equals(usernewpwd.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(id);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			if(user.getUserPassword().equals(userpwd)){
				user.setUserPassword(usernewpwd);
				dao.update(user);
				antResult = common.getContextParams(antResult,id);
				return AntResponse.response(antResult);
			}else{
				antResult.setType(AntType.ANT_103);
				return AntResponse.response(antResult);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public String updateUserActiveState(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String loginUserId = json.getString("loginUserId");
			String targetUserId = json.getString("targetUserId");
			
			if(loginUserId==null || "".equals(loginUserId.trim()) || targetUserId==null || "".equals(targetUserId.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(loginUserId);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Account targetUser = common.getAccountByUserId(targetUserId);
			if(targetUser.getState()==1){
				antResult.setType(AntType.ANT_308);
				return AntResponse.response(antResult);
			}
			
			User targerU = common.getUserByUserId(targetUserId);
			if(targerU.getReferenceId()!=Integer.parseInt(loginUserId)){
				antResult.setType(AntType.ANT_309);
				return AntResponse.response(antResult);
			}
			
			/*查询激活用户费用*/
			SystemParam activeUserAmountInfo = common.getValByKey(AntSystemParams.ACTIVE_AMOUNT);
			if(activeUserAmountInfo==null){//查询结果为空
				antResult.setType(AntType.ANT_208);
				return AntResponse.response(antResult);
			}
			Double activeAmount = Double.parseDouble(activeUserAmountInfo.getVal());
			
			/*查询激活用户费用平台提成*/
			SystemParam activePercentageInfo = common.getValByKey(AntSystemParams.ACTIVE_PERCENTAGE);
			if(activePercentageInfo==null){//查询结果为空
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
			Double activePercentage = Double.parseDouble(activePercentageInfo.getVal());
			
			/*查询推荐人Z钱包余额*/
			Account account = common.getAccountByUserId(loginUserId);
			
			if(account==null){
				antResult.setType(AntType.ANT_208);
				return AntResponse.response(antResult);
			}
			
			/*判断用户Z钱包金币是否充足*/
			Double packageZ = account.getPackageZ();
			Double allAmount = MatchUtils.add(activeAmount, (MatchUtils.multiply(activeAmount, activePercentage)));
			if(packageZ<allAmount){//金币数量不足
				antResult.setType(AntType.ANT_106);
				return AntResponse.response(antResult);
			}
			
			/*查询推荐一个的奖励金币数*/
			SystemParam goldAwardInfo = common.getValByKey(AntSystemParams.ACTIVE_AWARD);
			if(goldAwardInfo==null){//查询结果为空
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
			String goldAwardSingle = goldAwardInfo.getVal();
			
			/*查询推荐人直推人数，计算奖励金币*/
			int userAccount = common.countActiveUsers(loginUserId);
			Double goldAward = MatchUtils.doCalculationA(String.valueOf(userAccount),goldAwardSingle+"*"+userAccount+"+"+goldAwardSingle);
			
			/*查询推荐最高奖励*/
			SystemParam goldAwardTopInfo = common.getValByKey(AntSystemParams.ACTIVE_AWARD_TOP);
			if(goldAwardTopInfo==null){//查询结果为空
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
			Double goldAwardTop = Double.parseDouble(goldAwardTopInfo.getVal());
			if(goldAward>goldAwardTop){//高于最高奖励时，奖励以最高值为准
				goldAward = goldAwardTop;
			}
			
			/*推荐人Z钱包金币计算机*/
			account.setPackageZ(MatchUtils.subtract(packageZ, allAmount));
			account.setPackageZ(MatchUtils.add(account.getPackageZ(), goldAward));
			dao.update(account);
			
			String currentTimeStr = DateUtils.getCurrentTimeStr();
		
			/*目标用户状态激活*/
			targetUser.setState(1);
			targetUser.setPackageNum(1);
			targetUser.setActiveTime(currentTimeStr);
			dao.update(targetUser);
			
			
			/*记录Z钱包激活用户金币流水*/
			CapitalFlow capitalFlow = new CapitalFlow();
			capitalFlow.setUserId(Integer.parseInt(loginUserId));
			capitalFlow.setCreateDateTime(currentTimeStr);
			capitalFlow.setPackageType(2);
			capitalFlow.setAmount(allAmount);
			capitalFlow.setType(6);
			capitalFlow.setActiveUserId(Integer.parseInt(targetUserId));
			dao.save(capitalFlow);
			
			/*记录Z钱包推广奖励金币流水*/
			CapitalFlow capitalFlow2 = new CapitalFlow();
			capitalFlow2.setUserId(Integer.parseInt(loginUserId));
			capitalFlow2.setCreateDateTime(currentTimeStr);
			capitalFlow2.setPackageType(2);
			capitalFlow2.setAmount(goldAward);
			capitalFlow2.setType(3);
			capitalFlow2.setActiveUserId(Integer.parseInt(targetUserId));
			dao.save(capitalFlow2);
			
			/*记录Z钱包推广奖励金币*/
			GoldAward award = new GoldAward();
			award.setUserId(Integer.parseInt(loginUserId));
			award.setCreateDateTime(currentTimeStr);
			award.setAmount(goldAward);
			award.setAmountType(0);
			award.setChildUserId(Integer.parseInt(targetUserId));
			dao.save(award);
			
			/*记录用户当天激活用户收益*/
			Profit profit = new Profit();
			profit.setAmount(goldAward);
			profit.setCreateDate(DateUtils.getCurrentTimeStr());
			profit.setType(1);
			profit.setUserId(Integer.parseInt(loginUserId));
			dao.save(profit);
			
			Map<String,Object> responseUser = common.getUserInfoByUserId(loginUserId);
			JSONObject resultJson = (JSONObject) JSONObject.toJSON(responseUser);		
			antResult.setData(resultJson);
			antResult = common.getContextParamsAndPackage(antResult,account,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

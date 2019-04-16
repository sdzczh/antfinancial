package com.ant.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntSystemParams;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppPackageService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.glob.GolbParams;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;
import com.ant.util.ResultUtils;

/**
 * @描述 增值包接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-21
 */
@Transactional
@Service
public class AppPackageServiceImpl implements AppPackageService{
	
	@Autowired
	BaseDaoI dao;
	
	@Autowired
	CommonService common;

	@Override
	public String openPackage(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String dateTime = DateUtils.getCurrentTimeStr();
			String userIdStr = json.getString("userId");
			String packageTypeStr = json.getString("packageType");
			if(userIdStr==null || "".equals(userIdStr.trim())||packageTypeStr==null || "".equals(packageTypeStr.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(userIdStr);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Account account = common.getAccountByUserId(userIdStr);
			if(account==null){
				antResult.setType(AntType.ANT_208);
				return AntResponse.response(antResult);
			}
			
			/*用户已开启增值包个数*/
			int packageNum = account.getPackageNum();
			
			/*查询增值包可开启最大个数 */
			SystemParam packageTopNumInfo = common.getValByKey(AntSystemParams.PACKAGE_TOP_NUM);
			if(packageTopNumInfo==null){
				return ResultUtils.responseError(GolbParams.MSG_PARAMS_ERROR);
			}
			int packageTomNum = Integer.parseInt(packageTopNumInfo.getVal());
			
			if(packageNum>=packageTomNum){
				antResult.setType(AntType.ANT_105);
				return AntResponse.response(antResult);
			}
			
			/*查询开启增值包费用*/
			SystemParam packageAmountInfo = common.getValByKey(AntSystemParams.PACKAGE_AMOUNT);
			if(packageAmountInfo==null){
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
			Double packageAmount = Double.parseDouble(packageAmountInfo.getVal());

			/*判断对应钱包金币是否充足*/
			int packageType = Integer.parseInt(packageTypeStr);
			Double userPackage = 0d;
			if(packageType==0){
				userPackage = account.getPackageJ();
			}
			if(packageType==1){
				userPackage = account.getPackageD();
			}
			if(userPackage<packageAmount){
				antResult.setType(AntType.ANT_106);
				return AntResponse.response(antResult);
			}
			
			/*更改用户对应钱包金币和增值包数量*/
			account.setPackageNum(account.getPackageNum()+1);
			if(packageType==0){
				account.setPackageJ(MatchUtils.subtract(account.getPackageJ(), packageAmount));
			}
			if(packageType==1){
				account.setPackageD(MatchUtils.subtract(account.getPackageD(), packageAmount));
			}
			dao.update(account);
			
			/*记录金币流水*/
			CapitalFlow cap = new CapitalFlow();
			cap.setAmount(packageAmount);
			cap.setCreateDateTime(dateTime);
			cap.setPackageType(packageType);
			cap.setType(9);
			cap.setUserId(Integer.parseInt(userIdStr));
			dao.save(cap);
			
			antResult = common.getContextParamsAndPackageInfo(antResult, account, user);
			
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

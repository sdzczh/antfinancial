package com.ant.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntSystemParams;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppSignService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.GoldAward;
import com.ant.pojo.Profit;
import com.ant.pojo.Sign;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;

/**
 * @描述 签到接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-21
 */
@Transactional
@Service
public class AppSignServiceImpl implements AppSignService{

	@Autowired
	BaseDaoI dao;
	
	@Autowired
	CommonService common;
	
	public String doSign(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userIdStr = json.getString("userId");
			if(userIdStr==null || "".equals(userIdStr.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(userIdStr);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			String createDate = DateUtils.getCurrentDateStr();
			String hql = "from Sign where userId=:userId and createDate=:createDate";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", Integer.parseInt(userIdStr));
			params.put("createDate", createDate);
			Sign existSign = dao.findUnique(hql, params);

			if(existSign==null){//未签到
				SystemParam signInfo = common.getValByKey(AntSystemParams.SIGN_AWARD);
				if(signInfo==null){
					antResult.setType(AntType.ANT_206);
					return AntResponse.response(antResult);
				}
				Double signAmount = Double.parseDouble(signInfo.getVal().toString());
				
				String dateTime = DateUtils.getCurrentTimeStr();
				
				/*签到记录*/
				Sign sign = new Sign();
				sign.setUserId(Integer.parseInt(userIdStr));
				sign.setCreateDate(DateUtils.getCurrentDateStr());
				sign.setAmount(signAmount);
				dao.save(sign);
				
				/*用户Z钱包修改*/
				Account account = common.getAccountByUserId(userIdStr);
				
				if(account==null){
					antResult.setType(AntType.ANT_208);
					return AntResponse.response(antResult);
				}
				account.setPackageZ(MatchUtils.add(account.getPackageZ(),signAmount));
				dao.update(account);
				
				/*记录Z钱包金币流水记录*/
				CapitalFlow cap = new CapitalFlow();
				cap.setAmount(signAmount);
				cap.setCreateDateTime(dateTime);
				cap.setPackageType(2);
				cap.setUserId(Integer.parseInt(userIdStr));
				cap.setType(2);
				dao.save(cap);
				
				/*记录Z钱包奖励金币*/
				GoldAward award = new GoldAward();
				award.setUserId(Integer.parseInt(userIdStr));
				award.setCreateDateTime(dateTime);
				award.setAmount(signAmount);
				award.setAmountType(1);
				dao.save(award);
				
				/*记录用户当天签到收益*/
				Profit profit = new Profit();
				profit.setAmount(signAmount);
				profit.setCreateDate(DateUtils.getCurrentTimeStr());
				profit.setType(0);
				profit.setUserId(Integer.parseInt(userIdStr));
				dao.save(profit);
				
				antResult = common.getContextParamsAndPackage(antResult,account,user);

				return AntResponse.response(antResult);
			}else{//已签到
				antResult.setType(AntType.ANT_104);
				return AntResponse.response(antResult);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

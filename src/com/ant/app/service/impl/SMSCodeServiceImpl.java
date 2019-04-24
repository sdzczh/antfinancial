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
import com.ant.app.service.inte.CommonService;
import com.ant.app.service.inte.SMSCodeService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.SmsCode;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.Base64Utils;
import com.ant.util.SMSCodeUtil;

/**
 * @描述 短信接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-24
 */
@Transactional
@Service
public class SMSCodeServiceImpl implements SMSCodeService{
	
	@Autowired
	BaseDaoI dao;
	
	@Autowired
	CommonService common;
	
	@Override
	public String getValidateCode(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String phoneDecode  = json.getString("phone");
			if(phoneDecode==null || "".equals(phoneDecode.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			String type = json.getString("type");
			
			String phone = Base64Utils.decoder(phoneDecode);
			if(type==null || "".equals(type.trim())){//注册
				/*账号是否存在校验*/
				String checkHql = "from User where userAccount=:userAccount ";
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("userAccount", phone);
				User user = (User) dao.findUnique(checkHql, params);
				if(user!=null){
					if(user.getIsDel()==1){
						antResult.setType(AntType.ANT_305);
					}else{
						antResult.setType(AntType.ANT_109);
					}
					return AntResponse.response(antResult);
				}
			}
			
			/*APPKEY*/
			SystemParam appkInfo = common.getValByKey(AntSystemParams.APP_KEY);
			if(appkInfo==null){
				antResult.setType(AntType.ANT_107);
				return AntResponse.response(antResult);
			}
			String appk = appkInfo.getVal();
			
			/*APP_SECRET*/
			SystemParam secretInfo = common.getValByKey(AntSystemParams.APP_SECRET);
			if(secretInfo==null){
				antResult.setType(AntType.ANT_107);
				return AntResponse.response(antResult);
			}
			String secret = secretInfo.getVal();
			
			/*NONCE*/
			SystemParam nonceInfo = common.getValByKey(AntSystemParams.NONCE);
			if(nonceInfo==null){
				antResult.setType(AntType.ANT_107);
				return AntResponse.response(antResult);
			}
			String nonce = nonceInfo.getVal();
			
			/*TEMPLATEID*/
			SystemParam templateInfo = common.getValByKey(AntSystemParams.TEMPLATEID_VALIDATE);
			if(templateInfo==null){
				antResult.setType(AntType.ANT_107);
				return AntResponse.response(antResult);
			}
			String templateId = templateInfo.getVal();
			
			/*CODELEN*/
			SystemParam codelenInfo = common.getValByKey(AntSystemParams.CODELEN);
			if(codelenInfo==null){
				antResult.setType(AntType.ANT_107);
				return AntResponse.response(antResult);
			}
			String codeLen = codelenInfo.getVal();
			JSONObject codeJson = SMSCodeUtil.send(phone, templateId);

			String state =codeJson.getString("code");
			String  code = null;
			
			if(state.equals("200")){
				code = codeJson.getString("obj");
				if(code==null){
					antResult.setType(AntType.ANT_206);
					return AntResponse.response(antResult);
				}
			}else if(state.equals("416")){
				antResult.setType(AntType.ANT_108);
				return AntResponse.response(antResult);
			}else{
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
			
			SmsCode smsCode = new SmsCode();
			smsCode.setCode(code);
			smsCode.setPhone(phone);
			smsCode.setTime(String.valueOf(System.currentTimeMillis()));
			dao.save(smsCode);
			
			antResult.setCodeId(smsCode.getId().toString());
			
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

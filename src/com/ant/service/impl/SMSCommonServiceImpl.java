package com.ant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ant.app.model.AntSystemParams;
import com.ant.app.service.inte.CommonService;
import com.ant.service.inte.SMSCommonService;
import com.ant.util.DateUtils;
import com.ant.util.SMSCodeUtil;

/**
 * @描述 短信接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-26
 */
@Service
public class SMSCommonServiceImpl implements SMSCommonService{

	@Autowired
	CommonService common;
	
	public boolean sendNotice(List<String> phones) {
		try {
			/*APPKEY*/
			String appk = common.getValStrByKey(AntSystemParams.APP_KEY);
			
			/*APP_SECRET*/
			String secret = common.getValStrByKey(AntSystemParams.APP_SECRET);
			
			/*NONCE*/
			String nonce = common.getValStrByKey(AntSystemParams.NONCE);
			
			/*TEMPLATEID*/
			String templateId = common.getValStrByKey(AntSystemParams.TEMPLATEID_NOTICE);
			
			/*定单有效时长/小时*/
			String hour = common.getValStrByKey(AntSystemParams.DEADINE);
			
			/*匹配日期时间*/
			String dateTime = DateUtils.getCurrentTimeStr();
				
//			String result = SMSCodeUtil.sendNotice(appk, secret, nonce, templateId, phones, hour, dateTime);
			String result = "200";

			if("200".equals(result)){
				return true;
			}
			
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

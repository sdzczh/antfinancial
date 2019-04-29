package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 短信接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-24
 */
public interface SMSCodeService {

	/**
	 * @描述 发送短信验证码<br>
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-24
	 */
	public String getValidateCode(JSONObject json);
}

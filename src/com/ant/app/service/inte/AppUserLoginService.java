package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 APP用户登陆接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
public interface AppUserLoginService {
	
	/**
	 * @描述 用户登陆<br>
	 * @param json JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-8
	 */
	public String login(JSONObject json);
	
	/**
	 * @描述 用户信息检测<br>
	 * @param json JSON格式参数
	 * @return 
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-5
	 */
	public String checkUserState(JSONObject json);
}

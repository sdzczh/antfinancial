package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 APP用户管理接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-10
 */
public interface AppUserService {
	
	/**
	 * @描述 找回密码<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-24
	 */
	public String reSetPassword(JSONObject json);
	
	/**
	 * @描述 用户注册<br>
	 * @param json JSON对象参数
	 * @urn JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-24
	 */
	public String registerUser(JSONObject json);
	
	/**
	 * @描述 根据ID修改用户信息<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	public String updateUserInfoById(JSONObject json);
	
	/**
	 * @描述 根据ID获取用户信息<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	public String getUserInfoById(JSONObject json);
	
	/**
	 * @描述 密码修改<br>
	 * @param json JSON对象参数
	 * @return JOSN格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-10
	 */
	public String updateUserPwd(JSONObject json);
	
	
	/**
	 * @描述 激活用户<br>
	 * @param json JSON对象参数
	 * @return　JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-10
	 */
	public String updateUserActiveState(JSONObject json);
	
}

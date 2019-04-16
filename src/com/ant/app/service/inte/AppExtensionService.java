package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 推广接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
public interface AppExtensionService {

	/**
	 * @描述 获取一级会员信息<br>
	 * @param json JSONObject对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public String getUserOfFirstLevel(JSONObject json);
	
	/**
	 * @描述 获取二级会员信息<br>
	 * @param json JSONObject格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public String getUserOfSecondLevel(JSONObject json);
	
	/**
	 * @描述 获取三级会员信息<br>
	 * @param json JSONObject格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public String getUserOfThirdLevel(JSONObject json);
}

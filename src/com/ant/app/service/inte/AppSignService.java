package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 签到接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-21
 */
public interface AppSignService {
	
	/**
	 * @描述 签到<br>
	 * @param json JSON格式参数
	 * @return　JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-21
	 */
	public String doSign(JSONObject json);
}

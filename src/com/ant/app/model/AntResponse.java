package com.ant.app.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AntResponse {

	/**
	 * @描述 对象格式化为JSON<br>
	 * @param obj 返回值对象
	 * @return JSON 字符串
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-28
	 */	
	public static String response(Object obj){
		JSONObject json = (JSONObject) JSON.toJSON(obj);
		return json.toJSONString();
	}
}

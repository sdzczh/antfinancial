package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 增值包接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-21
 */
public interface AppPackageService {

	/**
	 * @描述 开启增值包<br>
	 * @param json
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-21
	 */
	public String openPackage(JSONObject json);
}

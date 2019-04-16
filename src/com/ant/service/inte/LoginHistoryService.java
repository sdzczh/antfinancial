package com.ant.service.inte;

import java.util.Map;

/**
 * @描述 用户登录记录接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-13
 */
public interface LoginHistoryService {

	/**
	 * @描述 根据ID查询用户登录记录<br>
	 * @param map 参数对象
	 * @param id 用户ID
	 * @param page 页数
	 * @param rows 行数
	 * @return 返回对象
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	public Map<String, Object> getLoginHistoryById(Map<String,Object>map,String id,Integer page,Integer rows);
}

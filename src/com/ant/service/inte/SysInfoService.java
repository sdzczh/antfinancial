package com.ant.service.inte;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @描述 系统信息接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
public interface SysInfoService {
	
	/**
	 * @描述 系统信息获取<br>
	 * @param map 参数对象
	 * @param request 请求对象
	 * @param response 返回对象
	 * @return 返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-8
	 */
	public Map<String,Object> info(Map<String,Object> map,HttpServletRequest request,HttpServletResponse response);
}

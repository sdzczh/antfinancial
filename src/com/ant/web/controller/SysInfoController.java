package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ant.base.BaseController;
import com.ant.service.inte.SysInfoService;

/**
 * @描述 系统信息<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
@Controller
@RequestMapping("/system")
public class SysInfoController extends BaseController{
	
	@Autowired
	private SysInfoService sysInfoService;
	
	/**
	 * @描述 系统信息获取<br>
	 * @param map 参数对象
	 * @return 地址
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-8
	 */
	@RequestMapping("info")
	public String info(Map<String,Object> map){
		sysInfoService.info(map, request, response);
		return "systemManage/info";
	}
}

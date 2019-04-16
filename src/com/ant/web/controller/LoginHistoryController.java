package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ant.base.BaseController;
import com.ant.service.inte.LoginHistoryService;

/**
 * @描述 登录记录<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-13
 */
@Controller
@RequestMapping("/system")
public class LoginHistoryController extends BaseController{

	@Autowired
	LoginHistoryService service;
	
	/**
	 * @描述 根据ID查询用户登录记录<br>
	 * @param map 参数对象
	 * @param id 用户ID
	 * @return　返回地址
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	@RequestMapping("getLoginHistoryById.action")
	public String getLoginHistoryById(Map<String,Object>map,String loginAccount,String createTimeStart,String createTimeEnd){
		map.put("loginAccount", loginAccount);
		map.put("createTimeStart", createTimeStart);
		map.put("createTimeEnd", createTimeEnd);
		service.getLoginHistoryById(map,null, page, rows);
		return "systemManage/loginHistory";
	}
}

package com.ant.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.app.service.inte.AppNoticeService;
import com.ant.base.BaseController;

/**
 * @描述 系统公告<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@RequestMapping("/app/notice")
@Controller
public class AppNoticeController extends BaseController{

	@Autowired
	private AppNoticeService service;
	
	/**
	 * @描述 获取最新公告<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	@ResponseBody
    @RequestMapping(value="getNewNotice.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
	public String getNewNotice(){
		return service.getNewNotice();
	}
}

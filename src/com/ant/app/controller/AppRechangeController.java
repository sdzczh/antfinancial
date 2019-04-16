package com.ant.app.controller;

import com.ant.app.service.inte.AppNoticeService;
import com.ant.app.service.inte.AppRechangeService;
import com.ant.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @描述 充值<br>
 * @author 昭和
 * @版本 v1.0.0
 * @日期 2019.4.16
 */
@RequestMapping("/app/rechange")
@Controller
public class AppRechangeController extends BaseController{

	@Autowired
	private AppRechangeService appRechangeService;
	
	/**
	 * @描述 获取充值信息<br>
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="getRechangeInfo.action",method=RequestMethod.GET,produces = "application/json;charset=utf-8")
	public String getRechangeInfo(){
		return appRechangeService.getRechangeInfo();
	}
}

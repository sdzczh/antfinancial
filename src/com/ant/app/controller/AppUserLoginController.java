package com.ant.app.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.ant.glob.GolbParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppUserLoginService;
import com.ant.base.BaseController;
/**
 * @描述 APP用户登陆<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
@RequestMapping("/app/user")
@Controller
public class AppUserLoginController extends BaseController{

	@Autowired
	private AppUserLoginService service;
	
	/**
	 * @描述 用户登陆<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @throws UnsupportedEncodingException 
	 * @版本 v1.0.0
	 * @日期 2017-6-8
	 */
    @ResponseBody
    @RequestMapping(value="login.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
	public String login(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				param = URLDecoder.decode(param, GolbParams.UTF8);
				JSONObject json = JSONObject.parseObject(param);
				return service.login(json);
			} catch (Exception e) {
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
    
    @ResponseBody
    @RequestMapping(value="checkUser.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
    public String checkUserState(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.checkUserState(json);
			} catch (Exception e) {
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
}

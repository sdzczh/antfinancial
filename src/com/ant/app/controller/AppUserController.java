package com.ant.app.controller;

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
import com.ant.app.service.inte.AppUserService;
import com.ant.base.BaseController;

/**
 * @描述 APP用户管理<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-10
 */
@RequestMapping("/app/user")
@Controller
public class AppUserController extends BaseController{

	@Autowired
	AppUserService service;
	
	/**
	 * @描述 找回密码<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	@ResponseBody
	@RequestMapping(value="reSetPassword.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String reSetPassword(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.reSetPassword(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
	
	/**
	 * @描述 用户注册<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	@ResponseBody
	@RequestMapping(value="registerUser.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String registerUser(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.registerUser(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
	
	/**
	 * @描述 根据ID修改用户信息<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	@ResponseBody
	@RequestMapping(value="updateUserInfoById.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String updateUserInfoById(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.updateUserInfoById(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
	
	/**
	 * @描述 根据ID获取用户信息<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	@ResponseBody
	@RequestMapping(value="getUserInfoById.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getUserInfoById(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getUserInfoById(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
	/**
	 * @描述 密码修改<br>
	 * @param params JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-10
	 */
	@ResponseBody
	@RequestMapping(value="updateUserPwd.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String updateUserPwd(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.updateUserPwd(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
	
	
	/**
	 * @描述 激活用户<br>
	 * @param params JSON对象参数
	 * @return　JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-10
	 */
	@ResponseBody
	@RequestMapping(value="updateUserActiveState.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String updateUserActiveState(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.updateUserActiveState(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	} 
	
}

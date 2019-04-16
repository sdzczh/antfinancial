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
import com.ant.app.service.inte.AppExtensionService;
import com.ant.base.BaseController;
/**
 * @描述 推广<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@RequestMapping("/app/extension")
@Controller
public class AppExtensionController extends BaseController{
	
	@Autowired
	private AppExtensionService service;
	
	/**
	 * @描述 获取一级会员信息<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	@ResponseBody
	@RequestMapping(value="getuserOfFirstLevel.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getUserOfFirstLevel(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getUserOfFirstLevel(json);
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
	 * @描述 获取二级会员信息<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	@ResponseBody
	@RequestMapping(value="getUserOfSecondLevel.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getUserOfSecondLevel(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getUserOfSecondLevel(json);
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
	 * @描述 获取三级会员信息<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	@ResponseBody
	@RequestMapping(value="getUserOfThirdLevel.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getUserOfThirdLevel(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getUserOfThirdLevel(json);
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

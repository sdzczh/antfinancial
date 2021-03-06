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
import com.ant.app.service.inte.AppSignService;
import com.ant.base.BaseController;

/**
 * @描述 用户签到<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-21
 */
@Controller
@RequestMapping("/app/sign")
public class AppSignController extends BaseController{

	@Autowired
	AppSignService service;
	
	/**
	 * @描述 用户签到<br>
	 * @param params JSON格式参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-21
	 */
	@ResponseBody
	@RequestMapping(value="doSign.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String doSign(@RequestBody String param) {
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.doSign(json);
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

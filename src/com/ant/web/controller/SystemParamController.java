package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.base.BaseController;
import com.ant.pojo.SystemParam;
import com.ant.service.inte.SystemParamService;

/**
 * 参数设置
 * @author lina
 *2017-6-16
 */
@Controller
@RequestMapping("/systemParam")
public class SystemParamController extends BaseController{
	
	@Autowired
	private SystemParamService systemParamService;
	
	/**
	 * 查询参数设置列表
	 * @param map
	 * @return
	 */
	@RequestMapping("paramList")
	public String paramList(Map<String, Object> map,String keyName,String remark){
		map.put("keyName",keyName );
		map.put("remark",remark );
		systemParamService.queryParamList(map,page,rows);
		return "systemParams/systemParamsQuery";
	} 
	
	/**
	 * @描述 跳转到参数添加页面<br>
	 * @param map 参数对象
	 * @return 地址
	 * @author 李娜
	 */
	@RequestMapping("toParamAdd.action")
	public String toParamAdd( ){
		return "systemParams/systemParamsAdd";
	}

	/**
	 * 添加参数
	 * @param param
	 * @return 添加结果
	 */
	@ResponseBody
	@RequestMapping(value="paramAdd.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public Map<String,Object> noticeAdd(SystemParam param){
		Map<String,Object> map = systemParamService.addParam(param);
		return map;
	}
	
	/**
	 * 跳转到修改公告
	 * @param paramId
	 * @return
	 */
	@RequestMapping("toParamModify.action")
	public String toParamModify(Map<String,Object> map,Integer paramId  ,String currentUrl){
		if(paramId!=null){
			map.put("params", systemParamService.queryOne(paramId));
		}
		map.put("currentUrl", currentUrl);
		return "systemParams/systemParamsModify";
	}
	
	/**
	 * 修改参数
	 * @param param
	 * @return 修改结果
	 */
	@ResponseBody
	@RequestMapping("paramModify.action")
	public Map<String,Object> paramModify(SystemParam param){
		Map<String,Object> map = systemParamService.paramModify(param);
		return map;
	}
	
	/**
	 * 删除参数
	 * @param paramId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="paramDelete.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public Map<String,Object> paramDelete(Integer paramId){
		SystemParam param = systemParamService.queryOne(paramId);
		return systemParamService.paramDelete(param);
	}
}

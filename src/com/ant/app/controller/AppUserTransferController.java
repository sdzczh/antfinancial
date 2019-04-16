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
import com.ant.app.service.inte.AppUserTransferService;
import com.ant.base.BaseController;
/**
 * @描述 APP用户钱包转账<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
@RequestMapping("/app/transfer")
@Controller
public class AppUserTransferController extends BaseController{

	@Autowired
	private AppUserTransferService service;

	/**
	 * @描述 J、D钱包转Z钱包<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
    @ResponseBody
    @RequestMapping(value="updatePackage.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
    public String updatePackage(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.updatePackage(json);
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
	 * @描述 J、D、Z钱包金币流水记录<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
    @ResponseBody
    @RequestMapping(value="getPackageRecord.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
    public String getPackageRecord(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getPackageRecord(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
    }
    @ResponseBody
    @RequestMapping(value="getPackageRecords.action",method=RequestMethod.GET,produces = "application/json;charset=utf-8")	
    public String getPackageRecords(){
		JSONObject json = new JSONObject();
		json.put("userId", 134);
		json.put("packageType", 2);
		return service.getPackageRecord(json);
    }
    
    /**
	 * @描述 J钱包静态收益<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
    @ResponseBody
    @RequestMapping(value="getStaticProfit.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
    public String getStaticProfit(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getStaticProfit(json);
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
   	 * @描述 D钱包推广收益<br>
   	 * @param json JSON对象参数
   	 * @return JSON格式返回值
   	 * @author 任晴
   	 * @版本 v1.0.0
   	 * @日期 2017-6-26
   	 */
       @ResponseBody
       @RequestMapping(value="getPromotionAward.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
       public String getPromotionAward(@RequestBody String param){
   		AntResult antResult = new AntResult();
   		if(param!=null && !"".equals(param.trim())){
   			try {
   				JSONObject json = JSONObject.parseObject(param);
   				return service.getPromotionAward(json);
   			} catch (Exception e) {
   				e.printStackTrace();
   				antResult.setType(AntType.ANT_206);
   				return AntResponse.response(antResult);
   			}
   		}
   		antResult.setType(AntType.ANT_204);
   		return AntResponse.response(antResult);
       }
       @ResponseBody
       @RequestMapping(value="getPromotionAward.action",method=RequestMethod.GET,produces = "application/json;charset=utf-8")	
       public String getPromotionAward(){
    	   JSONObject json = new JSONObject();
    	   json.put("userId", "163");
    	   return service.getPromotionAward(json);
       }
       
       /**
   	 * @描述 Z钱包奖励金币<br>
   	 * @param json JSON对象参数
   	 * @return JSON格式返回值
   	 * @author 任晴
   	 * @版本 v1.0.0
   	 * @日期 2017-6-26
   	 */
    @ResponseBody
    @RequestMapping(value="getGoldAward.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")	
   	public String getGoldAward(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getGoldAward(json);
			} catch (Exception e) {
				e.printStackTrace();
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);	
    }       
    @ResponseBody
    @RequestMapping(value="getGoldAwards.action",method=RequestMethod.GET,produces = "application/json;charset=utf-8")	
    public String getGoldAwards(){
    	
	JSONObject json = new JSONObject();
	json.put("userId", 182);
	return service.getGoldAward(json);

    }       
    
    //测试
    @ResponseBody
    @RequestMapping(value="updatePackage1.action",method=RequestMethod.GET,produces = "application/json;charset=utf-8")	
    public String jToZ1(@RequestBody String params){
    
    			JSONObject json = new JSONObject();
    			json.put("userId", 14);//id
                json.put("packageType", 1);//1:J钱包>Z钱包 2.D钱包>Z钱包
                json.put("page", 0);
                json.put("rows", 10);
    			
    			return service.getPackageRecord(json);
    		
    }
}

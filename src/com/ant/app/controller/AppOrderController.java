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
import com.ant.app.service.inte.AppOrderService;
import com.ant.base.BaseController;

/**
 * @描述 交易定单<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-19
 */
@Controller
@RequestMapping("/app/order")
public class AppOrderController extends BaseController{

	@Autowired
	AppOrderService service;

	/**
	 * @描述 买入定单提交<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	@ResponseBody
	@RequestMapping(value="submitOrderBuy.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String submitOrderBuy(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.submitOrderBuy(json);
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
	 * @描述 卖出定单提交<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	@ResponseBody
	@RequestMapping(value="submitOrderSale.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String submitOrderSale(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.submitOrderSale(json);
			} catch (Exception e) {
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);

	}
	
	@ResponseBody
	@RequestMapping(value="getOrderByUserId.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getOrderByUserId(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getOrderByUserId(json);
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
	 * @描述 根据ID查询定单信息<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-20
	 */
	@ResponseBody
	@RequestMapping(value="getOrderInfoById.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getOrderInfoById(@RequestBody String param){
		
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getOrderInfoById(json);
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
	 * @描述 根据用户ID查询交易记录<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-20
	 */
	@ResponseBody
	@RequestMapping(value="getOrderRecords.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getOrderRecords(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.getOrderRecords(json);
			} catch (Exception e) {
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
	
	/**
	 * @描述 确认打款,确认收款<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-23
	 */
	@ResponseBody
	@RequestMapping(value="confirmOrder.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String confirmOrder(@RequestBody String param){
		AntResult antResult = new AntResult();
		if(param!=null && !"".equals(param.trim())){
			try {
				JSONObject json = JSONObject.parseObject(param);
				return service.confirmOrder(json);
			} catch (Exception e) {
				antResult.setType(AntType.ANT_206);
				return AntResponse.response(antResult);
			}
		}
		antResult.setType(AntType.ANT_204);
		return AntResponse.response(antResult);
	}
}

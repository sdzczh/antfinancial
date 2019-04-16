package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.base.BaseController;
import com.ant.service.inte.OrderService;

/**
 * 买入卖出记录管理
 * @author lina
 * 2017-6-17
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 买卖记录查询
	 * @param map
	 * @param queryType "":买卖都过滤，0：买入过滤，1：卖出过滤
	 * @param userAccount
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param amount
	 * @return
	 */
	@RequestMapping("orderQuery.action")
	private String orderQuery(Map<String, Object> map,String queryType,String userAccount,String createTimeStart,String createTimeEnd,String remain){
		map.put("queryType",queryType );
		map.put("userAccount",userAccount );
		map.put("createTimeStart", createTimeStart);
		map.put("createTimeEnd",createTimeEnd );
		map.put("remain", remain);		
		orderService.orderQuery(map,page,rows);
		return"order/orderMatch";
	}
	
	
	/** 
	 * 匹配订单
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("orderMatch.action")
	private Map<String, Object> orderMatch(String buyIds,String saleIds,String remarkVal){
		return orderService.matchOrder(buyIds,saleIds, remarkVal);
	}
	

	@ResponseBody
	@RequestMapping("orderDelete.action")
	private Map<String, Object> orderDelete(String buyIds,String saleIds){
		return orderService.deleteOrder(buyIds,saleIds);
	}
	
}

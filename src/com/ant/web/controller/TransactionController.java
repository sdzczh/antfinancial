package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.base.BaseController;
import com.ant.service.inte.TransactionService;

/**
 * 交易记录管理
 * @author lina
 * 2017-6-17
 */
@Controller
@RequestMapping("transaction")
public class TransactionController extends BaseController{
	@Autowired
	private TransactionService transactionService;
	
	
	/**
	 * 查询订单记录
	 * @param map
	 * @return
	 */
	@RequestMapping("transactionQuery.action")
	public String transactionQuery(Map<String, Object> map,String buyAccount,String saleAccount,String state,String createTimeStart,String createTimeEnd){
		map.put("buyAccount", buyAccount);
		map.put("saleAccount",saleAccount );
		map.put("state", state);
		map.put("createTimeStart", createTimeStart);
		map.put("createTimeEnd", createTimeEnd);
		transactionService.transactionQuery(map, page, rows);
		return "transaction/transactionQuery";
	}
	 
	/**
	 * 取消订单
	 * @param transId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="cancelOrder.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public Map<String, Object> cancelOrder(Integer transId){
		return transactionService.cancelOrder(transId);
	}
	
	/**
	 * 确认收款
	 * @param transId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="confirmCollection.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public Map<String, Object> confirmCollection(Integer transId){
		return transactionService.confirmCollection(transId);
	}

}

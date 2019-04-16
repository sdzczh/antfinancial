package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ant.base.BaseController;
import com.ant.service.inte.CapitalFlowService;

/**
 * 交易流水
 * 
 * @author lina 2017-07-03
 */

@Controller
@RequestMapping("/capitalFlow")
public class CapitalFlowController extends BaseController {

	@Autowired
	private CapitalFlowService capitalFlowService;

	/**
	 * 查询交易流失
	 * @param map
	 * @param userAccount
	 * @param packageType
	 * @param type
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return
	 */
	@RequestMapping("capitalFlowQuery.action")
	public String capitalFlowQuery(Map<String, Object> map, String userAccount,
			String packageType, String type, String createTimeStart,
			String createTimeEnd) {
		map.put("userAccount",userAccount );
		map.put("packageType",packageType );
		map.put("type", type);
		map.put("createTimeStart",createTimeStart );
		map.put("createTimeEnd", createTimeEnd);
		capitalFlowService.capitalFlowQuery(map, page, rows);
		return "capitalFlow/capitalFlowQuery";
	}

}

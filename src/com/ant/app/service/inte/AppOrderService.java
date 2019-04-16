package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 定单接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-19
 */
public interface AppOrderService {

	/**
	 * @描述 买入定单提交<br>
	 * @param json JSON格式参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public String submitOrderBuy(JSONObject json);
	
	/**
	 * @描述 卖出定单提交<br>
	 * @param json JSON格式参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public String submitOrderSale(JSONObject json);
	
	/**
	 * @描述 根据用户ID获取买、卖金币的定单<br>
	 * @param json JSON格式参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */	
	public String getOrderByUserId(JSONObject json);
	
	/**
	 * @描述 根据ID查询定单详情<br>
	 * @param json JSON格式参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-20
	 */
	public String getOrderInfoById(JSONObject json);
	
	/**
	 * @描述 根据用户ID查询交易记录<br>
	 * @param json JSON格式参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-22
	 */
	public String getOrderRecords(JSONObject json);
	
	/**
	 * @描述 确认打款,确认收款<br>
	 * @param json JSON格式参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-23
	 */
	public String confirmOrder(JSONObject json);
}

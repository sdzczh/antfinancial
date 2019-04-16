package com.ant.service.inte;

import java.util.List;
import java.util.Map;

import com.ant.pojo.Order;

/**
 * 买入卖出记录接口
 * @author lina
 *
 */
public interface OrderService {

	/**
	 * 同时查询买入卖出记录
	 * @param map 过滤参数
	 * @param page
	 * @param rows
	 * @return
	 */
	public Map<String, Object> orderQuery(Map<String, Object> map,Integer page,Integer rows);

	/**
	 * 匹配订单
	 * @param map
	 * @param buyIds
	 * @param saleIds
	 * @param remark
	 * @return
	 */
	public Map<String, Object> matchOrder(String buyIds,String  saleIds,String remark);
	
	/**
	 * 删除买入卖出申请
	 * @param buyIds
	 * @param saleIds
	 * @return
	 */
	public Map<String, Object> deleteOrder(String buyIds,String  saleIds);
	
	/**
	 * 根据多个id查找订单
	 * @param ids
	 * @return
	 */
	public List<Order> queryByIds(String ids);
	
	/**
	 * 根据ID查找订单
	 * @return
	 */
	public Order queryOne(Integer id);
	
	/**
	 * 按照用户ID查询未完成的订单
	 * @param userId
	 * @return
	 */
	public List<Order> queryUnfinishedByUserId(Integer userId);
	
	
	
}

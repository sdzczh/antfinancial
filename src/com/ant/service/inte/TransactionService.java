package com.ant.service.inte;

import java.util.List;
import java.util.Map;

import com.ant.pojo.OrderTransaction;

/**
 * 交易记录接口
 * @author lina
 *
 */
public interface TransactionService {
	
	
	/**
	 * 查询订单记录
	 * @param map
	 * @param page
	 * @param rows
	 * @return
	 */
	public Map<String, Object> transactionQuery(Map<String, Object> map,Integer page,Integer rows);
	
	/**
	 * 取消订单
	 * @param transId
	 * @return
	 */
	public Map<String, Object> cancelOrder(Integer transId);
	
	/**
	 * 确认收款
	 * @param transId
	 * @return
	 */
	public Map<String, Object> confirmCollection(Integer transId);
	
	/**
	 * 按照ID查找
	 * @param id
	 * @return
	 */
	public OrderTransaction queryOne(Integer id);

	/**
	 * 按照买入订单ID查找未完成的交易
	 * @param buyId
	 * @return
	 */
	public List<OrderTransaction> queryUnfinishedByBuyId(Integer buyId,Integer saleId);
	
	/**
	 * 按照卖出订单ID查找未完成的交易
	 * @param saleId
	 * @return
	 */
	public List<OrderTransaction> queryUnfinishedBySaleId(Integer buyId,Integer saleId);
	
	/**
	 * 根据用户ID查找作为买家的未完成交易记录
	 * @param buyUserId
	 * @return
	 */
	public List<OrderTransaction> queryUnfinishedByBuyUserId(Integer buyUserId);
	
	/**
	 * 根据用户ID查找作为卖家的未完成交易记录
	 * @param saleUserId
	 * @param states states 状态以“,”为分割的字符串
	 * @return
	 */
	public List<OrderTransaction> queryUnfinishedBySaleUserId(Integer saleUserId);
}

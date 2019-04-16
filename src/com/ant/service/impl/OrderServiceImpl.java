package com.ant.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.Order;
import com.ant.pojo.OrderTransaction;
import com.ant.pojo.User;
import com.ant.service.inte.AccountService;
import com.ant.service.inte.OrderService;
import com.ant.service.inte.SMSCommonService;
import com.ant.service.inte.TransactionService;
import com.ant.service.inte.UserManageService;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;
import com.ant.util.StrUtils;

/**
 * 买入卖出记录接口实现
 * 
 * @author lina
 * 
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private BaseDaoI dao;
	@Autowired
	private UserManageService userManageService;
	@Autowired
	private SMSCommonService smsCommonService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AccountService accountService;

	public Map<String, Object> orderQuery(Map<String, Object> map,
			Integer type, Integer page, Integer rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		StringBuffer orderQuerySql = new StringBuffer();
		orderQuerySql.append("select ");
		orderQuerySql.append("n.* ,u.userAccount ,u.userCode ");
		orderQuerySql.append("from t_order n ");
		orderQuerySql.append("left join t_user u on n.userId = u.id ");
		orderQuerySql.append("where ");
		orderQuerySql.append("n.type = :type ");
		orderQuerySql.append("and n.state=0  ");
		if(!StrUtils.isBlank((String)map.get("userAccount"))){
			orderQuerySql.append("and u.userAccount like :userAccount ");
			params.put("userAccount", "%"+map.get("userAccount")+"%");
		}
		if(!StrUtils.isBlank((String)map.get("createTimeStart"))){
			orderQuerySql.append("and left(n.createDate,10) >= :createTimeStart ");
			params.put("createTimeStart", map.get("createTimeStart"));
		}
		if(!StrUtils.isBlank((String)map.get("createTimeEnd"))){
			orderQuerySql.append("and left(n.createDate,10) <= :createTimeEnd ");
			params.put("createTimeEnd", map.get("createTimeEnd"));
		}
		if(!StrUtils.isBlank((String)map.get("remain"))){
			orderQuerySql.append("and n.remain = :remain ");
			params.put("remain", map.get("remain"));
		}
		params.put("type", type);
		List<Map<String, Object>> orderList =  (List<Map<String, Object>>)dao.findBySql(
				orderQuerySql.toString(), params, page, rows);

		String countSql = "SELECT COUNT(id) FROM ( " + orderQuerySql.toString()
				+ " )z";
		BigInteger count = dao.countBySql(countSql, params);

		returnMap.put("count", count);
		returnMap.put("orderList", orderList);
		return returnMap;
	}

	@Override
	public Map<String, Object> orderQuery(Map<String, Object> map,Integer page,Integer rows) {
		Map<String, Object> buyMap = this.orderQuery(!"1".equals(map.get("queryType"))?map:new HashMap<String, Object>(), 0, page, rows);
		Map<String, Object> saleMap = this.orderQuery(!"0".equals(map.get("queryType"))?map:new HashMap<String, Object>(), 1, page, rows);
		BigInteger buyCount = (BigInteger)buyMap.get("count");
		BigInteger saleCount = (BigInteger)saleMap.get("count");
		
		map.put("buyOrderList", buyMap.get("orderList"));
		map.put("saleOrderList", saleMap.get("orderList"));
		map.put("count", buyCount.compareTo(saleCount)==1?buyCount:saleCount);
		map.put("page", page);
		map.put("rows", rows);
		return map;
	}

	@Override
	public Map<String, Object> matchOrder(String buyIds,String  saleIds,String remark){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Order> buyOrders = this.queryByIds(buyIds);
			List<Order> saleOrders = this.queryByIds(saleIds);
			List<String> accountList = new ArrayList<String>();

			if (saleOrders == null || saleOrders.isEmpty() ||saleOrders==null|| saleOrders.isEmpty()) {
				map.put("success", false);
				return map;
			} 
			
			Stack<Order> saleStack = new Stack<Order>();
			Collections.reverse(saleOrders);
			saleStack.addAll(saleOrders);
			for(int i=0;i<buyOrders.size();i++){
				//当卖出栈为空时，退出循环
				if(saleStack.isEmpty()){
					break;
				}
				//循环匹配订单
				else{
					recurMatchOrder(buyOrders.get(i),saleStack,remark,buyOrders.size()-1-i,accountList);
				}
			}
				
			// 调用短信接口通知买方 sendMsgsales
			smsCommonService.sendNotice(accountList);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);

		}

		return map;
	}

	/**
	 * 递归匹配买入订单
	 * @param buyOrder 买入订单
	 * @param saleOrder 卖出订单
	 * @param remark 备注
	 * @param rIndex 买入订单剩余个数
	 * @param accountList 需要短信通知的用户账号集合
	 */
	private void recurMatchOrder(Order buyOrder,Stack<Order> saleStack,String remark,Integer rIndex,List<String> accountList){
		
		//当卖出栈为空时，退出递归，并更新买入订单
		if(saleStack.isEmpty()){
			accountList.add(userManageService.queryOne(buyOrder.getUserId()).getUserAccount());
			dao.update(buyOrder);
			return;
		}
		Order saleOrder = saleStack.pop();
		double buyAmount = buyOrder.getRemain();
		double saleAmount = saleOrder.getRemain();
		//买入金额小于卖出金额.退出递归
		if(buyAmount<saleAmount){
			//保存匹配订单
			saveOrderTransaction(buyOrder,saleOrder,buyAmount,remark);
			//修改买入订单状态
			buyOrder.setRemain(0);
			buyOrder.setState(1);//0:正在匹配 1:匹配成功 2:完成 3:删除或取消
			dao.update(buyOrder);
			//卖出订单减去匹配金额，如果是最后一个买入订单，则更新库，否则入栈
			saleOrder.setRemain(MatchUtils.subtract(saleAmount, buyAmount));
			if(rIndex==0){
				accountList.add(userManageService.queryOne(saleOrder.getUserId()).getUserAccount());
				dao.update(saleOrder);
			}else{				
				saleStack.push(saleOrder);
			}
			accountList.add(userManageService.queryOne(buyOrder.getUserId()).getUserAccount());
			return;
		}
		//买入金额等于卖出金额，退出递归
		else if(buyAmount==saleAmount){
			//保存匹配订单
			saveOrderTransaction(buyOrder,saleOrder,buyAmount,remark);
			//修改买入订单状态
			buyOrder.setRemain(0);
			buyOrder.setState(1);//0:正在匹配 1:匹配成功 2:完成 3:删除或取消
			dao.update(buyOrder); 
			//修改卖出订单状态
			saleOrder.setRemain(0);
			saleOrder.setState(1);//0:正在匹配 1:匹配成功 2:完成 3:删除或取消
			dao.update(saleOrder);
			accountList.add(userManageService.queryOne(buyOrder.getUserId()).getUserAccount());
			accountList.add(userManageService.queryOne(saleOrder.getUserId()).getUserAccount());
			return;
		}
		//买入金额大于卖出金额，递归调用recurMatchOrder
		else{
			//保存匹配订单
			saveOrderTransaction(buyOrder,saleOrder,saleAmount,remark);
			//修改当前卖出订单状态
			saleOrder.setRemain(0);
			saleOrder.setState(1);//0:正在匹配 1:匹配成功 2:完成 3:删除或取消
			dao.update(saleOrder);
			//买入订单减去卖出订单金额
			buyOrder.setRemain(MatchUtils.subtract(buyAmount, saleAmount));
			accountList.add(userManageService.queryOne(saleOrder.getUserId()).getUserAccount());
			recurMatchOrder(buyOrder,saleStack,remark,rIndex ,accountList);
			
		}
	}
	
	/**
	 * 保存匹配订单
	 * @param buyOrder 买入订单
	 * @param saleOrder 卖出订单
	 * @param amount 匹配金额
	 * @param remark 备注
	 */
	private void saveOrderTransaction(Order buyOrder,Order saleOrder,double amount,String remark){
		OrderTransaction tran = new OrderTransaction();
		tran.setBuyId(buyOrder.getId());
		tran.setBuyUserId(buyOrder.getUserId());
		tran.setSaleId(saleOrder.getId());
		tran.setSaleUserId(saleOrder.getUserId());
		tran.setAmount(amount);
		tran.setState(0);//0:等待付款 1:确认打款,2:确认收款 3:已完成
		tran.setRemark(remark);
		tran.setCreateDate(DateUtils.getCurrentDateStr());
		tran.setCreateTime(DateUtils.getCurrentTimeStr());
		tran.setUpdateTime(DateUtils.getCurrentTimeStr());
		dao.save(tran);
	}
	
	@Override
	public List<Order> queryByIds(String ids) {
		StringBuffer orderQuerySql = new StringBuffer();
		orderQuerySql.append("from Order ");
		orderQuerySql.append("where ");
		orderQuerySql.append("id in (" + ids + " ) ");
		List<Order> orderList = dao.find(orderQuerySql.toString());
		return orderList;
	}

	@Override
	public Order queryOne(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		StringBuffer orderQuerySql = new StringBuffer();
		orderQuerySql.append("select");
		orderQuerySql.append("*");
		orderQuerySql.append("from t_order ");
		orderQuerySql.append("where ");
		orderQuerySql.append("id= :id ");

		List<Order> list = dao.findBySql(Order.class, orderQuerySql.toString(),
				map);
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}


	@Override
	public Map<String, Object> deleteOrder(String buyIds, String saleIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {  
			
			if(!StrUtils.isBlank(buyIds)){
				List<Order> buyOrders = this.queryByIds(buyIds);
				for(Order buy :buyOrders){
					List<OrderTransaction> buyTrans = transactionService.queryUnfinishedByBuyId(buy.getId(),0);//买入方未完成的交易
					if(buyTrans!=null&&!buyTrans.isEmpty()){
						map.put("success", false);
						map.put("errorCode", "01");
						map.put("errorMsg", userManageService.queryOne(buy.getUserId()).getUserAccount()+",存在未完成订单，请检查！");
						return map;
					}else{
						buy.setRemain(0);
						buy.setState(3);//0:正在匹配 1:匹配成功 2:完成 3:删除或取消
						dao.update(buy);
					}
				}
				
			}
			if(!StrUtils.isBlank(saleIds)){
				List<Order> saleOrders = this.queryByIds(saleIds);
				for(Order sale :saleOrders){
					User user = userManageService.queryOne(sale.getUserId());
					List<OrderTransaction> saleTrans = transactionService.queryUnfinishedBySaleId(0,sale.getId());//买入方未完成的交易
					if(saleTrans!=null&&!saleTrans.isEmpty()){
						map.put("success", false);
						map.put("errorCode", "01");
						map.put("errorMsg", user.getUserAccount()+",存在未完成订单，请检查！");
						return map;
					}else{
						
						//增加相应钱包的金币
						Account account = accountService.queryByUserId(sale.getUserId(),null);
						switch (sale.getPackageType()) {
						case 0: account.setPackageJ(MatchUtils.add(account.getPackageJ(),sale.getRemain()));
								break;
						case 1: account.setPackageD(MatchUtils.add(account.getPackageD(),sale.getRemain()));
								break;
						case 2: account.setPackageZ(MatchUtils.add(account.getPackageZ(),sale.getRemain()));
								break;
						default:
							break;
						}
						dao.update(account);
						
						//添加交易流水记录
						CapitalFlow flow = new CapitalFlow();
						flow.setUserId(sale.getUserId());
						flow.setCreateDateTime(DateUtils.getCurrentTimeStr());
						flow.setAmount(sale.getRemain());
						flow.setPackageType(sale.getPackageType());
						flow.setType(10);//取消交易
						dao.save(flow);
						
						//取消卖出申请
						sale.setRemain(0);
						sale.setState(3);//0:正在匹配 1:匹配成功 2:完成 3:删除或取消
						dao.update(sale);
					}
				}
			}
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		
		return map;
	}

	@Override
	public List<Order> queryUnfinishedByUserId(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		StringBuffer ordersQuerySql = new StringBuffer();
		ordersQuerySql.append("select ");
		ordersQuerySql.append("* ");
		ordersQuerySql.append("from t_order ");
		ordersQuerySql.append("where ");
		ordersQuerySql.append("userId = :userId ");
		ordersQuerySql.append("and state <2 ");
		
		return  dao.findBySql(Order.class,ordersQuerySql.toString(), map);
	}
}

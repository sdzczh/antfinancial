package com.ant.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.Order;
import com.ant.pojo.OrderTransaction;
import com.ant.service.inte.AccountService;
import com.ant.service.inte.OrderService;
import com.ant.service.inte.TransactionService;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;
import com.ant.util.StrUtils;

/**
 * 交易记录接口实现
 * @author lina
 *
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private BaseDaoI dao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AccountService accountService;

	@Override
	public Map<String, Object> transactionQuery(Map<String, Object> map,Integer page,Integer rows) {
		StringBuffer transQuerySql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		transQuerySql.append("select ");
		transQuerySql.append("trans.*,buy.userAccount as buyAccount,sale.userAccount as saleAccount ");
		transQuerySql.append("from t_order_transaction trans ");
		transQuerySql.append("left join t_user buy on trans.buyUserId = buy.id ");
		transQuerySql.append("left join t_user sale on trans.saleUserId = sale.id ");
		transQuerySql.append("where 1=1 ");
		if(!StrUtils.isBlank((String)map.get("buyAccount"))){
			transQuerySql.append("and buy.userAccount like :buyAccount ");
			params.put("buyAccount", "%"+map.get("buyAccount")+"%");
		}
		if(!StrUtils.isBlank((String)map.get("saleAccount"))){
			transQuerySql.append("and sale.userAccount like :saleAccount ");
			params.put("saleAccount", "%"+map.get("saleAccount")+"%");
		}
		if(!StrUtils.isBlank((String)map.get("state"))){
			transQuerySql.append("and trans.state = :state ");
			params.put("state", map.get("state"));
		}
		if(!StrUtils.isBlank((String)map.get("createTimeStart"))){
			transQuerySql.append("and trans.createDate >= :createTimeStart ");
			params.put("createTimeStart", map.get("createTimeStart"));
		}
		if(!StrUtils.isBlank((String)map.get("createTimeEnd"))){
			transQuerySql.append("and trans.createDate <= :createTimeEnd ");
			params.put("createTimeEnd", map.get("createTimeEnd"));
		}
		transQuerySql.append("order by state, id desc ");
		List<Map<String,Object>> transList = (List<Map<String,Object>>) dao.findBySql( transQuerySql.toString(),params, page, rows);
		
		String countSql = "SELECT COUNT(id) FROM ( "+transQuerySql.toString()+" )z";
		BigInteger count = dao.countBySql(countSql,params);
		
		map.put("count", count);
		map.put("transList", transList);
		map.put("page", page);
		map.put("rows", rows);
		return map;
	}

	@Override    
	public Map<String, Object> cancelOrder(Integer transId) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			OrderTransaction transaction = this.queryOne(transId);
			if(transaction==null){
				map.put("success", false);
			}else{
				List<OrderTransaction> buys = this.queryUnfinishedByBuyId(transaction.getBuyId(),transaction.getSaleId());//买入方未完成的交易
				Order buyOrder = orderService.queryOne(transaction.getBuyId());
				Order saleOrder = orderService.queryOne(transaction.getSaleId());
				//买入申请只有一条正在处理的交易，且状态为匹配完成时，置为已过期
				if(buys!=null&&buys.size()==0&&buyOrder.getState()==1){
					buyOrder.setState(2);//已过期
					dao.update(buyOrder);
				}
				//增加卖出申请的剩余金额，状态置为待分配
				saleOrder.setRemain(MatchUtils.add(saleOrder.getRemain(),transaction.getAmount() ));
				saleOrder.setState(0);//待分配
				dao.update(saleOrder);
				
				transaction.setState(3);//已取消
				dao.update(transaction);
				
				map.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		
		return map;
	}

	@Override
	public Map<String, Object> confirmCollection(Integer transId) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			OrderTransaction transaction = this.queryOne(transId);
			Order buyOrder = orderService.queryOne(transaction.getBuyId());
			Order saleOrder = orderService.queryOne(transaction.getSaleId());
			List<OrderTransaction> buys = this.queryUnfinishedByBuyId(transaction.getBuyId(),transaction.getSaleId());//买入方未完成的交易
			List<OrderTransaction> sales = this.queryUnfinishedBySaleId(transaction.getBuyId(),transaction.getSaleId());//卖出方未完成的交易
			
			//买入交易只有一条有效时，且买入申请为匹配成功，将order置为过期
			if(buys!=null&&buys.size()==0&&buyOrder.getState()==1){
				buyOrder.setState(2);//已过期
				dao.update(buyOrder);
			}
			//卖出交易只有一条有效时，且卖出申请为匹配成功，将order置为过期
			if(sales!=null&&sales.size()==0&&saleOrder.getState()==1){
				saleOrder.setState(2);//已过期
				dao.update(saleOrder);
			}
			//交易记录设置为确认收款
			transaction.setState(2);//已收款
			dao.update(transaction);
			
			//增加买方相应钱包金币
			Account buyAccount = accountService.queryByUserId(transaction.getBuyUserId(),null);
			switch (buyOrder.getPackageType()) {
				case 0: buyAccount.setPackageJ(MatchUtils.add(buyAccount.getPackageJ(),transaction.getAmount()));
						break;
				case 1: buyAccount.setPackageD(MatchUtils.add(buyAccount.getPackageD(),transaction.getAmount()));
						break;
				case 2: buyAccount.setPackageZ(MatchUtils.add(buyAccount.getPackageZ(),transaction.getAmount()));
						break;
				default:
					break;
			}
			dao.update(buyAccount);
			
			//添加交易流水记录
			CapitalFlow flow = new CapitalFlow();
			flow.setUserId(transaction.getBuyUserId());
			flow.setCreateDateTime(DateUtils.getCurrentTimeStr());
			flow.setAmount(transaction.getAmount());
			flow.setPackageType(buyOrder.getPackageType());
			flow.setType(4);
			flow.setBuyUserId(transaction.getSaleUserId());
			dao.save(flow);
			
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		
		return map;
	}

	@Override
	public OrderTransaction queryOne(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		StringBuffer transQuerySql = new StringBuffer();
		transQuerySql.append("select ");
		transQuerySql.append("* ");
		transQuerySql.append("from t_order_transaction ");
		transQuerySql.append("where ");
		transQuerySql.append("id= :id ");
		
		List<OrderTransaction> list = dao.findBySql(OrderTransaction.class,transQuerySql.toString(), map);
		if(list==null || list.isEmpty()){			
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public List<OrderTransaction> queryUnfinishedByBuyId(Integer buyId,Integer saleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyId", buyId);
		StringBuffer transQuerySql = new StringBuffer();
		transQuerySql.append("select ");
		transQuerySql.append("* ");
		transQuerySql.append("from t_order_transaction ");
		transQuerySql.append("where ");
		transQuerySql.append("buyId = :buyId ");
		
		if(saleId!=0){
			map.put("saleId", saleId);
			transQuerySql.append("and saleId != :saleId ");
		}
		
		transQuerySql.append("and state <2 ");
		
		return  dao.findBySql(OrderTransaction.class,transQuerySql.toString(), map);

	}

	@Override
	public List<OrderTransaction> queryUnfinishedBySaleId(Integer buyId,Integer saleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleId", saleId);
		StringBuffer transQuerySql = new StringBuffer();
		transQuerySql.append("select ");
		transQuerySql.append("* ");
		transQuerySql.append("from t_order_transaction ");
		transQuerySql.append("where ");
		transQuerySql.append("saleId = :saleId ");
		
		if(buyId!=0){
			map.put("buyId", buyId);
			transQuerySql.append("and buyId != :buyId ");
		}
		
		transQuerySql.append("and state <2 ");
		
		return  dao.findBySql(OrderTransaction.class,transQuerySql.toString(), map);

	}

	@Override
	public List<OrderTransaction> queryUnfinishedByBuyUserId(Integer buyUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyUserId", buyUserId);
		StringBuffer transQuerySql = new StringBuffer();
		transQuerySql.append("select ");
		transQuerySql.append("* ");
		transQuerySql.append("from t_order_transaction ");
		transQuerySql.append("where ");
		transQuerySql.append("buyUserId = :buyUserId ");
		transQuerySql.append("and state <2 ");
		
		return  dao.findBySql(OrderTransaction.class,transQuerySql.toString(), map);
	}

	@Override
	public List<OrderTransaction> queryUnfinishedBySaleUserId(Integer saleUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleUserId", saleUserId);
		StringBuffer transQuerySql = new StringBuffer();
		transQuerySql.append("select ");
		transQuerySql.append("* ");
		transQuerySql.append("from t_order_transaction ");
		transQuerySql.append("where ");
		transQuerySql.append("saleUserId = :saleUserId ");
		transQuerySql.append("and state <2");
		
		return  dao.findBySql(OrderTransaction.class,transQuerySql.toString(), map);
	}
}

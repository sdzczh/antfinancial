package com.ant.app.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntSystemParams;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppOrderService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.CapitalFlow;
import com.ant.pojo.ManageProfitHistory;
import com.ant.pojo.Order;
import com.ant.pojo.OrderTransaction;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;
import com.ant.util.DateUtils;
import com.ant.util.MatchUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;
/**
 * @描述 定单接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-19
 */
@Transactional
@SuppressWarnings("unchecked")
@Service
public class AppOrderServiceImpl implements AppOrderService{

	@Autowired
	BaseDaoI dao;
	@Autowired
	private CommonService common;
	
	public String submitOrderBuy(JSONObject json) {//TODO  买入定单提交
		try {
			AntResult result = new AntResult();
			/*参数抽取*/
			String userIdStr = json.getString("userId");
			String amountStr = json.getString("amount");
			if(userIdStr==null || "".equals(userIdStr.trim())||amountStr==null || "".equals(amountStr.trim())){
				result.setType(AntType.ANT_204);
				return AntResponse.response(result);
			}
			
			User user = common.getUserByUserId(userIdStr);
			result = common.checkUserLoginState(result, user);
			if(!result.getType().equals(AntType.ANT_100)){
				return AntResponse.response(result);
			}
			
			Double amount = Double.parseDouble(amountStr);
			Integer userId = Integer.parseInt(userIdStr);
			
			String getCountHql = "from Account where userId=:userId";
			Map<String,Object> getCountHqlParams = new HashMap<String, Object>();
			getCountHqlParams.put("userId", userId);
			Account account = dao.findUnique(getCountHql,getCountHqlParams);
			if(account==null){
				result.setType(AntType.ANT_208);
				return AntResponse.response(result);
			}
			
			/*判断是否绑定银行卡或支付宝信息*/
			String alipayNumber = account.getAlipayNumber();
			String bankNumber = account.getBankNumber();
			String bankName = account.getBankName();
			
			if((alipayNumber==null||"".equals(alipayNumber))&&((bankName==null||"".equals(bankName))||(bankNumber==null||"".equals(bankNumber)))){
				result.setType(AntType.ANT_207);
				return AntResponse.response(result);
			}
			
			if(common.checkMaxAmount(Integer.parseInt("0"), userId)){
				result.setType(AntType.ANT_306);
				return AntResponse.response(result);
			}
			
			/*定单保存*/
			String dateTime = DateUtils.getCurrentTimeStr();
			Order order = new Order();
			order.setAmount(amount);
			order.setCreateDate(dateTime);
			order.setState(0);
			order.setType(0);
			order.setUserId(userId);
			order.setPackageType(0);
			order.setRemain(amount);
			
			String orderCode=null;
			boolean checkFlg = false;
			while(!checkFlg){
				orderCode = "A"+StrUtils.getCode(8);
				checkFlg = common.checkOrderCode(orderCode);
			}
			
			order.setOrderCode(orderCode);
			
			dao.save(order);

			user = common.getUserByUserId(userIdStr);
			
			JSONObject resJson = (JSONObject) JSONObject.toJSON(order);
			result.setData(resJson);
			result.setType(AntType.ANT_100);
			result = common.getContextParamsAndPackage(result,account,user);
			return AntResponse.response(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String submitOrderSale(JSONObject json) {//TODO 卖出定单提交
		try {
			AntResult result = new AntResult();
			/*参数抽取*/
			String userIdStr = json.getString("userId");
			String amountStr = json.getString("amount");
			String packageTypeStr = json.getString("packageType");
			String dateTime = DateUtils.getCurrentTimeStr();
			
			if(userIdStr==null || "".equals(userIdStr.trim())||amountStr==null || "".equals(amountStr.trim())||packageTypeStr==null || "".equals(packageTypeStr.trim())){
				result.setType(AntType.ANT_204);
				return AntResponse.response(result);
			}
			
			User user = common.getUserByUserId(userIdStr);
			result = common.checkUserLoginState(result, user);
			if(!result.getType().equals(AntType.ANT_100)){
				return AntResponse.response(result);
			}
			
			Integer userId = Integer.parseInt(userIdStr);
			Double amount = Double.parseDouble(amountStr);
			Integer packageType = Integer.parseInt(packageTypeStr);
			
			/*查询账号信息*/
			Account account = common.getAccountByUserId(userIdStr);
			if(account==null){
				result.setType(AntType.ANT_208);
				return AntResponse.response(result);
			}
			
			/*判断是否绑定银行卡或支付宝信息*/
			String alipayNumber = account.getAlipayNumber();
			String bankNumber = account.getBankNumber();
			String bankName = account.getBankName();
			
			if((alipayNumber==null||"".equals(alipayNumber))&&((bankName==null||"".equals(bankName))||(bankNumber==null||"".equals(bankNumber)))){
				result.setType(AntType.ANT_207);
				return AntResponse.response(result);
			}
			
			if(common.checkMaxAmount(Integer.parseInt("1"), userId)){
				result.setType(AntType.ANT_307);
				return AntResponse.response(result);
			}
			
			/*判断用户增值包等级*/
			int accountPackageNum = account.getPackageNum();
			String level=null;
			if(accountPackageNum>=0&&accountPackageNum<=5){
				level = AntSystemParams.POUNDAGE_LEVEL_5;
			}
			if(accountPackageNum>=6&&accountPackageNum<=10){
				level = AntSystemParams.POUNDAGE_LEVEL_4;
			}
			if(accountPackageNum>=11&&accountPackageNum<=15){
				level = AntSystemParams.POUNDAGE_LEVEL_3;
			}
			if(accountPackageNum>=16&&accountPackageNum<=19){
				level = AntSystemParams.POUNDAGE_LEVEL_2;
			}
			if(accountPackageNum>=20){
				level = AntSystemParams.POUNDAGE_LEVEL_1;
			}
			
			
			/*查询卖出手续费*/
			SystemParam saleInfo = common.getValByKey(level);
			Double salePoundage = Double.parseDouble(saleInfo.getVal());
			Double allAmount = MatchUtils.add(amount, MatchUtils.multiply(amount, salePoundage));

			/*判断钱包金币是否充足*/
			Double userAmount =account.getPackageD();
			if(packageType==0){
				userAmount = account.getPackageJ();
			}
			if(userAmount<allAmount){
				result.setType(AntType.ANT_106);
				return AntResponse.response(result);
			}

			/*查询特殊账号*/
			String getAdminHql = "from User where userRole=:userRole";
			Map<String,Object> getAdminHqlParams = new HashMap<String, Object>();
			getAdminHqlParams.put("userRole", Integer.parseInt("2"));
			User adminUser = dao.findUnique(getAdminHql, getAdminHqlParams);
			if(adminUser!=null){//向特殊账号添加金币
				String getadminAccount = "from Account where userId=:userId";
				Map<String,Object> getAdminAccountParams = new HashMap<String, Object>();
				getAdminAccountParams.put("userId", adminUser.getId());
				Account adminAccount = dao.findUnique(getadminAccount, getAdminAccountParams);
				if(adminAccount!=null){
					ManageProfitHistory history = new ManageProfitHistory();
					history.setCreateDate(dateTime);
					history.setProfit(allAmount);
					history.setUserId(adminAccount.getUserId());
					history.setType(1);
					adminAccount.setProfit(MatchUtils.add(adminAccount.getProfit(), allAmount));
					dao.update(adminAccount);
				}
			}
			
			/*定单提交*/
			Order order = new Order();
			order.setAmount(amount);
			order.setCreateDate(dateTime);
			order.setPackageType(packageType);
			order.setState(0);
			order.setType(1);
			order.setUserId(userId);
			order.setRemain(amount);
			
			String orderCode=null;
			boolean checkFlg = false;
			while(!checkFlg){
				orderCode = "P"+StrUtils.getCode(8);
				checkFlg = common.checkOrderCode(orderCode);
			}
			
			order.setOrderCode(orderCode);
			
			dao.save(order);
			
			/*更新用户钱包*/
			if(packageType==0){
				account.setPackageJ(MatchUtils.subtract(account.getPackageJ(), allAmount));
			}else{
				account.setPackageD(MatchUtils.subtract(account.getPackageD(), allAmount));
			}
			dao.update(account);
			
					
			/*添加卖出金币流水记录*/
			CapitalFlow cap = new CapitalFlow();
			cap.setAmount(amount);
			cap.setCreateDateTime(dateTime);
			cap.setPackageType(packageType);
			cap.setType(5);
			cap.setUserId(userId);
			cap.setFee(MatchUtils.multiply(amount, salePoundage));
			dao.save(cap);
		
			JSONObject resJson = (JSONObject) JSONObject.toJSON(order);
			result.setPackageJ(account.getPackageJ());
			result.setPackageD(account.getPackageD());
			result.setPackageZ(account.getPackageZ());
			result.setData(resJson);
			result.setType(AntType.ANT_100);
			result = common.getContextParamsAndPackage(result,account,user);
			return AntResponse.response(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getOrderByUserId(JSONObject json){//TODO 根据用户ID获取买、卖金币的定单
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userIdStr = json.getString("userId");
			if(userIdStr==null || "".equals(userIdStr.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(userIdStr);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer userId = Integer.parseInt(userIdStr);
			Order order = checkExistOrder(userId);
			if(order==null){
				antResult.setExist(0);
			}else{
				antResult.setExist(1);
				antResult.setOrderType(order.getType());
				
				if(order.getState()==0){
					order.setAmount((new BigDecimal(order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					List<Order> orders = new ArrayList<Order>();
					orders.add(order);
					JSONArray  orderJson = (JSONArray) JSON.toJSON(orders);
					antResult.setOrder(orderJson);
				}else{
					antResult.setOrder(null);
				}
				
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT tor.id orderId,tot.id,tot.amount,tot.updateTime,tot.state,tu.userAccount ");
				sql.append("FROM t_order_transaction tot ");
				sql.append("LEFT JOIN t_order tor ON (CASE tor.type WHEN 0 THEN tor.id = tot.buyId ELSE tor.id = tot.saleId END) ");
				sql.append("LEFT JOIN t_user tu ON (CASE tor.type WHEN 0 THEN tu.id = tot.saleUserId ELSE tu.id = tot.buyUserId END) ");
				sql.append("WHERE tor.id = :id ");
				sql.append("AND tot.state<:state ");
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("id", order.getId());
				params.put("state", 3);
				
				List<?> results = dao.findBySql(sql.toString(), params);
				for (Object object : results) {
					Map<String,Object> map = (Map<String, Object>) object;
					map.put("amount", (new BigDecimal(map.get("amount").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
				}
				JSONArray data = (JSONArray) JSON.toJSON(results);
				antResult.setData(data);
			}
			antResult = common.getContextParams(antResult, userIdStr);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getOrderInfoById(JSONObject json){//TODO 查询定单详情
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String id = json.getString("id");
			String orderId = json.getString("orderId");
			if(StrUtils.checkNull(id)|| StrUtils.checkNull(orderId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			String userId = WebUtils.getRequest().getHeader("userId");
			if(StrUtils.checkNull(userId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			User user = common.getUserByUserId(userId);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT tor.type,tot.id,tor.id orderId,tu.userName,tu.userAccount,ta.bankName,ta.bankNumber,ta.alipayNumber,ta.webcatNumber,tot.imgSrc,tot.state,tot.amount ");
			sql.append("FROM t_order_transaction tot ");
			sql.append("LEFT JOIN t_order tor ON (CASE tor.type WHEN 0 THEN tor.id = tot.buyId ELSE tor.id = tot.saleId END) ");
			sql.append("LEFT JOIN t_user tu ON (CASE tor.type WHEN 0 THEN tu.id = tot.saleUserId ELSE tu.id = tot.buyUserId END) ");
			sql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			sql.append("WHERE tot.id = :id ");
			sql.append("AND tor.id = :orderId ");
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("orderId", orderId);
			
			List<?> results = dao.findBySql(sql.toString(), params);
			for (Object object : results) {
				Map<String,Object> map = (Map<String, Object>) object;
				map.put("amount", (new BigDecimal(map.get("amount").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
			}
			
			Map<String,Object> info = (Map<String, Object>) results.get(0);
			JSONObject data = (JSONObject) JSON.toJSON(info);

			antResult.setData(data);
			antResult = common.getContextParams(antResult, null);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getOrderRecords(JSONObject json) {//TODO 交易记录
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = json.getString("userId");
			String pageStr = json.getString("page");
			String rowsStr = json.getString("rows");
			
			if(StrUtils.checkNull(userId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(userId);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}

			
			Integer page = StrUtils.checkNull(pageStr)?0:Integer.parseInt(pageStr);
			Integer rows = StrUtils.checkNull(rowsStr)?10:Integer.parseInt(rowsStr);
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT tor.id orderId,tor.orderCode,tor.type,tu.userAccount,tu.userName,tot.updateTime,tot.amount,tot.state,tot.id ");
			sql.append("FROM t_order_transaction tot ");
			sql.append("LEFT JOIN t_order tor ON (CASE tor.type WHEN 0 THEN tor.id = tot.buyId ELSE tor.id = tot.saleId END) ");
			sql.append("LEFT JOIN t_user tu ON (CASE tor.type WHEN 0 THEN tu.id = tot.saleUserId ELSE tu.id = tot.buyUserId END) ");
			sql.append("WHERE tor.userId=:userId ");
			sql.append("AND tot.state=:stateComfirm "); 
			sql.append("OR tot.state=:stateConsole "); 
			sql.append("ORDER BY tot.createTime DESC ");
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("stateComfirm", 2);
			params.put("stateConsole", 3);
			
			List<?> results = dao.findBySql(sql.toString(), params, page, rows);
			for (Object object : results) {
				Map<String,Object> map = (Map<String, Object>) object;
				map.put("amount", (new BigDecimal(map.get("amount").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
			}
			
			String countSql = "select count(z.id) from ("+sql.toString()+")z";
			BigInteger count = dao.countBySql(countSql,params);
			antResult.setCount(count.intValue());
			JSONArray data = (JSONArray) JSON.toJSON(results);
			antResult.setData(data);
			antResult = common.getContextParams(antResult,null);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	public String confirmOrder(JSONObject json) {//TODO 确认付款 收款
		AntResult antResult = new AntResult();
		try {
			String recordId = json.getString("recordId");
			String typeStr = json.getString("type");
			
			if(StrUtils.checkNull(recordId)|| StrUtils.checkNull(typeStr)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}			
			
			String userId = WebUtils.getRequest().getHeader("userId");
			if(StrUtils.checkNull(userId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			User user = common.getUserByUserId(userId);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}

			
			/*查询定单*/
			String hql = "from OrderTransaction where id = :id";
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("id", Integer.parseInt(recordId));
			OrderTransaction orderTransaction = dao.findUnique(hql, params);
			if(orderTransaction==null){
				antResult.setType(AntType.ANT_209);
				return AntResponse.response(antResult);
			}
			
			Integer type = Integer.parseInt(typeStr);
			
			if(type==0){//确认打款
				String imgSrc = json.getString("imgSrc");
				if(StrUtils.checkNull(imgSrc)){
					antResult.setType(AntType.ANT_302);
					return AntResponse.response(antResult);
				}
				orderTransaction.setImgSrc(WebUtils.getHttpPathWithPort()+imgSrc);
				antResult = confirmPay(orderTransaction);				
			}else{//确认收款
				antResult = confirmReceivable(orderTransaction);
			}
			
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @描述 确认付款<br>
	 * @param orderTransaction
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-23
	 */
	private AntResult confirmPay(OrderTransaction orderTransaction){//TODO 确认付款
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String updateTime = DateUtils.getCurrentTimeStr();
			orderTransaction.setState(1);
			orderTransaction.setUpdateTime(updateTime);
			dao.update(orderTransaction);
			orderTransaction.setAmount((new BigDecimal(orderTransaction.getAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
			JSONObject data = (JSONObject) JSON.toJSON(orderTransaction);
			antResult.setData(data);
			
			User user = common.getUserByUserId(orderTransaction.getBuyUserId().toString());
			Account account = common.getAccountByUserId(orderTransaction.getBuyUserId().toString());
			antResult = common.getContextParamsAndPackage(antResult, account, user);
			return antResult;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @描述 确认收款<br>
	 * @param orderTransaction
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-23
	 */
	private AntResult confirmReceivable(OrderTransaction orderTransaction){//TODO 确认收款
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			/*设置交易记录状态*/
			String updateTime = DateUtils.getCurrentTimeStr();
			orderTransaction.setUpdateTime(updateTime);
			orderTransaction.setState(2);
			dao.update(orderTransaction);
			
			/*买入用户金币重置*/
			Account account = common.getAccountByUserId(String.valueOf(orderTransaction.getBuyUserId()));
			if(account==null){
				antResult.setType(AntType.ANT_209);
				return antResult;
			}
			account.setPackageJ(MatchUtils.add(account.getPackageJ(), orderTransaction.getAmount()));
			dao.update(account);
			
			/*修改买方定单：修改交易定单余额;查询,如果还有未完成的交易，不做处理;如果没有，查询定单交易余额是否为0;如果是，设置过期;如果余额大于0，修改定单状态为未匹配*/
			StringBuffer buySql = new StringBuffer();
			buySql.append("SELECT COUNT(tot.id) ");
			buySql.append("FROM t_order_transaction tot ");
			buySql.append("WHERE ");
			buySql.append("tot.buyId = :buyId ");
			buySql.append("AND tot.state < :state ");
			buySql.append("AND tot.id != :totId ");
			
			Map<String,Object> buyParams = new HashMap<String, Object>();
			buyParams.put("buyId", orderTransaction.getBuyId());
			buyParams.put("state", 2);
			buyParams.put("totId", orderTransaction.getId().toString());
			BigInteger buyOrderCount = dao.countBySql(buySql.toString(), buyParams);
			Order buyOrder = dao.getById(Order.class, orderTransaction.getBuyId());
	//		buyOrder.setAmount(MatchUtils.subtract(buyOrder.getAmount(), orderTransaction.getAmount()));
			if(buyOrderCount.intValue()==0){
				if(buyOrder.getState()==1){
					buyOrder.setState(2);
				}
			}
			dao.update(buyOrder);
			
			/*修改卖方定单过期*/
			StringBuffer saleSql = new StringBuffer();
			saleSql.append("SELECT COUNT(tot.id) ");
			saleSql.append("FROM t_order_transaction tot ");
			saleSql.append("WHERE ");
			saleSql.append("tot.saleId = :saleId ");
			saleSql.append("AND tot.state < :state ");
			saleSql.append("AND tot.id != :totId ");
			
			Map<String,Object> saleParams = new HashMap<String, Object>();
			saleParams.put("saleId", orderTransaction.getSaleId());
			saleParams.put("state", 2);
			saleParams.put("totId", orderTransaction.getId().toString());
			BigInteger saleOrderCount = dao.countBySql(saleSql.toString(), saleParams);
			Order saleOrder = dao.getById(Order.class, orderTransaction.getSaleId());
//			saleOrder.setAmount(MatchUtils.subtract(saleOrder.getAmount(), orderTransaction.getAmount()));
			if(saleOrderCount.intValue()==0){
				if(saleOrder.getState()==1){
					saleOrder.setState(2);
				}
			}
			dao.update(saleOrder);
			
			/*记录买用户金币流水*/
			CapitalFlow cap = new CapitalFlow();
			cap.setAmount(orderTransaction.getAmount());
			cap.setCreateDateTime(updateTime);
			cap.setPackageType(0);
			cap.setType(4);
			cap.setUserId(account.getUserId());
			cap.setBuyUserId(orderTransaction.getSaleUserId());
			dao.save(cap);
			orderTransaction.setAmount((new BigDecimal(orderTransaction.getAmount()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
			JSONObject data = (JSONObject) JSON.toJSON(orderTransaction);
			antResult.setData(data);

			User saleUser = common.getUserByUserId(orderTransaction.getSaleUserId().toString());
			Account saleAccount = common.getAccountByUserId(orderTransaction.getSaleUserId().toString());
			antResult = common.getContextParamsAndPackage(antResult, saleAccount, saleUser);
			return antResult;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	
	/**
	 * @描述 获取定单<br>
	 * @param userId 用户Id
	 * @param type　0买入 1卖出
	 * @return 定单
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-20
	 */
	private Order checkExistOrder(Integer userId){
		String hql = "from Order where userId= :userId and state<:state";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("state", 2);
		Order order = dao.getByHql(hql, params);
		return order;
	}
}

package com.ant.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.base.BaseController;
import com.ant.pojo.Account;
import com.ant.pojo.Order;
import com.ant.pojo.User;
import com.ant.service.inte.AccountService;
import com.ant.service.inte.OrderService;
import com.ant.service.inte.UserManageService;
import com.ant.util.StrUtils;

/**
 * 账户管理
 * @author lina
 * 2017-6-17
 */

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController  {

	@Autowired
	private AccountService accountService;
	@Autowired
	UserManageService userManageService;
	@Autowired
	private OrderService  orderService;
	/**
	 * 解冻账户
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("activateUser")
	public Map<String, Object> activateUser(String userIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!StrUtils.isBlank(userIds)){
				String[] ids = userIds.split(",");
				for(String id :ids){
					Integer idInt = Integer.parseInt(id);
					Account account = accountService.queryByUserId(idInt,null);
					
					if(account!=null){
						if(account.getState()==0){//账户未激活
							map.put("success", false);
							map.put("errCode", "1");
							map.put("errMsg", userManageService.queryOne(idInt).getUserAccount()+"，账户未激活！");
							return map;
						}else if(account.getState()==1){//账户已激活
							map.put("success", false);
							map.put("errCode", "1");
							map.put("errMsg", userManageService.queryOne(idInt).getUserAccount()+"，账户已激活！");
							return map;
						}
						account.setState(1);
					}
					accountService.updateAccount(account);
				}
			}
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		
		return map;
	}
	
	/**
	 * 冻结账户
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("freezeUser")
	public Map<String, Object> freezeUser(String userIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(!StrUtils.isBlank(userIds)){
				String[] ids = userIds.split(",");
				for(String id :ids){
					Integer idInt = Integer.parseInt(id);
					User user = userManageService.queryOne(idInt);
					List<Order> buys = orderService.queryUnfinishedByUserId(idInt);//买入方未完成的交易
					List<Order> sales = orderService.queryUnfinishedByUserId(idInt);//卖出方未完成的交易
					if((buys!=null&&!buys.isEmpty())||(sales!=null&&!sales.isEmpty())){
						map.put("success", false);
						map.put("errCode", "1");
						map.put("errMsg", user.getUserAccount()+"，存在未完成的交易，请稍后操作！");
						return map;
					}else{
						Account account = accountService.queryByUserId(idInt,null);
						if(account.getState()==0){//账户未激活
							map.put("success", false);
							map.put("errCode", "1");
							map.put("errMsg", user.getUserAccount()+"，账户未激活！");
							return map;
						}else if(account.getState()==2){//账户已冻结
							map.put("success", false);
							map.put("errCode", "1");
							map.put("errMsg", user.getUserAccount()+"，账户已冻结！");
							return map;
						}
						if(account!=null){			
							account.setState(2);
						}
						 accountService.updateAccount(account);
					}
				}
			}
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}
}

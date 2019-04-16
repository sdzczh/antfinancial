package com.ant.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.base.BaseController;
import com.ant.glob.GolbParams;
import com.ant.pojo.Account;
import com.ant.pojo.User;
import com.ant.service.inte.AccountService;
import com.ant.service.inte.UserManageService;
import com.ant.util.WebUtils;

/**
 * @描述 用户登录<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-13
 */
@Controller
@RequestMapping("/manager/user")
public class UserManagController extends BaseController {

	@Autowired
	UserManageService service;

	@Autowired
	AccountService accountService;

	/**
	 * @描述 获取已删除的用户列表<br>
	 * @param map
	 *            参数对象
	 * @return 返回地址
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	@RequestMapping("getDeletedUserList.action")
	public String getDeletedUserList(Map<String, Object> map, String userCode, String userAccount,
			String userName, String referCode, String accountState) {
		map.put("userAccount", userAccount);
		map.put("userCode", userCode);
		map.put("userName", userName);
		map.put("referCode", referCode);
		map.put("accountState", accountState);
		
		service.getUserList(map, page, rows, 1);
		return "userManage/deletedUser";
	}

	
	/**
	 * @描述 获取人工充值列表<br>
	 * @param map
	 *            参数对象
	 * @return 返回地址
	 * @author 赵赫
	 * @版本 v1.0.0
	 * @日期 2017-7-1
	 */
	@RequestMapping("getUserRecharge.action")
	public String getUserRecharge(Map<String, Object> map,String userAccount,String userName) {
		map.put("userAccount", userAccount);
		map.put("userName",userName );
		service.getRechargeList(map, page, rows,0);
		return "recharge/userRecharge";
	}

	/**
	 * @描述 获取未删除的用户列表<br>
	 * @param map
	 *            参数对象
	 * @return 返回地址
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	@RequestMapping("getUserList.action")
	public String getUserList(Map<String, Object> map,String userAccount, String userCode,
			String userName, String referCode, String accountState,
			String createTimeStart, String createTimeEnd,
			String activeTimeStart, String activeTimeEnd) {
		map.put("userAccount", userAccount);
		map.put("userCode", userCode);
		map.put("userName", userName);
		map.put("referCode", referCode);
		map.put("accountState", accountState);
		map.put("createTimeStart", createTimeStart);
		map.put("createTimeEnd", createTimeEnd);
		map.put("activeTimeStart", activeTimeStart);
		map.put("activeTimeEnd", activeTimeEnd);
		service.getUserList(map, page, rows, 0);
		return "userManage/userQuery";
	}

	/**
	 * @描述 退出<br>
	 * @return
	 * @author 陈之晶
	 * @throws IOException
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	@RequestMapping("loginOut.action")
	public String loginOut() {
		service.loginOut();
		return "login/login";
	}

	/**
	 * @描述 管理员登录<br>
	 * @param user
	 *            参数对象
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	@ResponseBody
	@RequestMapping("adminLogin.action")
	public Map<String, Object> adminLogin(User user) {
		return service.adminLogin(user, request);
	}

	/**
	 * @描述 初始页面转发<br>
	 * @return 返回页面地址
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	@RequestMapping("dispatcher.action")
	public String dispatcher() {
		User user = (User) WebUtils.getSession().getAttribute(
				GolbParams.Manager);
		if (user != null) {
			return "redirect:/system/info.action";
		}
		return "login/login";
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @return 地址
	 */
	@RequestMapping("toUserAdd.action")
	public String toUserAdd() {
		return "userManage/userAdd";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping("userAdd.action")
	public Map<String, Object> userAdd(User user, Account account) {
		return service.addUserAndAccount(user, account, request);
	}

	/**
	 * 删除会员
	 * 
	 * @param userId
	 * @return 是否删除成功
	 */
	@ResponseBody
	@RequestMapping("userDelete.action")
	public Map<String, Object> userDelete(String userIds) {
		return service.userDelete(userIds);
	}

	/**
	 * 恢复会员
	 * 
	 * @param userId
	 * @return 是否恢复成功
	 */
	@ResponseBody
	@RequestMapping("userReply.action")
	public Map<String, Object> userReply(Integer userId) {
		User user = service.queryOne(userId);
		user.setIsDel(0);
		return service.userUpdate(user);
	}

	/**
	 * 跳转到修改会员
	 * 
	 * @param noticeId
	 * @return
	 */
	@RequestMapping("toUserModify.action")
	public String toUserModify(Map<String, Object> map, Integer userId,
			String currentUrl) {
		if (userId != null) {
			map.put("user", service.queryOne(userId));
			map.put("account", accountService.queryByUserId(userId,null));
		}
		map.put("currentUrl", currentUrl);
		return "userManage/userModify";
	}

	@ResponseBody
	@RequestMapping("userModify")
	public Map<String, Object> userModify(User user, Account account) {
		return service.userModify(user, account);
	}

	/**
	 * 永久删除已删除的会员
	 * 
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("userDeletePerm")
	public Map<String, Object> userDeletePerm(Integer userId) {
		return service.userDeletePerm(userId);
	}

	@ResponseBody
	@RequestMapping("checkAccount.action")
	public Map<String, Object> checkAccount(String referenceAccount) {
		User user = service.selectByUserAccount(referenceAccount);// 0:未删除 1:已删除
		Map<String, Object> map = new HashMap<String, Object>();
		if(user!=null){			
			map.put("success", true);
			return map; 
		}else
		map.put("success", false);
		return map;
	}

	/**
	 * 按基数添加金额
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping("addOneMoney.action")
	public String addOneMoney(Integer userId,String wallet){
		String s="+1";
		service.addOneMoney(userId,wallet,s);
		return "true";
	}
	/**
	 * 按基数减少金额
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping("delOneMoney.action")
	public String delOneMoney(Integer userId,String wallet){
		String s="-1";
		Account account=accountService.queryByUserId(userId,null);
		Double packageD=account.getPackageD();
		Double packageJ=account.getPackageJ();
		Double packageZ=account.getPackageZ();
		if(wallet.contains("packageD")){
			if(packageD.compareTo(0.0)==0){
				return "false";
			}else{
				service.addOneMoney(userId,wallet,s);
			}
		}else
		if(wallet.contains("packageJ")){
			if(packageJ.compareTo(0.0)==0){
				return "false";
			}else{
				service.addOneMoney(userId,wallet,s);
			}
		}else
		if(wallet.contains("packageZ")){
			if(packageZ.compareTo(0.0)==0){
				return "false";
			}else{
				service.addOneMoney(userId,wallet,s);
			}
		}
		return "true";
	}
	
	/**
	 * 自定义充值
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping("changeMoney.action")
	public String changeMoney(Integer userId,String wallet,String num){
		try {
			if(Integer.parseInt(num)>999999999||Integer.parseInt(num)==0){
				return "max";
			}
		} catch (NumberFormatException e) {
			return "error";
		}
		Account account=accountService.queryByUserId(userId,null);
		Double packageD=account.getPackageD();
		Double packageJ=account.getPackageJ();
		Double packageZ=account.getPackageZ();
		if(num.contains("-")){
			if(wallet.contains("packageD")){
				Double nums=Double.parseDouble(num);
				Double d=Math.abs(nums);
				if(packageD.compareTo(d)<0){
					return "false";
				}else{
					service.addOneMoney(userId,wallet,num);
				}
			}else
			if(wallet.contains("packageJ")){
				Double nums=Double.parseDouble(num);
				Double d=Math.abs(nums);
				if(packageJ.compareTo(d)<0){
					return "false";
				}else{
					service.addOneMoney(userId,wallet,num);
				}
			}else
			if(wallet.contains("packageZ")){
				Double nums=Double.parseDouble(num);
				Double d=Math.abs(nums);
				if(packageZ.compareTo(d)<0){
					return "false";
				}else{
					service.addOneMoney(userId,wallet,num);
				}
			}
		}else{
			num="+"+num;
			service.addOneMoney(userId,wallet,num);
		}
		return "true";
	}
	/**
	 * 修改账户身份为收益账号
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping("updateRole.action")
	public String updateRole(Integer userId){
		String s=service.updateRole(userId);
		if(s=="true"){
			return "true";
		}else
			return "false";
	}
	/**
	 * 撤销账户身份为收益账号
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping("revoke.action")
	public String revoke(Integer userId){
		service.revoke(userId);
			return "true";
	}
}

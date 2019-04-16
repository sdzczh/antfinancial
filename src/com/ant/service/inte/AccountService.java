package com.ant.service.inte;

import java.util.Map;

import com.ant.pojo.Account;

/**
 * 账户管理接口
 * @author lina
 *
 */
public interface AccountService {

	/**
	 * 根据用户ID查找账户信息
	 * @param userId
	 * @param 0:未激活，1:激活，2:冻结  null:全部
	 * @return
	 */
	public Account queryByUserId(Integer userId,Integer state);
	
	/**
	 * 修改账户信息
	 * @param account
	 * @return 修改结果
	 */
	public Map<String ,Object> updateAccount(Account account); 
}

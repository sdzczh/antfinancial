package com.ant.service.inte;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ant.pojo.Account;
import com.ant.pojo.User;

/**
 * @描述 用户管理接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
public interface UserManageService {
	
	/**
	 * @描述 获取已删除的用户列表<br>
	 * @param map 参数对象
	 * @param page 页数
	 * @param rows 行数
	 * @return 返回对象
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	public Map<String,Object> getUserList(Map<String,Object> map,Integer page,Integer rows,Integer isDel);
	/**
	 * @描述 退出<br>
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	public void loginOut();
	/**
	 * @描述 获取人工充值列表<br>
	 * @author 赵赫
	 * @版本 v1.0.0
	 * @日期 2017-7-1
	 */
	public Map<String,Object> getRechargeList(Map<String,Object> map,Integer page,Integer rows,Integer isDel);
	/**
	 * @描述 管理员登录<br>
	 * @param user 用户对象参数
	 * @param request 请求参数
	 * @return 返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-13
	 */
	public Map<String,Object> adminLogin(User user,HttpServletRequest request);
	
	/**
	 * @描述 添加用户<br>
	 * @param user 用户对象
	 * @return 返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public Map<String,Object> createUser(User user);
	
	/**
	 * 添加用户和账户信息
	 * @param user
	 * @param account
	 * @param request
	 * @return 是否添加成功
	 */
	public Map<String, Object> addUserAndAccount(User user,Account account,HttpServletRequest request); 
	
	/**
	 * 修改会员
	 * @param userId
	 * @return 修改标志
	 */
	public Map<String ,Object> userUpdate(User user);
	
	/**
	 * 根据ID查找用户
	 * @param userId
	 * @return
	 */
	public User queryOne(Integer userId);
	
	/**
	 * 爱找推荐人ID查找用户
	 * @param referenceId
	 * @return 用户列表
	 */
	public List<User> queryByReferenceId(Integer referenceId);
	
	/**
	 * 修改会员信息和账户信息
	 * @param user
	 * @param account
	 * @return 修改结果
	 */
	public Map<String , Object> userModify(User user,Account account);
	
	/**
	 * 永久删除会员
	 * @param userId
	 * @return 修改标志
	 */
	public Map<String ,Object> userDeletePerm(Integer userId);
	/**
	 * 删除会员-可恢复
	 * @param userIds 以,分割的id字符串
	 * @return 修改标志
	 */
	public Map<String ,Object> userDelete(String userIds);
	
	/**
	 * 按照用户名查找用户
	 * @param userAccount
	 * @return
	 */
	public User queryByUserAccount(String userAccount,Integer state);
	public User selectByUserAccount(String userAccount);
	/**
	 * 按照基数修改金额
	 * @param s 
	 * 
	 * @return
	 */
	public void addOneMoney(Integer userId, String wallet, String s);
	/**
	 * 修改账户身份为收益账号
	 * @param userId 
	 * @return
	 */
	public String updateRole(Integer userId);
	/**
	 * 撤销账户的收益账号身份
	 * @param userId 
	 * @return
	 */
	public String revoke(Integer userId);
	/**
	 * @描述 条件获取人工充值列表<br>
	 * @author 赵赫
	 * @版本 v1.0.0
	 * @日期 2017-7-5
	 */
	public void getRecharge(Map<String, Object> map, Integer page, Integer rows);
}

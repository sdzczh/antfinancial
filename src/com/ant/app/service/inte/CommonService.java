package com.ant.app.service.inte;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResult;
import com.ant.pojo.Account;
import com.ant.pojo.SystemParam;
import com.ant.pojo.User;

/**
 * @描述 公用接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-17
 */
public interface CommonService {

	/**
	 * @描述 根据ID获取用户信息<br>
	 * @param id 用户ID
	 * @return 用户信息
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public User getUserByUserId(String id);
	
	/**
	 * 
	 * @描述 根据用户ID获取账号信息<br>
	 * @param id 用户ID
	 * @return 账号信息
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public Account getAccountByUserId(String id);
	
	/**
	 * @描述 根据ID获取用户的所有信息<br>
	 * @param id 用户ID
	 * @return 用户信息及账号信息
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public Map<String,Object> getUserInfoByUserId(String id);

	/**
	 * @描述 根据推荐人ID查询已激活人数<br>
	 * @param id 推荐人ID
	 * @return 人数
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public int countActiveUsers(String id);
	

	/**
	 * @描述 根据用户ID查询用户增值包数量<br>
	 * @param userId 用户ID
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-3
	 */
	public int getPackageNumByUserId(Integer userId);
	
	/**
	 * @描述 根据Key值获取系统参数的值<br>
	 * @param key 值
	 * @return 值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public SystemParam getValByKey(String key);
	
	/**
	 * @描述 根据Key值获取系统参数的值<br>
	 * @param key 值
	 * @return 值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public String getValStrByKey(String key);
	
	/**
	 * @描述 检测CODE是否存在<br>
	 * @param code
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-4
	 */
	public boolean checkOrderCode(String code);

	/**
	 * @描述 检测用户信息<br>
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-5
	 */
	public AntResult checkUserState(JSONObject json);
	
	/**
	 * @描述 检测当月买入/卖出是否超出大值(审请金额总值)<br>
	 * @param type 类型 0:买入 1:卖出
	 * @param userId 用户ID
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-5
	 */
	public boolean checkMaxAmount(Integer type,Integer userId);
	
	/**
	 * @描述 检测当前用户收益状态信息<br>
	 * @param id 用户ID
	 * @return map down:距离下次下降天数  profit:当前收益指数 after:将要下降指数
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-17
	 */
	public Map<String,Object> checkUserProfit(String id);
	
	/**
	 * 
	 * @描述 获取通用参数<br>
	 * @param antResult 返回值对象
	 * @param id 可选参数,用户ID
	 * @return 
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-7
	 */
	public AntResult getContextParams(AntResult antResult,String id) ;
	
	/**
	 * 
	 * @描述 获取通用参数<br>
	 * @param antResult 返回值对象
	 * @param id 可选参数,用户ID
	 * @return 
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-7
	 */
	public AntResult getContextParamsLogin(AntResult antResult,String id) ;
	
	/**
	 * 
	 * @描述 获取通用参数<br>
	 * @param antResult 返回值对象
	 * @param id 可选参数,用户ID
	 * @return 
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-7
	 */
	public AntResult getContextParamsAndPackage(AntResult antResult,Account account,User user) ;
	
	/**
	 * 
	 * @描述 获取通用参数<br>
	 * @param antResult 返回值对象
	 * @param id 可选参数,用户ID
	 * @return 
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-7
	 */
	public AntResult getContextParamsAndPackageInfo(AntResult antResult,Account account,User user) ;
	
	/**
	 * 
	 * @描述 获取通用参数<br>
	 * @param antResult 返回值对象
	 * @param id 可选参数,用户ID
	 * @return 
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-7
	 */
	public AntResult getContextParamsAndExtendsion(AntResult antResult,User user) ;
	
	/**
	 * @描述 检测用户是否异地登录<br>
	 * @param user
	 * @return
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-7-14
	 */
	public AntResult checkUserLoginState(AntResult antResult,User user);
}

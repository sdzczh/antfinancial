package com.ant.app.service.inte;

import com.alibaba.fastjson.JSONObject;

/**
 * @描述 APP用户钱包转账接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
public interface AppUserTransferService {
	
	/**
	 * @描述 J、D钱包转Z钱包<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public String updatePackage(JSONObject json);
	
	/**
	 * @描述 J、D、Z钱包金币流水记录<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-19
	 */
	public String getPackageRecord(JSONObject json);

	/**
	 * @描述 J钱包静态收益<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public String getStaticProfit(JSONObject json);
	
	/**
	 * @描述 D钱包推广收益<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public String getPromotionAward(JSONObject json);
	
	/**
	 * @描述 Z钱包奖励金币<br>
	 * @param json JSON对象参数
	 * @return JSON格式返回值
	 * @author 任晴
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public String getGoldAward(JSONObject json);
}

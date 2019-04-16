package com.ant.service.inte;

import java.util.List;

/**
 * 短信接口
 * @描述 <br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-26
 */
public interface SMSCommonService {
	
	/**
	 * @描述 给用记发送短信通知<br>
	 * @param userPhones 用户手机号码集合
	 * @return 状态
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-26
	 */
	public boolean sendNotice(List<String> userPhones);
}

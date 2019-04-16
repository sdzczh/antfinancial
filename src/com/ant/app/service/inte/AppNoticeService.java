package com.ant.app.service.inte;

/**
 * @描述 系统公告接口<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
public interface AppNoticeService {
	
	/**
	 * @描述 获取最新公告<br>
	 * @return JSON格式返回值
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public String getNewNotice();
}

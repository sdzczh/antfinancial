package com.ant.service.inte;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ant.pojo.Notice;

/**
 * @描述 公告管理接口<br>
 * @author lina
 * @日期 2017-6-19
 */
public interface NoticeService {
	/**
	 * @描述 公告列表查询<br>
	 * @param map 参数对象
	 * @param request 请求对象
	 * @param response 返回对象
	 * @return 返回值
	 * @author 李娜
	 * @版本 v1.0.0
	 * @日期 2017-6-14
	 */
	public Map<String,Object> noticeQuery(Map<String,Object> map,int queryRole,Integer page,Integer rows);
	
	/**
	 * @描述 添加公告<br>
	 * @param map 参数对象
	 * @param request 请求对象
	 * @param response 返回对象
	 * @return 返回值
	 * @author 李娜
	 * @版本 v1.0.0
	 * @日期 2017-6-14
	 */
	public Map<String,Object> addNotice(Notice notice,HttpServletRequest request);
	
	/**
	 * 删除公告 
	 * @param noticeId
	 * @return 删除结果
	 */
	public Map<String,Object> noticeDelete(Integer noticeId);
	
	/**
	 * 根据ID查找一条记录
	 * @param NoticeId
	 * @return Notice
	 */
	public Notice queryOne(Integer noticeId);
	
	/**
	 * 修改公告
	 * @param notice
	 * @return 修改结果 
	 */
	public Map<String,Object> noticeModify(Notice notice);
}

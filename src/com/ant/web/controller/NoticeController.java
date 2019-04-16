package com.ant.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.base.BaseController;
import com.ant.glob.GolbParams;
import com.ant.pojo.Notice;
import com.ant.pojo.User;
import com.ant.service.inte.NoticeService;
import com.ant.util.WebUtils;

/**
 * @ 公告管理
 * @author lina
 * @since 2017-6-19
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController{

	@Autowired
	NoticeService service;
	
	/**
	 * @描述 公告查询<br>
	 * @param map 参数对象
	 * @return 地址
	 * @author 李娜
	 * @版本 v1.0.0
	 * @日期 2017-6-14
	 */
	@RequestMapping("notice.action")
	public String notice(Map<String,Object> map,String title,String sendRole){
		User loginUser = (User) WebUtils.getSession().getAttribute(GolbParams.Manager);
		map.put("title",title );
		map.put("sendRole",sendRole );
		if(loginUser!=null){			
			int queryRole = loginUser.getUserRole()==0?0:2;
			service.noticeQuery(map,queryRole,page,rows);
		}
		
		return "notice/noticeQuery";
	}
	  
	/**
	 * @描述 跳转到公告添加页面<br>
	 * @param map 参数对象
	 * @return 地址
	 * @author 李娜
	 * @版本 v1.0.0
	 * @日期 2017-6-14
	 */
	@RequestMapping("toNoticeAdd.action")
	public String toNoticeAdd( ){
		return "notice/noticeAdd";
	}
	
	/**
	 * @描述 添加公告<br>
	 * @param map 参数对象
	 * @return 是否成功标志
	 * @author 李娜
	 * @版本 v1.0.0
	 * @日期 2017-6-14
	 */
	@ResponseBody
	@RequestMapping(value="noticeAdd.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public Map<String,Object> noticeAdd(Notice notice){
		Map<String,Object> map = service.addNotice(notice, request);
		return map;
	}
	
	
	/**
	 * @描述 删除公告<br>
	 * @param Integer 公告ID
	 * @return 是否成功标志
	 * @author 李娜
	 * @版本 v1.0.0
	 * @日期 2017-6-14
	 */
	@ResponseBody
	@RequestMapping(value="noticeDelete.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public Map<String,Object> noticeDelete(Integer noticeId){
		Notice notice = service.queryOne(noticeId);
		notice.setState(1);
		Map<String,Object> map = service.noticeModify(notice);
		return map;
	}
	/**
	 * 跳转到修改公告
	 * @param noticeId
	 * @return
	 */
	@RequestMapping("toNoticeModify.action")
	public String toNoticeModify(Map<String,Object> map,Integer noticeId  ,String currentUrl){
		map.put("currentUrl", currentUrl);
		if(noticeId!=null){
			map.put("notice", service.queryOne(noticeId));
		}
		return "notice/noticeModify";
	}
	
	/**
	 * 修改公告
	 * @param notice
	 * @return 修改结果
	 */
	@ResponseBody
	@RequestMapping("noticeModify.action")
	public Map<String,Object> noticeModify(Notice notice){
		Map<String,Object> map = service.noticeModify(notice);
		return map;
	}
	
}

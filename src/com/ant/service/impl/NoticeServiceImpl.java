package com.ant.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.glob.GolbParams;
import com.ant.pojo.Notice;
import com.ant.pojo.User;
import com.ant.service.inte.NoticeService;
import com.ant.util.DateUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;

/**
 * @ 公告管理接口实现
 * @author lina
 * @since 2017-6-19
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
	@Autowired
    private BaseDaoI dao;

	@Override
	public Map<String, Object> noticeQuery(Map<String, Object> map,int queryRole,Integer page,Integer rows) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer noticeQuerySql = new StringBuffer();
		noticeQuerySql.append("select ");
		noticeQuerySql.append("n.*,u.userName as createName ");
		noticeQuerySql.append("from t_notice n ");
		noticeQuerySql.append("left join t_user u on n.createId = u.id ");
		noticeQuerySql.append("where ");
		noticeQuerySql.append("n.state=0 ");
		if(!StrUtils.isBlank((String)map.get("title"))){
			noticeQuerySql.append("and title like :title ");
			params.put("title", "%"+map.get("title")+"%");
		}
		if(!StrUtils.isBlank((String)map.get("sendRole"))){
			noticeQuerySql.append("and sendRole = :sendRole ");
			params.put("sendRole", map.get("sendRole"));
		}
		List<Map<String,Object>> noticeList = (List<Map<String,Object>>) dao.findBySql( noticeQuerySql.toString(), params, page, rows);
		
		String countSql = "SELECT COUNT(id) FROM ( "+noticeQuerySql.toString()+" )z";
		BigInteger count = dao.countBySql(countSql, params);
		
		map.put("count", count);
		map.put("noticeList", noticeList);
		map.put("page", page);
		map.put("rows", rows);
		return map;
	}

	@Override
	public Map<String, Object> addNotice(Notice notice,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			User loginUser = (User) WebUtils.getSession().getAttribute(GolbParams.Manager);
			notice.setCreateId(loginUser.getId());
			notice.setCreateDate(DateUtils.getCurrentTimeStr());
			notice.setState(0);
			if(dao.save(notice)!=null){
				map.put("success", true);
			}else{
				map.put("success", false);
			}
		} catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		return map;
	}

	@Override
	public Map<String, Object> noticeDelete(Integer noticeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(dao.del(noticeId, "t_notice")==1){
				map.put("success", true);
			}else{
				map.put("success", false);
			}
		} catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		return map;
		
	}

	@Override
	public Notice queryOne(Integer NoticeId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", NoticeId);
		StringBuffer noticeQuerySql = new StringBuffer();
		noticeQuerySql.append("select");
		noticeQuerySql.append("*");
		noticeQuerySql.append("from t_notice ");
		noticeQuerySql.append("where ");
		noticeQuerySql.append("id= :id ");
		
		List<Notice> list = dao.findBySql(Notice.class,noticeQuerySql.toString(), map);
		if(list==null || list.isEmpty()){			
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public Map<String,Object> noticeModify(Notice notice) {
		Map<String,Object> map = new HashMap<String, Object>();
		try{		
			if(notice==null){
				map.put("success", false);
			}else{				
				dao.update(notice);
				map.put("success", true);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		return map;
	}
	

}

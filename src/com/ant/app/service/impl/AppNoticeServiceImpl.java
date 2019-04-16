package com.ant.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppNoticeService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.User;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;

/**
 * @描述 系统公告接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@Transactional
@Service
public class AppNoticeServiceImpl implements AppNoticeService{
	
	@Autowired
	private BaseDaoI dao;
	@Autowired
	private CommonService common;
	
	public String getNewNotice() {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			String userId = WebUtils.getRequest().getHeader("userId");
			
			if(StrUtils.checkNull(userId)){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(userId);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			StringBuffer getNewNoticeSql = new StringBuffer();
			getNewNoticeSql.append("SELECT ");
			getNewNoticeSql.append("tn.id,tn.title,tn.content,tn.createDate,tn.createId,tn.state,tn.sendRole ");
			getNewNoticeSql.append("FROM ");
			getNewNoticeSql.append("t_notice tn ");
			getNewNoticeSql.append("WHERE ");
			getNewNoticeSql.append("tn.sendRole=:sendRole ");
			getNewNoticeSql.append("AND state=:state ");
			getNewNoticeSql.append("ORDER BY tn.id DESC ");
			getNewNoticeSql.append("LIMIT 0,1 ");
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("sendRole", 0);
			params.put("state", 0);
			List<?> notices = dao.findBySql(getNewNoticeSql.toString(),params);
			
			JSONArray jsonObject = (JSONArray) JSON.toJSON(notices);
			antResult.setData(jsonObject);

			Account account = common.getAccountByUserId(userId);
			antResult = common.getContextParamsAndPackage(antResult,account,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

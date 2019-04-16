package com.ant.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.glob.GolbParams;
import com.ant.pojo.LoginHistory;
import com.ant.pojo.User;
import com.ant.service.inte.SysInfoService;
import com.ant.util.IPUtils;
import com.ant.util.WebUtils;
/**
 * @描述 系统信息接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-8
 */
@Service
@Transactional
public class SysInfoServiceImpl implements SysInfoService{
	
    @Autowired
    private BaseDaoI dao;

	public Map<String, Object> info(Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) {
		
		User loginUser = (User) WebUtils.getSession().getAttribute(GolbParams.Manager);
		map.put("userInfo",dao.getById(User.class, loginUser.getId()));

		StringBuffer frozenCountSql = new StringBuffer();
		frozenCountSql.append("SELECT ");
		frozenCountSql.append("COUNT(1)offCount ");
		frozenCountSql.append("FROM t_user u  LEFT JOIN t_account a ON a.`userId` = u.`id`  ");
		frozenCountSql.append("WHERE ");
		frozenCountSql.append("a.state = :state ");
		frozenCountSql.append("AND u.userRole = :userRole ");
		frozenCountSql.append("AND u.isDel = :isDel ");
		Map<String,Object> frozenCountSqlParams = new HashMap<String, Object>();
		frozenCountSqlParams.put("state", 2);
		frozenCountSqlParams.put("userRole", 0);
		frozenCountSqlParams.put("isDel", 0);
		BigInteger frozenCount = dao.countBySql(frozenCountSql.toString(), frozenCountSqlParams);
		map.put("frozenCount", frozenCount);
		
		Map<String,Object> deleteCountSqlParams = new HashMap<String, Object>();
		deleteCountSqlParams.put("state", 0);
		deleteCountSqlParams.put("userRole", 0);
		deleteCountSqlParams.put("isDel", 0);
		BigInteger deleteCount = dao.countBySql(frozenCountSql.toString(), deleteCountSqlParams);
		map.put("deleteCount", deleteCount);
		
		StringBuffer lastLoginInfoSql = new StringBuffer();
		lastLoginInfoSql.append("SELECT ");
		lastLoginInfoSql.append("tlh.* ");
		lastLoginInfoSql.append("FROM t_login_history tlh ");
		lastLoginInfoSql.append("WHERE ");
		lastLoginInfoSql.append("tlh.userId = :userId ");
		lastLoginInfoSql.append("AND tlh.userRole = :userRole ");
		lastLoginInfoSql.append("ORDER BY id DESC ");
		Map<String,Object> lastLoginInfoSqlParams = new HashMap<String, Object>();
		lastLoginInfoSqlParams.put("userId", loginUser.getId());
		lastLoginInfoSqlParams.put("userRole", loginUser.getUserRole());
		List<LoginHistory> histories = dao.findBySql(LoginHistory.class, lastLoginInfoSql.toString(), lastLoginInfoSqlParams, 0, 2);
		if(histories.size()>0&&histories.size()<2){
			LoginHistory history = histories.get(0);
			map.put("loginDate",history.getLoginDate());
			map.put("loginIp",history.getLoginIp());
		}
		if(histories.size()==2){
			LoginHistory history = histories.get(1);
			map.put("loginDate",history.getLoginDate());
			map.put("loginIp",history.getLoginIp());
		}
		if(histories.size()==0){
			map.put("loginDate", "无");
			map.put("loginIp", "无");
		}
		
		String agent = request.getHeader("User-Agent"); 
		String serviceIp = IPUtils.getIpAddr(request);
		String serverName = request.getServerName();
		String serverPort = String.valueOf(request.getServerPort());
		map.put("agent", agent);
		map.put("serviceIp", serviceIp);
		map.put("serverName", serverName);
		map.put("serverPort", serverPort);
		return map;
	}

}

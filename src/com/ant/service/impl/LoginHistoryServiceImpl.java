package com.ant.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.service.inte.LoginHistoryService;
import com.ant.util.StrUtils;

/**
 * @描述 登录记录接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-13
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class LoginHistoryServiceImpl implements LoginHistoryService{

	@Autowired
	BaseDaoI dao;

	public Map<String, Object> getLoginHistoryById(Map<String,Object>map,String id,Integer page,Integer rows) {
		map.put("state", true);
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("th.*,user.userAccount as userAccount,user.userName as userName ");
			sql.append("FROM ");
			sql.append("t_login_history th ");
			sql.append("left join t_user user on th.userId = user.id ");
			sql.append("WHERE ");
			sql.append("1=1 ");
			if(!StrUtils.isBlank((String)map.get("loginAccount"))){
				sql.append("and user.userAccount like :userAccount ");
				params.put("userAccount", "%"+map.get("loginAccount")+"%");
			}
			if(!StrUtils.isBlank((String)map.get("createTimeStart"))){
				sql.append("and left(th.loginDate,10) >= :createTimeStart ");
				params.put("createTimeStart", map.get("createTimeStart"));
			}
			if(!StrUtils.isBlank((String)map.get("createTimeEnd"))){
				sql.append("and left(th.loginDate,10) <= :createTimeEnd ");
				params.put("createTimeEnd", map.get("createTimeEnd"));
			}
			sql.append("ORDER BY id DESC");
			List<Map<String, Object>> histories = (List<Map<String, Object>>)dao.findBySql( sql.toString(), params, page, rows);
			String countSql = "SELECT COUNT(id) FROM( "+sql.toString()+" )z";
			BigInteger count = dao.countBySql(countSql, params);

			map.put("count", count);
			map.put("data", histories);
			map.put("page", page);
			map.put("rows", rows);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", false);
			throw new RuntimeException(e);
		}
		return map;
	}
}

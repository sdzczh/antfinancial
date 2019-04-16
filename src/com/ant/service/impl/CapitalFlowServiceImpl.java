package com.ant.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.service.inte.CapitalFlowService;
import com.ant.util.StrUtils;
/**
 * 交易流水管理
 * @author lina
 * 2017-07-03
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class CapitalFlowServiceImpl implements CapitalFlowService{
	@Autowired
    private BaseDaoI dao;

	@Override
	public Map<String, Object> capitalFlowQuery(Map<String, Object> map,
			Integer page, Integer rows) {
		StringBuffer flowsQuerySql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		flowsQuerySql.append("select ");
		flowsQuerySql.append("flows.*,user.userAccount,user.userName ");
		flowsQuerySql.append("from t_capital_flow flows ");
		flowsQuerySql.append("left join t_user user on flows.userId = user.id ");
		flowsQuerySql.append("where 1=1 ");
		if(!StrUtils.isBlank((String)map.get("userAccount"))){
			flowsQuerySql.append("and user.userAccount like :userAccount ");
			params.put("userAccount", "%"+map.get("userAccount")+"%");
		}
		if(!StrUtils.isBlank((String)map.get("packageType"))){
			flowsQuerySql.append("and flows.packageType = :packageType ");
			params.put("packageType", map.get("packageType"));
		}
		if(!StrUtils.isBlank((String)map.get("type"))){
			flowsQuerySql.append("and flows.type = :type ");
			params.put("type", map.get("type"));
		}
		if(!StrUtils.isBlank((String)map.get("createTimeStart"))){
			flowsQuerySql.append("and left(flows.createDateTime,10) >= :createTimeStart ");
			params.put("createTimeStart", map.get("createTimeStart"));
		}
		if(!StrUtils.isBlank((String)map.get("createTimeEnd"))){
			flowsQuerySql.append("and left(flows.createDateTime,10) <= :createTimeEnd ");
			params.put("createTimeEnd", map.get("createTimeEnd"));
		}
		flowsQuerySql.append("order by id desc ");
		List<Map<String,Object>> flowsList = (List<Map<String,Object>>) dao.findBySql( flowsQuerySql.toString(),params, page, rows);
		
		String countSql = "SELECT COUNT(id) FROM ( "+flowsQuerySql.toString()+" )z";
		BigInteger count = dao.countBySql(countSql,params);
		
		map.put("count", count);
		map.put("flowList", flowsList);
		map.put("page", page);
		map.put("rows", rows);
		return map;
	}
}

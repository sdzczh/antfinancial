package com.ant.app.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppExtensionService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.User;

/**
 * @描述 推广接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@Transactional
@SuppressWarnings("unchecked")
@Service
public class AppExtensionServiceImpl implements AppExtensionService{

	@Autowired
	private BaseDaoI dao;
	
	@Autowired
	private CommonService common;
	
	public String getUserOfFirstLevel(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			
			String id = json.getString("id");
			if(id==null || "".equals(id.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(id);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			StringBuffer getUserOfFirstLevelSql = new StringBuffer();
			getUserOfFirstLevelSql.append("SELECT ");
			getUserOfFirstLevelSql.append("tu.id loginUserId,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
			getUserOfFirstLevelSql.append("FROM t_user tu ");
			getUserOfFirstLevelSql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			getUserOfFirstLevelSql.append("WHERE ");
			getUserOfFirstLevelSql.append("tu.referenceId= :referenceId ");
			getUserOfFirstLevelSql.append("AND tu.isDel = :isDel ");
			getUserOfFirstLevelSql.append("AND ta.state != :state ");
			getUserOfFirstLevelSql.append("AND (tu.userRole = :userRole OR tu.userRole = :managerRole )");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("referenceId", id);
			params.put("isDel", 0);
			params.put("state", 2);
			params.put("userRole", 0);
			params.put("managerRole", 3);
			List<?> users = dao.findBySql(getUserOfFirstLevelSql.toString(), params, page, rows);
			
			if(users.size()>0){
				for(int i=0;i<users.size();i++){
					Map<String,Object> map = (Map<String, Object>) users.get(i);
					map.put("orderNum", page*rows+i+1);
					map.put("packageJ", (new BigDecimal(map.get("packageJ").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					map.put("packageD", (new BigDecimal(map.get("packageD").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					map.put("packageZ", (new BigDecimal(map.get("packageZ").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					map.put("profit", (new BigDecimal(map.get("profit").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
				}
			}
			
			String countSql = "SELECT COUNT(loginUserId) FROM ("+getUserOfFirstLevelSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);
			JSONArray arrayJson = (JSONArray) JSON.toJSON(users);

			antResult.setCount(count.intValue());
			antResult.setData(arrayJson);
			antResult = common.getContextParamsAndExtendsion(antResult,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getUserOfSecondLevel(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			
			String id = json.getString("id");
			
			if(id==null || "".equals(id.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(id);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			StringBuffer getUserOfSecondLevelSql = new StringBuffer();
			getUserOfSecondLevelSql.append("SELECT ");
			getUserOfSecondLevelSql.append("tu.id loginUserId,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
			getUserOfSecondLevelSql.append("FROM t_user tu ");
			getUserOfSecondLevelSql.append("INNER JOIN ");
			getUserOfSecondLevelSql.append("(SELECT * FROM t_user  WHERE referenceId = :referenceId ) u  ON tu.referenceId = u.id ");
			getUserOfSecondLevelSql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			getUserOfSecondLevelSql.append("WHERE tu.isDel = :isDel ");
			getUserOfSecondLevelSql.append("AND ta.state != :state ");
			getUserOfSecondLevelSql.append("AND (tu.userRole = :userRole OR tu.userRole = :managerRole )");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("referenceId", id);
			params.put("isDel", 0);
			params.put("state", 2);
			params.put("userRole", 0);
			params.put("managerRole", 3);
			List<?> users = dao.findBySql(getUserOfSecondLevelSql.toString(), params, page, rows);
			
			if(users.size()>0){
				for(int i=0;i<users.size();i++){
					Map<String,Object> map = (Map<String, Object>) users.get(i);
					map.put("orderNum", page*rows+i+1);
					map.put("packageJ", (new BigDecimal(map.get("packageJ").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					map.put("packageD", (new BigDecimal(map.get("packageD").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					map.put("packageZ", (new BigDecimal(map.get("packageZ").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());
					map.put("profit", (new BigDecimal(map.get("profit").toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN)).doubleValue());					
				}
			}
			
			String countSql = "SELECT COUNT(loginUserId) FROM ("+getUserOfSecondLevelSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);
			
			JSONArray arrayJson = (JSONArray) JSON.toJSON(users);
			
			antResult.setCount(count.intValue());
			antResult.setData(arrayJson);
			antResult = common.getContextParamsAndExtendsion(antResult,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getUserOfThirdLevel(JSONObject json) {
		AntResult antResult = new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			
			String id = json.getString("id");
			
			if(id==null || "".equals(id.trim())){
				antResult.setType(AntType.ANT_204);
				return AntResponse.response(antResult);
			}
			
			User user = common.getUserByUserId(id);
			antResult = common.checkUserLoginState(antResult, user);
			if(!antResult.getType().equals(AntType.ANT_100)){
				return AntResponse.response(antResult);
			}
			
			Integer page = json.getInteger("page");
			page=page==null?0:page;
			Integer rows = json.getInteger("rows");
			rows=rows==null?10:rows;
			
			StringBuffer getUserOfThirdLevelSql = new StringBuffer();
			getUserOfThirdLevelSql.append("SELECT ");
			getUserOfThirdLevelSql.append("tu.id loginUserId,tu.userName,tu.userRole,tu.userAccount,tu.createTime loginUserCreateTime,ta.* ");
			getUserOfThirdLevelSql.append("FROM t_user tu ");
			getUserOfThirdLevelSql.append("INNER JOIN ( ");
			getUserOfThirdLevelSql.append("SELECT tuser.* FROM t_user tuser ");
			getUserOfThirdLevelSql.append("INNER JOIN ( ");
			getUserOfThirdLevelSql.append("SELECT * FROM t_user WHERE referenceId = :referenceId ");
			getUserOfThirdLevelSql.append(")uu ON tuser.referenceId = uu.id ");
			getUserOfThirdLevelSql.append(")u ON tu.referenceId = u.id ");
			getUserOfThirdLevelSql.append("LEFT JOIN t_account ta ON tu.id = ta.userId ");
			getUserOfThirdLevelSql.append("WHERE tu.isDel = :isDel ");
			getUserOfThirdLevelSql.append("AND ta.state != :state ");
			getUserOfThirdLevelSql.append("AND (tu.userRole = :userRole OR tu.userRole = :managerRole )");			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("referenceId", id);
			params.put("isDel", 0);
			params.put("state", 2);
			params.put("userRole", 0);
			params.put("managerRole",3);
			
			List<?> users = dao.findBySql(getUserOfThirdLevelSql.toString(), params, page, rows);
			
			if(users.size()>0){
				for(int i=0;i<users.size();i++){
					Map<String,Object> map = (Map<String, Object>) users.get(i);
					map.put("orderNum", page*rows+i+1);
				}
			}
			
			String countSql = "SELECT COUNT(loginUserId) FROM ("+getUserOfThirdLevelSql.toString()+")z";
			BigInteger count = dao.countBySql(countSql, params);
			
			JSONArray arrayJson = (JSONArray) JSON.toJSON(users);
			
			antResult.setCount(count.intValue());
			antResult.setData(arrayJson);
			antResult = common.getContextParamsAndExtendsion(antResult,user);
			return AntResponse.response(antResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

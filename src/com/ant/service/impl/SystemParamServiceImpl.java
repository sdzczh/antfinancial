package com.ant.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.SystemParam;
import com.ant.service.inte.SystemParamService;
import com.ant.util.StrUtils;

/**
 * 参数设置接口实现
 * @author Administrator
 *
 */
@Service
@Transactional
public class SystemParamServiceImpl implements SystemParamService {

	@Autowired
    private BaseDaoI dao;

	@Override
	public Map<String, Object> queryParamList(Map<String, Object> map,Integer page,Integer rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer systemParamQuerySql = new StringBuffer();
		systemParamQuerySql.append("select ");
		systemParamQuerySql.append("* ");
		systemParamQuerySql.append("from t_sysparam ");
		systemParamQuerySql.append("where 1=1 ");
		if(!StrUtils.isBlank((String)map.get("keyName"))){
			systemParamQuerySql.append("and keyName like :keyName ");
			params.put("keyName", "%"+map.get("keyName")+"%");
		}
		if(!StrUtils.isBlank((String)map.get("remark"))){
			systemParamQuerySql.append("and remark like :remark ");
			params.put("remark", "%"+map.get("remark")+"%");
		}
		List<SystemParam> systemParamList = dao.findBySql(SystemParam.class,systemParamQuerySql.toString(),params, page, rows);
		
		String countSql = "SELECT COUNT(id) FROM ( "+systemParamQuerySql.toString()+" )z";
		BigInteger count = dao.countBySql(countSql, params);
		
		map.put("count", count);
		map.put("paramList", systemParamList);
		map.put("page", page);
		map.put("rows", rows);
		return map;
	}

	@Override
	public Map<String, Object> addParam(SystemParam param) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(dao.save(param)!=null){
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
	public SystemParam queryOne(Integer paramId) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", paramId);
		StringBuffer systemParamQuerySql = new StringBuffer();
		systemParamQuerySql.append("select");
		systemParamQuerySql.append("*");
		systemParamQuerySql.append("from t_sysparam ");
		systemParamQuerySql.append("where ");
		systemParamQuerySql.append("id= :id ");
		
		List<SystemParam> list = dao.findBySql(SystemParam.class,systemParamQuerySql.toString(), map);
		if(list==null || list.isEmpty()){			
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public Map<String, Object> paramModify(SystemParam param) {
		Map<String,Object> map = new HashMap<String, Object>();
		try{		
			if(param==null){
				map.put("success", false);
			}else{				
				dao.update(param);
				map.put("success", true);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		return map;
	}

	@Override
	public Map<String, Object> paramDelete(SystemParam param) {
		Map<String,Object> map = new HashMap<String, Object>();
		try{		
			if(param==null){
				map.put("success", false);
			}else{				
				dao.delete(param);
				map.put("success", true);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			throw new RuntimeException(e);
		}
		return map;
	}

    @Override
    public SystemParam queryByKeyName(String keyName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyName", keyName);
        StringBuffer systemParamQuerySql = new StringBuffer();
        systemParamQuerySql.append("select");
        systemParamQuerySql.append("*");
        systemParamQuerySql.append("from t_sysparam ");
        systemParamQuerySql.append("where ");
        systemParamQuerySql.append("keyName= :keyName ");

        List<SystemParam> list = dao.findBySql(SystemParam.class,systemParamQuerySql.toString(), map);
        if(list==null || list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }
}

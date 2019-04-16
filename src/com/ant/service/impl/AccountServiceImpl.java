package com.ant.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.service.inte.AccountService;
import com.ant.util.DateUtils;

/**
 * 账户管理接口实现
 * @author lina
 *
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService{
	@Autowired
    private BaseDaoI dao;

	@Override
	public Account queryByUserId(Integer userId,Integer state) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		StringBuffer accountQuerySql = new StringBuffer();
		accountQuerySql.append("select");
		accountQuerySql.append("*");
		accountQuerySql.append("from t_account ");
		accountQuerySql.append("where ");
		accountQuerySql.append("userId= :id ");
		if(state!=null){
			accountQuerySql.append("and state= :state ");
			map.put("state", state);
		}
		
		List<Account> list = dao.findBySql(Account.class,accountQuerySql.toString(), map);
		if(list==null || list.isEmpty()){			
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public Map<String, Object> updateAccount(Account account) {
		Map<String,Object> map = new HashMap<String, Object>();
		try{		
			if(account==null){
				map.put("success", false);
			}else{				
				account.setUpdateTime(DateUtils.getCurrentTimeStr());
				dao.update(account);
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

package com.ant.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppRechangeService;
import com.ant.app.service.inte.AppWithdrawService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.User;
import com.ant.pojo.Withdraw;
import com.ant.util.ResultUtils;
import com.ant.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @描述 系统公告接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@Transactional
@Service
public class AppWithdrawServiceImpl implements AppWithdrawService {
	
	@Autowired
	private BaseDaoI dao;
	@Autowired
	private CommonService common;


    @Override
    public String getRechangeInfo(JSONObject json) {
        AntResult result = new AntResult();
        result.setType(AntType.ANT_100);
        try {
            Integer type = json.getInteger("type");
            String address = json.getString("address");
            String remark = json.getString("remark");
            BigDecimal amount = json.getBigDecimal("amount");
            String userIdStr = json.getString("userId");
            if(StrUtils.isBlank(address) || StrUtils.isBlank(remark) || StrUtils.isBlank(userIdStr) || type == null || amount == null){
                result.setType(AntType.ANT_204);
                return AntResponse.response(result);
            }
            User user = common.getUserByUserId(userIdStr);
            String getCountHql = "from Account where userId=:userId";
            Map<String,Object> getCountHqlParams = new HashMap<>();
            Integer userId = Integer.parseInt(userIdStr);
            getCountHqlParams.put("userId", userId);
            Account account = dao.findUnique(getCountHql,getCountHqlParams);
            BigDecimal balance;
            if(type == 0){
                balance = new BigDecimal(account.getPackageJ());
            }else if(type == 1){
                balance = new BigDecimal(account.getPackageD());
            }else{
                balance = new BigDecimal(account.getPackageZ());
            }
            if(balance.compareTo(amount) < 0){
                result.setType(AntType.ANT_312);
                return AntResponse.response(result);
            }
            if(type == 0){
                balance = balance.subtract(amount);
                account.setPackageJ(balance.doubleValue());
            }else if(type == 1){
                balance = balance.subtract(amount);
                account.setPackageD(balance.doubleValue());
            }else{
                balance = balance.subtract(amount);
                account.setPackageZ(balance.doubleValue());
            }
            dao.saveOrUpdate(account);

            if(account==null){
                result.setType(AntType.ANT_208);
                return AntResponse.response(result);
            }
            Withdraw withdraw = new Withdraw();
            withdraw.setAccount_type(type);
            withdraw.setAddress(address);
            withdraw.setAmount(amount);
            withdraw.setRemark(remark);
            withdraw.setUser_id(userId);
            withdraw.setState(0);
            dao.save(withdraw);
            return AntResponse.response(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

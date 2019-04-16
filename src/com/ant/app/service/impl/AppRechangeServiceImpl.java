package com.ant.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppNoticeService;
import com.ant.app.service.inte.AppRechangeService;
import com.ant.app.service.inte.CommonService;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.User;
import com.ant.util.ResultUtils;
import com.ant.util.StrUtils;
import com.ant.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述 系统公告接口实现<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@Transactional
@Service
public class AppRechangeServiceImpl implements AppRechangeService{
	
	@Autowired
	private BaseDaoI dao;
	@Autowired
	private CommonService common;

    @Override
    public String getRechangeInfo() {
        String imgUrl = common.getValStrByKey("RECHANGE_IMG_URL");
        String rechange_account = common.getValStrByKey("RECHANGE_ACCOUNT");
        Map<String, Object> map = new HashMap<>();
        map.put("img_url", imgUrl);
        map.put("rechange_account", rechange_account);
        return ResultUtils.jsonSuccess(JSONObject.toJSONString(map));
    }
}

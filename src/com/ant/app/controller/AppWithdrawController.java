package com.ant.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.app.service.inte.AppRechangeService;
import com.ant.app.service.inte.AppWithdrawService;
import com.ant.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @描述 提现
 * @author 赵赫
 * @版本 v1.0.0
 * @日期 2019.4.16
 */
@RequestMapping("/app/withdraw")
@Controller
public class AppWithdrawController extends BaseController{

	@Autowired
	private AppWithdrawService appWithdrawService;
	
	/**
	 * @描述 获取充值信息<br>
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="commit.action",method=RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String getRechangeInfo(@RequestBody String param){
        AntResult antResult = new AntResult();
        if(param!=null && !"".equals(param.trim())){
            try {
                JSONObject json = JSONObject.parseObject(param);
                return appWithdrawService.getRechangeInfo(json);
            } catch (Exception e) {
                antResult.setType(AntType.ANT_206);
                return AntResponse.response(antResult);
            }
        }
        antResult.setType(AntType.ANT_204);
        return AntResponse.response(antResult);
	}
}

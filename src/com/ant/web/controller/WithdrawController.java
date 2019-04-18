package com.ant.web.controller;

import com.ant.base.BaseController;
import com.ant.pojo.Notice;
import com.ant.service.inte.TransactionService;
import com.ant.service.inte.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 交易记录管理
 * @author lina
 * 2017-6-17
 */
@Controller
@RequestMapping("withdraw")
public class WithdrawController extends BaseController{
	@Autowired
	private WithdrawService withdrawService;
	
	
	/**
	 * 查询订单记录
	 * @param map
	 * @return
	 */
	@RequestMapping("getWithdrawList.action")
	public String getWithdrawList(Map<String, Object> map, String userAccount, String state, HttpSession session){
		map.put("userAccount", userAccount);
		map.put("state", state);
        session.setAttribute("data", withdrawService.getWithdrawList(map, page, rows));
		return "withdraw/withdrawQuery";
	}

    @ResponseBody
    @RequestMapping(value="withdrawDelete.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String noticeDelete(Integer id){
        withdrawService.delete(id);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value="update.action",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String update(Integer id, Integer state){
        withdrawService.update(id, state);
        return "success";
    }
}

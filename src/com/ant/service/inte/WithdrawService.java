package com.ant.service.inte;

import com.ant.pojo.Notice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface WithdrawService {

    Map<String,Object> getWithdrawList(Map<String, Object> map, Integer page, Integer rows);

    void delete(Integer id);

    void update(Integer id, Integer state);
}

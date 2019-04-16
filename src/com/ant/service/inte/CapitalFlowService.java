package com.ant.service.inte;

import java.util.Map;

/**
 * 交易流水管理
 * @author lina
 * 2017-07-03
 */
public interface CapitalFlowService {

	Map<String, Object> capitalFlowQuery(Map<String, Object> map,Integer page,Integer rows);
}

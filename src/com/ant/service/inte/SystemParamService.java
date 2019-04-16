package com.ant.service.inte;

import java.util.Map;

import com.ant.pojo.SystemParam;

/**
 * 参数设置接口
 * @author lina
 *
 */
public interface SystemParamService {
	
	/**
	 * 查询参数列表
	 * @param map
	 * @return 参数列表
	 */
	public Map<String, Object> queryParamList(Map<String, Object> map,Integer page,Integer rows);
	
	/**
	 * 添加参数
	 * @param param
	 * @return 添加结果
	 */
	public Map<String, Object> addParam(SystemParam param);
	
	/**
	 * 根据ID查询参数
	 * @param paramId
	 * @return 参数
	 */
	public SystemParam queryOne(Integer paramId);
	
	/**
	 * 修改参数
	 * @param param
	 * @return
	 */
	public Map<String, Object> paramModify(SystemParam param);
	
	/**
	 * 删除参数
	 * @param param
	 * @return
	 */
	public Map<String,Object> paramDelete(SystemParam param);

    SystemParam queryByKeyName(String keyName);
}

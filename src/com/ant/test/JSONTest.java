package com.ant.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ant.glob.ResponseParam;
import com.ant.pojo.User;
import com.ant.util.ResultUtils;
@SuppressWarnings("unused")
public class JSONTest {

	public static void main(String[] args) {
		
		test12();
	}

	private static void test12() {
		Map<String,Object> contextParams = new HashMap<String, Object>();
		contextParams.put("userUpdateState", 1);
		contextParams.put("currentProfit", 1);
		contextParams.put("afterProfit", 1);
		contextParams.put("profit", 1);
		contextParams.put("days", 1);
		contextParams.put("after", 1);
		contextParams.put("id", 1);
		contextParams.put("pid", 1);
		contextParams.put("userAccount", 1);
		contextParams.put("userName", 1);
		contextParams.put("userCode", 1);
		contextParams.put("loginState",1);
		contextParams.put("packageJ", 1);
		contextParams.put("packageD", 1);
		contextParams.put("packageZ", 1);
		contextParams.put("packageNum", 1);
		contextParams.put("alipayNumber", 1);
		contextParams.put("bankName", 1);
		contextParams.put("bankNumber", 1);
		JSONObject contextParamJSON = (JSONObject) JSON.toJSON(contextParams);
		System.out.println(contextParamJSON);
	}

	private static void test11() {
		List<String> phones = new ArrayList<String>();
		JSONArray json = (JSONArray) JSONObject.toJSON(phones);
		System.out.println(json.toJSONString());
	}

	private static void test10() {
		List<String> phones = new ArrayList<String>();
		for(int i = 0;i<3;i++){
			phones.add("1506371082"+1);
		}
		JSONArray json = (JSONArray) JSONObject.toJSON(phones);
		System.out.println(json.toJSONString());
	}

	private static void test9() {
		User user = new User();
		user.setUserAccount("acc");
		user.setUserName("name");
		
		JSONObject json = (JSONObject) JSONObject.toJSON(user);
		ResponseParam responseParam = new ResponseParam();
		responseParam.setData(json);
		System.out.println(ResultUtils.response(responseParam));
		
	}

	private static void test8() {
		List<User> users = new ArrayList<User>();
		for(int i=0;i<3;i++){
			User user = new User();
			user.setUserAccount("acc"+i);
			user.setUserName("name"+i);
			users.add(user);
		}
		
		JSONArray json = (JSONArray) JSON.toJSON(users);
		ResponseParam param = new ResponseParam();
		param.setData(json);
		System.out.println(ResultUtils.response(param));
		
	}

	private static void test7(){
		ResponseParam response = new ResponseParam();
		
		User user = new User();
		user.setId(1);
		user.setUserAccount("taetdas");
		user.setUserName("tsts");
		
		JSONObject json = (JSONObject) JSONObject.toJSON(user);
		
		response.setData(json);
		response.setMsg("aldfalsdfasdf");
		response.setResult("ok");

		ResponseParam r2 = new ResponseParam();
		r2.setResult("ok");
		r2.setExist(1);
		System.out.println(ResultUtils.response(response));
		
	}

	
	private static void test6(){
		User user = new User();
		user.setId(1);
		user.setUserAccount("taetdas");
		user.setUserName("tsts");
		
		JSONObject json = (JSONObject) JSONObject.toJSON(user);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", json.toString());
		/*
		System.out.println(jsonObject.toString());
		String st = jsonObject.toJSONString();
		st = st.replace("\\", "");
		System.out.println(st);
		*/
		System.out.println(jsonObject.toJSONString().replace("\\", ""));
	}
	
	private static void test5() {
		List<User> users = new ArrayList<User>();
		for(int i=0;i<2;i++){
			User user = new User();
			user.setId(2+i);
			user.setUserName("JACK"+i);
			users.add(user);
		}
		JSONArray json = (JSONArray) JSON.toJSON(users);
		String str = ResultUtils.jsonSuccess(json.toJSONString()+",\"count\":"+2);
		System.out.println(str);
	}


	public static void test4(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", "name");
		map.put("age", 15);
		map.put("sex", "男");
		
		JSONObject json = (JSONObject) JSON.toJSON(map);
		json.put("aa", "safa");
		System.out.println(json);
	}
	
	/**
	 * @描述 获取不存在的Key值<br>
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	public static void test3(){
		User u = new User();
		u.setId(1);
		u.setUserAccount("account");
		u.setUserPassword("pwd");
		JSONObject json = (JSONObject) JSONObject.toJSON(u);
		System.out.println(json.toJSONString());
		String ts = json.getString("testname");
		System.out.println(ts);
	}
	/**
	 * @描述 集合转JSON<br>
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public static void listToJson(){
		List<User> users = new ArrayList<User>();
		for(int i=0;i<5;i++){
			User u = new User();
			u.setId(1+i);
			u.setUserAccount("account"+i);
			u.setUserPassword("pwd"+i);
			users.add(u);
		}
		JSONArray json = (JSONArray) JSON.toJSON(users);
		String resultJson = ResultUtils.jsonSuccess(json.toJSONString());
		/*
		String res = "{\"result\":\"ok\",\"data\":"+jsons.toJSONString()+"}";
		System.out.println(res);
		 */
		
		JSONObject jso = JSONObject.parseObject(resultJson);
		
		User u = new User();
		u.setId(1);
		u.setUserAccount("account");
		u.setUserPassword("pwd");
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(u);
		System.out.println(ResultUtils.jsonSuccess(jsonObject.toJSONString()));
		
	}
}

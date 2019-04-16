package com.ant.test;

import com.alibaba.fastjson.JSONObject;
import com.ant.pojo.Notice;
import com.ant.util.ResultUtils;

public class CountTest {
	public static void main(String[] args) {
		test2();
	}
	
	private static void test2() {
		Notice notice = new Notice();
		notice.setContent("Content");
		notice.setCreateDate("2017-05-06");
		notice.setCreateId(5);
		notice.setSendRole(0);
		notice.setState(0);
		notice.setTitle("title");
		JSONObject json = (JSONObject) JSONObject.toJSON(notice);
		System.out.println(json.toJSONString());
		System.out.println(ResultUtils.jsonSuccess(json.toJSONString()));
	}


}

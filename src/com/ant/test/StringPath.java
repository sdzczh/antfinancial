package com.ant.test;

import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
@SuppressWarnings("unused")
public class StringPath {
	
	public static void main(String[] args) {
		
		test2();
	//	test1();
	}

	private static void test2() {
		AntResult antResult =  new AntResult();
		antResult.setType(AntType.ANT_100);
		try {
			int i = 3/0;
		} catch (Exception e) {
			
		}
		
	}

	private static void test1() {
		String s="{\"id\":21,\"amount\":100.0,\"userId\":2,\"state\":0,\"type\":1,\"userAccount\":\"13000000000\",\"createDate\":\"2017-06-19 18:21:50\"},{\"id\":22,\"amount\":100.0,\"userId\":10,\"state\":0,\"type\":1,\"userAccount\":\"18353710128\",\"createDate\":\"2017-06-19 18:25:18\"}";
		s = s.replace("\"", "");
		System.out.println(s);
		/*
		String path=WebUtils.getRequest().getSession().getServletContext().getRealPath("/");
		System.out.println(path);
	*/	
	}

}

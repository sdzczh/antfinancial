package com.ant.test;

import java.math.BigDecimal;

import com.ant.pojo.User;
import com.ant.util.StrUtils;
@SuppressWarnings("unused")
public class StringTest {

	public static void main(String[] args) {
		test5();
	}

	private static void test5() {
	}

	private static void test4() {
		StringBuffer a = new StringBuffer();
		System.out.println(a.length());
		
	}

	private static void test3() {
		String s =StrUtils.getCode(8);
		System.out.println(s);
	}

	/**
	 * @描述 公式计算<br>
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-16
	 */
	private static void test2() {
		//(1+1+0.1*(n-1))*(n/2)* (500*1%)
		String days = "4";
		BigDecimal bd1 = new BigDecimal("500").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd2 = new BigDecimal("0.001").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd3 = new BigDecimal(days).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd4 = new BigDecimal("2").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd5 = new BigDecimal("0.1").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd6 = new BigDecimal("3").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd7 = new BigDecimal("3").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal bd8 = new BigDecimal("1").setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
		
		BigDecimal resultt =bd4.add(bd2.multiply(bd3.subtract(bd8))).multiply(bd3.divide(bd4)).multiply(bd1.multiply(bd2));
		BigDecimal result = bd5.multiply(bd6).add(bd4).multiply(bd3.divide(bd4)).multiply(bd1.multiply(bd2));
		System.out.println(resultt.doubleValue());
		
	}

	public static void test1(){
		User user = new User();
		user.setUserPassword("admin");
		String a = new String("admin");
		System.out.println(user.getUserPassword().equals(a));
		System.out.println(user.getUserPassword()==a);
	}
}

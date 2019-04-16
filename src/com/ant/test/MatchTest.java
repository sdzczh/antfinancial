package com.ant.test;

import com.ant.util.MatchUtils;


public class MatchTest {
	public static void main(String[] args) {
		
		System.out.println(MatchUtils.doCalculationB(String.valueOf(15),"500", "(1+1+0.1*(n-1))*(n/2)*(y*0.01)"));
		System.out.println(MatchUtils.doCalculationB(String.valueOf(16),"500", "(1+1+0.1*(n-1))*(n/2)*(y*0.01)"));
	}
}

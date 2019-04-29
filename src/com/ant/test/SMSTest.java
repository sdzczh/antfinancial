package com.ant.test;

import java.util.ArrayList;
import java.util.List;

import com.ant.util.DateUtils;
import com.ant.util.SMSCodeUtil;

public class SMSTest {

	public static void main(String[] args) throws Exception {
        SMSCodeUtil smsCodeUtil = new SMSCodeUtil();
        smsCodeUtil.send("13165373280", "SMS_141580455");
	}
}

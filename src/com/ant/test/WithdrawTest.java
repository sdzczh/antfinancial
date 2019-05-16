package com.ant.test;

import com.alibaba.fastjson.JSONObject;
import com.ant.util.Base64Utils;
import com.ant.util.HttpUtils;
import org.junit.Test;

public class WithdrawTest {
    /*public static void main(String args[]){
        String url = "http://localhost:8080/antfinancial_Web_exploded/app/withdraw/getWithdrawInfo.action";
        String userId = "4";
        String param = Base64Utils.encoder(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("param", param);
        System.out.println(HttpUtils.postWithJson(url, jsonObject.toJSONString()));
    }*/

   public static void main(String args[]){
        String url = "http://localhost:8080/antfinancial_Web_exploded/app/withdraw/commit.action";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 100);
        jsonObject.put("userId", 171);
        jsonObject.put("type", 0);
        jsonObject.put("address", "123");
        jsonObject.put("remark", "aaa");
        String param = jsonObject.toJSONString();
        param = Base64Utils.encoder(param);
       jsonObject = new JSONObject();
       jsonObject.put("param", param);
       param = jsonObject.toJSONString();
        System.out.println(HttpUtils.postWithJson(url, param));
    }
}

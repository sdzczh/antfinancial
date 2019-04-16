    package com.ant.web.common;  
      
    import java.io.Serializable;  

import com.alibaba.fastjson.JSONObject;
      
      
    /** 
     * 请求返回结果基类。 
     * 返回结果的公共数据可以放在此类 
     * @author nier_ni 
     * 
     */  
    public class ResultBaseBean implements Serializable{  
      
        private static final long serialVersionUID = 7755697531438413626L;  
          
        /** 
         * 请求结果 1代表成功  0代表失败 
         * 默认为成功 
         */  
        protected int result = 1;  
        /** 
         * 错误编码  
         * 只有当 result为0时才有 errorCode 和 errorMsg 
         */  
        protected String errorCode = "";  
        /**错误信息*/  
        protected String errorMsg = "";  
          
        protected Object data = null;  
          
        public Object getData() {  
            return data;  
        }  
      
        public void setData(Object data) {  
            this.data = data;  
        }  
      
        public int getResult() {  
            return result;  
        }  
          
        public void setResult(int result) {  
            this.result = result;  
        }  
          
        public String getErrorCode() {  
            return errorCode;  
        }  
      
        public void setErrorCode(String errorCode) {  
            this.errorCode = errorCode;  
        }  
      
        public String getErrorMsg() {  
            return errorMsg;  
        }  
          
        public void setErrorMsg(String errorMsg) {  
            this.errorMsg = errorMsg;  
        }  
          
        public JSONObject toJsonObject(){  
            JSONObject jo = new JSONObject();  
            jo.put("result", result);  
            jo.put("errorCode", errorCode);  
            jo.put("errorMsg", errorMsg);  
            jo.put("data", data.toString());  
            return jo;  
        }  
          
        @Override  
        public String toString() {  
            return this.toJsonObject().toString();  
        }  
    }  
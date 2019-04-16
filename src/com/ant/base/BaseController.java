package com.ant.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @描述 基础Controller,执行前记录request,response,session<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-7
 */
public class BaseController {
	/**
	 * request
	 */
	protected HttpServletRequest request;
	
	/**
	 * response
	 */
	protected HttpServletResponse response;
	
	/**
	 * session
	 */
	protected HttpSession session;
	
	/**
	 * 分页--页数
	 */
	protected Integer page;
	
	/**
	 * 分页--行数
	 */
	protected Integer rows;
	
	/**
	 * @描述 Controller执行前先记录当前的request,response,session<br>
	 * @param request 请求对象
	 * @param response 返回对象
	 * @author 陈之晶
	 * @版本 v1.0.0
	 * @日期 2017-6-7
	 */
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    } 
    
    @ModelAttribute  
    public void setPagenationParams(Integer page,Integer rows){
    	this.page = page==null?0:page;
    	this.rows = rows==null?10:rows;
    }

    /**
     * 输出JSON数据
     *
     * @param response
     * @param jsonStr
     */
    public void writeJson(HttpServletResponse response, String jsonStr) {
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(jsonStr);
            pw.flush();
        } catch (Exception e) {
        }finally{
            if(pw!=null){
                pw.close();
            }
        }
    }
    /**
     *
     * 向页面响应json字符数组串流.
     *
     * @param response
     * @param jsonStr
     * @throws IOException
     * @return void
     * @author 蒋勇
     * @date 2015-1-14 下午4:18:33
     */
    public void writeJsonStr(HttpServletResponse response, String jsonStr) throws IOException {

        OutputStream outStream = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            outStream = response.getOutputStream();
            outStream.write(jsonStr.getBytes("UTF-8"));
            outStream.flush();
        } catch (IOException e) {
        } finally {
            if(outStream!=null){
                outStream.close();
            }
        }
    }

    public void writeJsonStr(HttpServletResponse response, InputStream in) throws IOException {

        if(null == in ){
            return ;
        }
        OutputStream outStream = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            outStream = response.getOutputStream();
            int len = 0;
            byte[] byt = new byte[1024];
            while ((len = in.read(byt)) != -1) {
                outStream.write(byt, 0, len);
            }
            outStream.flush();

        } catch (IOException e) {
        } finally {
            if(outStream!=null){
                outStream.close();
                in.close();
            }
        }
    }


    /**
     * 输出JSON数据
     *
     * @param response
     * @param jsonStr
     */
    public void writeJson(HttpServletResponse response, Object obj) {
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        JSONObject gson = new JSONObject();
        try {
            pw = response.getWriter();
            pw.write(gson.toJSONString(obj));

            pw.flush();
        } catch (Exception e) {
        }finally{
            if(pw!=null){
                pw.close();
            }
        }
    }




    public void writeHtml(HttpServletResponse response, String html) {
        response.setContentType("text/html;;charset=utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(html);
            pw.flush();
        } catch (Exception e) {
        }finally{
            if(pw!=null){
                pw.close();
            }
        }
    }
}

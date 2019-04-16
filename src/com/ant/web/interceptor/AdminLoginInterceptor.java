package com.ant.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ant.glob.GolbParams;
import com.ant.pojo.User;

/**
 * @描述 管理员登录验证拦截器<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-13
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter{

	/***
	 * 判断管理员是否登录
	 */
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getSession().getAttribute(GolbParams.Manager);
		if(user==null){
			 response.sendRedirect(request.getServletContext().getContextPath());  
			return false;
		}
		return true;
	}
}

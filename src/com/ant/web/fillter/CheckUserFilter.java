package com.ant.web.fillter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ant.app.model.AntResponse;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.dao.inte.BaseDaoI;
import com.ant.pojo.Account;
import com.ant.pojo.User;
import com.ant.util.StrUtils;

public class CheckUserFilter implements Filter {

	private String excludedPages;
	private String[] excludedPageArray;

	BaseDaoI dao;
	
	public void destroy() {
	}
	
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		boolean isExcludedPage = false;
		for (String page : excludedPageArray) {// 判断是否过滤
			if (((HttpServletRequest) request).getServletPath().equals(page)) {
				isExcludedPage = true;
				break;
			}
		}

		if (isExcludedPage){
			chain.doFilter(request, response);
		}else{// 过滤
			AntResult antResult = new AntResult();
			HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        
	        String userIdStr = req.getHeader("userId");
	        String loginState = req.getHeader("loginState");
			if(!StrUtils.checkNull(userIdStr)&&!StrUtils.checkNull(loginState)){//Header中userId不为空
				Integer userId = Integer.parseInt(userIdStr);
				User user = dao.getById(User.class, userId);
				
				if(!user.getLoginState().equals(loginState)){
					antResult.setType(AntType.ANT_310);
					responseOutWithJson(res, AntResponse.response(antResult));
				}
				
				if(user.getIsDel()==1){//用户被删除
					antResult.setType(AntType.ANT_305);
					responseOutWithJson(res, AntResponse.response(antResult));
				}else{
					String getAccountHql = "from Account where userId=:userId";
					Map<String,Object> getAccountParams = new HashMap<String, Object>();
					getAccountParams.put("userId", userId);
					Account account = dao.getByHql(getAccountHql, getAccountParams);
					if(account.getState()==0){//用户未激活
						antResult.setType(AntType.ANT_300);
						responseOutWithJson(res, AntResponse.response(antResult));
					}
					if(account.getState()==1){
						chain.doFilter(request, response);
					}
					if(account.getState()==2){//用户被冻结
						antResult.setType(AntType.ANT_102);
						responseOutWithJson(res, AntResponse.response(antResult));
					}
				}
			}else{
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		excludedPages = fConfig.getInitParameter("excludedPages");
		if (StringUtils.isNotEmpty(excludedPages)) {
			excludedPageArray = excludedPages.split(",");
		}
		 ServletContext context = fConfig.getServletContext();  
	     ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);  
	     dao = (BaseDaoI) ctx.getBean("userDao");
		return;
	}

    /**  
     * 以JSON格式输出  
     * @param response  
     */    
    protected void responseOutWithJson(HttpServletResponse response, String jsonString) {    
        //将实体对象转换为JSON Object转换    
        response.setCharacterEncoding("UTF-8");    
        response.setContentType("application/json; charset=utf-8");    
        PrintWriter out = null;    
        try {    
            out = response.getWriter();    
            out.append(jsonString);    
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            if (out != null) {    
                out.close();    
            }    
        }    
    } 	
	
}

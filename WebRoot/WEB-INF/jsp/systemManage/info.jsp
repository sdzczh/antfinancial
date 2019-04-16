<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>蚂蚁理财管理中心</title>

<%@include file="../../../common/common-css.jsp" %>
<%@include file="../../../common/common-js.jsp" %>
</head>
<body>
<%@include file="../../../common/left-menum.jsp" %>
<%--
<%@include file="common/top.jsp" %>
--%>
<div class="right-content sysinfo">
    		<table class="table table-bordered">
    			<tr>
    				<td colspan="2" style="background-color: #f5fafe">用户信息</td>
    			</tr>
    			<tr>
    				<td>账号</td>
    				<td>${userInfo.userAccount}</td>
    			</tr>
    			<tr>
    				<td>类型</td>
    				<td>
    				<c:if test="${userInfo.userRole==0}">
    					会员用户
    				</c:if>
    				<c:if test="${userInfo.userRole==1}">
    					系统管理员
    				</c:if>
    				</td>
    			</tr>
    			<tr> 
    				<td>冻结会员</td>
    				<td>${frozenCount}</td>
    			</tr>
    			<tr>
    				<td>未激活会员</td>
    				<td>${deleteCount}</td>
    			</tr>
    			<tr>
    				<td>上次登陆时间</td>
    				<td>${loginDate}</td>
    			</tr>
    			<tr>
    				<td>上次登陆IP</td>
    				<td>${loginIp}</td>
    			</tr>
   			</table>
   			<table class="table table-bordered">
    			<tr>
    				<td colspan="2" style="background-color: #f5fafe">服务器信息</td>
    			</tr>
    			<tr>
    				<td>客户端浏览器信息</td>
    				<td>${agent}</td>
    			</tr>
    			<tr>
    				<td>服务器IP地址</td>
    				<td>${serviceIp}</td>
    			</tr>
    			<tr>
    				<td>服务器名称</td>
    				<td>${serverName}</td>
    			</tr>
    			<tr>
    				<td>服务器端口</td>
    				<td>${serverPort}</td>
    			</tr>
    			<tr>
    				<td>服务器当前时间</td>
    				<td id="serverDate">
    				</td>
    			</tr>
	    	</table>
</div>
<script type="text/javascript">
	$(function(){
		$('#manage-system').trigger('click');
		$("#system-info").addClass("active");
	})
	
	setInterval("flushTime()",50)
	function flushTime(){
		var myDate = new Date();  
		var year = myDate.getFullYear();
		var mon = myDate.getMonth();
		var day = myDate.getDate();
		var h = myDate.getHours();
		var m = myDate.getMinutes();
		var s = myDate.getSeconds();
	  $("#serverDate").text(year+"-"+(mon+1)+"-"+day+" "+h+":"+m+":"+s);
	  }
</script>
</body>
</html>
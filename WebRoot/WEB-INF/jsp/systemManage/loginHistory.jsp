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
<br/><br/>
		<form class="form-inline" action="getLoginHistoryById.action" id="getLoginHistoryByIdFrom">
		<div class=" row">
			<div class="col-md-12">
			<input type="text" class="form-control" id="loginAccount" name="loginAccount" value="${loginAccount }" placeholder="用户账号">
			 <input type="text" id="createTimeStart" name="createTimeStart" value="${createTimeStart }" placeholder="开始登录日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})">
		    -
		    <input type="text" id="createTimeEnd" name="createTimeEnd" value="${createTimeEnd }" placeholder="结束登录日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeStart\')}'})">
			
			<button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
	<div class="panel panel-info">
		<div class="panel-heading"><h3 class="panel-title">登录记录</h3></div>
    	<div class="panel-body">
    		<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>用户账号</td>
	    				<td>用户姓名</td>
	    				<td>用户角色</td>
	    				<td>登录时间</td>
	    				<td>登录IP</td>
	    			</tr>
    			</thead>
    			<c:forEach var="history" items="${data}">
    				<tr>
    					<td>${history.userAccount}</td>
    					<td>${history.userName}</td>
    					<td>
    						<c:if test="${history.userRole==0}">
    							会员用户
    						</c:if>
    						<c:if test="${history.userRole==1}">
    							管理员 
    						</c:if>
    					</td>
    					
    					<td>${history.loginDate}</td>
    					<td>${history.loginIp}</td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div><div class="text-center">共${count }条</div>
    	</div>
   	</div>
    		
</div>	    	
<script type="text/javascript">
	$("#resetBtn").click(function() {
			$("#loginAccount").val("");
			$("#createTimeStart").val("");
			$("#createTimeEnd").val("");
			$("#getLoginHistoryByIdFrom").submit();
		});
	$(function(){
		$('#manage-system').trigger('click');
		$("#system-login-history").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count) {
				window.location.href="${domain}/system/getLoginHistoryById.action?page="+(pagecount-1)+"&&rows="+size+"&loginAccount="+$("#loginAccount").val()
				+"&createTimeStart="+$("#createTimeStart").val()+"&createTimeEnd="+$("#createTimeEnd").val();
			}
		});	
	})
	
	
</script>
</body>
</html>
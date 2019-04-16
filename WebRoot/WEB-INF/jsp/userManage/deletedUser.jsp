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
<div class="right-content sysinfo" id="deletedUser">
		<br/><br/>
		<form class="form-inline" action="getDeletedUserList.action" id="getDeletedUserFrom">
		<div class=" row">
			<div class="col-md-12">
			<input type="text" class="form-control" id="userAccount" name="userAccount" value="${userAccount}" placeholder="用户账户">
			<input type="text" class="form-control" id="userCode" name="userCode" value="${userCode}" placeholder="用户ID">
			<input type="text" class="form-control" id="userName" name="userName" value="${userName}" placeholder="用户姓名">
			<input type="text" class="form-control" id="referCode" name="referCode" value="${referCode}" placeholder="推荐人ID">
			<select class="form-control" id="accountState" name="accountState">
		    	<option value="">--请选择账户状态--</option>
		    	<option  value="0" <c:if test="${accountState=='0'}">selected="selected"</c:if>>未激活</option>
		    	<option value="1" <c:if test="${accountState=='1'}">selected="selected"</c:if>>已激活</option>
		    	<option value="2" <c:if test="${accountState=='2'}">selected="selected"</c:if>>已冻结</option>
		    </select>
			<button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
<div class="panel panel-info">
	<div class="panel-heading">
		<h3 class="panel-title">删除用户</h3>
	</div>
	<div class="panel-body">
		<!-- <div class="details">
			用户账号:<input class="form-control" style="width: 200px;">
		</div> -->
		
		<table class="table table-bordered table-striped table-hover" style="margin-top: 10px;">
    			<thead>
	    			<tr>
	    				<td>用户账户</td>
	    				<td>用户ID</td>
	    				<td>用户姓名</td>
	    				<td>推荐人ID</td>
	    				<td>增值包数量</td>
	    				<td>注册时间</td>
	    				<td>激活时间</td>
	    				<td>账户状态</td>
	    				<!-- <td>操作</td> -->
	    			</tr>
    			</thead>
    			<c:forEach var="user" items="${data}">
    				<tr>
	    				<td>${user.userAccount}</td>
	    				<td>${user.userCode}</td>
	    				<td>${user.userName}</td>
	    				<td>${user.referUserCode}</td>
	    				<td>${user.packageNum}</td>
	    				<td>${user.createTime}</td>
	    				<td>${user.activeTime}</td>
	    				<td>
	    					<c:if test="${user.state==0}">未激活</c:if>
	    					<c:if test="${user.state==1}">已激活</c:if>
							<c:if test="${user.state==2}">已冻结</c:if>
	    				</td>
	    				<%-- <td>
							<span class="fa-stack fa-lg">
							  <a title="恢复" onclick="replyUser(${user.id})">
						  	  <i class="fa fa-reply fa-stack-2x"></i>
							  </a>
							</span>
							<span class="fa-stack fa-lg">
							  <a title="删除" id="deleteUser" onclick="deleteUser(${user.id})">
							  	<i class="fa fa-trash-o fa-stack-2x"></i>
							  </a>
							</span>
						</td> --%>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div><div class="text-center">共${count }条</div>
		</div>
	</div>
</div>	    	
<script type="text/javascript">
var currentUrl=window.location.href;
	$(function(){
		$('#manage-user').trigger('click');
		$("#user-del").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count) {
				window.location.href="${domain}/manager/user/getDeletedUserList.action?page="+(pagecount-1)+"&&rows="+size+"&userCode="+$("#userCode").val()+
					"&userName="+$("#userName").val()+"&referCode="+$("#referCode").val()+"&accountState="+$("#accountState").val()
					+"&userAccount="+$("#userAccount").val();
			}
		});	
	});
	
	$("#resetBtn").click(function() {
			$("#userAccount").val("");
			$("#userCode").val("");
			$("#userName").val("");
			$("#referCode").val("");
			$("#accountState").val("");
			$("#getDeletedUserFrom").submit();
		});
	function replyUser(userId){
		layer.confirm('确定恢复？',{
			icon: 3, title:'提示'
		},function(index){
			$.ajax({
				type: "POST",
				url: "${domain}/manager/user/userReply.action",
				data: "userId=" + userId,
				dataType: "json",
				async: false,
				success: function (data, textStatus, jqXHR) {
					if (data.success) {
						layer.alert('恢复成功!',{icon:1},function(index){
							window.location.href=currentUrl;
						});
					} else {
						layer.alert('恢复失败!',{icon:2});
					}
				}
			});
		});
	};
	
	function deleteUser(userId){
		layer.confirm('确定删除？',{
			icon: 3, title:'提示'
		},function(index){
			$.ajax({
				type: "POST",
				url: "${domain}/manager/user/userDeletePerm.action",
				data: "userId=" + userId,
				dataType: "json",
				async: false,
				success: function (data, textStatus, jqXHR) {
					if (data.success) {
						layer.alert('删除成功!',{icon:1},function(index){
							window.location.href=currentUrl;
						});
					} else {
						layer.alert('删除失败!',{icon:2});
					}
				}
			});
		});
	}
</script>
</body>
</html>
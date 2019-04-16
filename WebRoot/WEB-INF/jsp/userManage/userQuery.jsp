<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>蚂蚁理财管理中心</title>

<%@include file="../../../common/common-css.jsp"%>
<%@include file="../../../common/common-js.jsp"%>
</head>
<body>
	<%@include file="../../../common/left-menum.jsp"%>
	<%--
<%@include file="common/top.jsp" %>
--%>
	<div class="right-content sysinfo" id="userList">
	<br/><br/>
		<form class="form-inline" action="getUserList.action" id="getUserFrom">
		<div class=" row">
			<div class="col-md-12">
			  <input type="text" class="form-control" id="userAccount" name="userAccount" value="${userAccount}" placeholder="用户账户">
			  <input type="text" class="form-control" id="userCode" name="userCode" value="${userCode}" placeholder="用户ID">
			<input type="text" class="form-control" id="userName" name="userName" value="${userName}" placeholder="用户姓名">
			<input type="text" class="form-control" id="referCode" name="referCode" value="${referCode}" placeholder="推荐人ID">
			<select class="form-control" id="accountState" name="accountState">
		    	<option value="">--请选择账户状态--</option>
		    	<option value="0" <c:if test="${accountState=='0'}">selected="selected"</c:if>>未激活</option>
		    	<option value="1" <c:if test="${accountState=='1'}">selected="selected"</c:if>>已激活</option>
		    	<option value="2" <c:if test="${accountState=='2'}">selected="selected"</c:if>>已冻结</option>
		    </select>
		    <input type="text" id="createTimeStart" name="createTimeStart" value="${createTimeStart }" placeholder="开始注册时间" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})">
		    -
		    <input type="text" id="createTimeEnd" name="createTimeEnd" value="${createTimeEnd }" placeholder="结束注册时间" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createTimeStart\')}'})">
		    			
			</div>

		</div>
		<div  class="row">
			<div  class="col-md-4">
				<input type="text" id="activeTimeStart" name="activeTimeStart" value="${activeTimeStart }" placeholder="开始激活日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'activeTimeEnd\')}'})">
			    -
			    <input type="text" id="activeTimeEnd" name="activeTimeEnd" value="${activeTimeEnd }" placeholder="结束激活日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'activeTimeStart\')}'})">
				
			</div>
		</div>
		<br>
		<div class="row">
				<div class="col-md-4 col-md-offset-4">
				    <button type="submit" class="btn btn-primary">搜&nbsp;索</button>
				  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
				  	<button type="button" class="btn btn-primary" id="addUser">添&nbsp;加</button> 
				  	<button type="button" class="btn btn-primary" id="deleteUsers">删&nbsp;除</button> 
				  	<button type="button" class="btn btn-primary" id="freezeUsers">冻&nbsp;结</button>
				  	<button type="button" class="btn btn-primary" id="activateUsers">解&nbsp;冻</button>
				</div>	
					
			</div>
		<br>
		<div class="panel panel-info">
			<div class="panel-heading">
				<h3 class="panel-title">会员列表</h3>
			</div>
			<div class="panel-body">
			
				<table class="table table-bordered table-striped table-hover">
					<thead>
						<tr>
							<td><input type="checkbox" name="selectAll" id="selectAll"></td>
		    				<td>用户账户</td>
							<td>用户ID</td>
		    				<td>用户姓名</td>
		    				<td>推荐人账号</td>
		    				<td>推荐人ID</td>
		    				<td>增值包数量</td>
		    				<td>注册时间</td>
		    				<td>激活时间</td>
		    				<td>账户状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<c:forEach var="user" items="${data}">
						<tr>
							<td><input type="checkbox" id="ck1" name="userIds"  value="${user.id }" ></td>
		    				<td>${user.userAccount}</td>
							<td>${user.userCode}</td>
		    				<td>${user.userName}</td>
		    				<td>${user.referUserAccount}</td>
		    				<td>${user.referUserCode}</td>
		    				<td>${user.packageNum}</td>
		    				<td>${user.createTime}</td>
		    				<td>${user.activeTime}</td>
							<td><c:if test="${user.state==0}">未激活</c:if> <c:if
									test="${user.state==1}">已激活</c:if> <c:if
									test="${user.state==2}">已冻结</c:if></td>
							<td><c:if test="${user.userRole==0 || user.userRole==3}">
									<span class="fa-stack fa-lg"> <a title="修改"
										id="modifyUser" onclick="modifyUser(${user.id})"> <i
											class="fa fa-wrench fa-stack-2x"></i>
									</a>
									</span>
									<span class="fa-stack fa-lg"> <a title="删除"
										id="deleteUser" onclick="deleteUser(${user.id})"> <i
											class="fa fa-trash-o fa-stack-2x"></i>
									</a>
									</span>
									<c:if test="${user.state==1 && user.userRole!=3}">
										<span class="fa-stack fa-lg"> <a title="冻结账户"
											id="deleteUser" onclick="freezeUser(${user.id})"> <i
												class="fa fa-user-times fa-stack-2x"></i>
										</a>
										</span>
									</c:if>
									<c:if test="${user.state==2 && user.userRole!=3}">
										<span class="fa-stack fa-lg"> <a title="解冻账户"
											id="modifyUser" onclick="activateUser(${user.id})"> <i
												class="fa fa-user fa-stack-2x"></i>
										</a>
										</span>
									
									</c:if>
								</c:if>
								<c:if test="${user.userRole==0}">
										<span class="fa-stack fa-lg"> <a title="修改账户身份"
										id="updateRole" onclick="updateRole(${user.id})"> <i
											class="fa fa-pencil fa-fw fa-stack-2x"></i>
									</a>
									</span>
								</c:if>	
								<c:if test="${user.userRole==3}">
										<span class="fa-stack fa-lg"> <a title="撤销账户身份"
										id="updateRole" onclick="revoke(${user.id})"> <i
											class="fa fa-undo fa-fw fa-stack-2x"></i>
									</a>
									</span>
								</c:if>	
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pageTool"></div><div class="text-center">共${count }条</div>


			</div>

		</div>
		</form>
	</div>
	<script type="text/javascript">
	var currentUrl=window.location.href;
		$(function() {
			
			$("#manage-user").trigger('click');
			$("#user-list").addClass("active");
			var page = new Paging();
			page.init({
				target : $('#pageTool'),
				pagesize : 10,
				count : parseInt("${count}"),
				current : parseInt("${page}") == 0 ? 1 : (parseInt("${page}") + 1),
				callback : function(pagecount, size, count) {
					window.location.href = "${domain}/manager/user/getUserList.action?page=" + (pagecount - 1) + "&&rows=" + size+"&userCode="+$("#userCode").val()+
					"&userName="+$("#userName").val()+"&referCode="+$("#referCode").val()+"&accountState="+$("#accountState").val()+"&createTimeStart="+$("#createTimeStart").val()
					+"&createTimeEnd="+$("#createTimeEnd").val()+"&activeTimeStart="+$("#activeTimeStart").val()
					+"&userAccount="+$("#userAccount").val();
				}
			});
		});
	
		$("#addUser").click(function() {
			window.location.href = "${domain}/manager/user/toUserAdd.action";
		});
		$("#resetBtn").click(function() {
			$("#userCode").val("");
			$("#userAccount").val("");
			$("#userName").val("");
			$("#referCode").val("");
			$("#accountState").val("");
			$("#createTimeStart").val("");
			$("#createTimeEnd").val("");
			$("#activeTimeStart").val("");
			$("#activeTimeEnd").val("");
			$("#getUserFrom").submit();
		});
		function modifyUser(userId) {
			window.location.href = "${domain}/manager/user/toUserModify.action?userId=" + userId+"&currentUrl="+encodeURIComponent(currentUrl);
		}
		$("#selectAll").click(function(){
		var isChecked = $(this).prop("checked");
	    $("input[name='userIds']").prop("checked", isChecked);	
	});
		$("#deleteUsers").click(function(){
			if($("input[name='userIds']:checked").length==0){
			layer.alert('请至少选择一条记录！', { icon : 2 });
			}else{
			var selectIds = "";
					$("input[name='userIds']:checked").each(function(){
						selectIds = selectIds+$(this).val()+",";
					});
					deleteUser(selectIds.substring(0,selectIds.length-1));
			}
			
		});
		function deleteUser(userId) {
			layer.confirm('确定删除？', {
				icon : 3,
				title : '提示'
			}, function(index) {
				$.ajax({
					type : "POST",
					url : "${domain}/manager/user/userDelete.action",
					data : "userIds=" + userId,
					dataType : "json",
					async : false,
					success : function(data, textStatus, jqXHR) {
						if (data.success) {
							layer.alert('删除成功!', {
								icon : 1
							}, function(index) {
								window.location.href = currentUrl;
							});
						} else {
							if(data.errCode=='1'){
								layer.alert(data.errMsg, { icon : 2 });
							}else{
								layer.alert('删除失败!', { icon : 2 });
							}
						}
					}
				});
			});
		}
		;
		function updateRole(userId) {
		var msg = " 确定把该账户设定为收益账号？";
			if (confirm(msg) == true) {
			$.post("${domain}/manager/user/updateRole.action", {
					userId:userId,
				}, function(result) {
					if (result == "true") {
						layer.alert('修改成功!', {
								icon : 1
							}, function(index) {
								window.location.href = currentUrl;
							});
					}
					else
						layer.alert('修改失败，已有该账户或该账户不是普通会员!', {icon : 2});
				});
			}
		}
		function revoke(userId) {
		var msg = " 确定撤销该账户的收益账号身份？";
			if (confirm(msg) == true) {
			$.post("${domain}/manager/user/revoke.action", {
					userId:userId,
				}, function(result) {
					if (result == "true") {
						layer.alert('撤销成功!', {
								icon : 1
							}, function(index) {
								window.location.href = currentUrl;
							});
					}
					else
						layer.alert('撤销失败!', {icon : 2});
				});
			}
		}
		
		$("#activateUsers").click(function(){
			if($("input[name='userIds']:checked").length==0){
			layer.alert('请至少选择一条记录！', { icon : 2 });
			}else{
			var selectIds = "";
					$("input[name='userIds']:checked").each(function(){
						selectIds = selectIds+$(this).val()+",";
					});
					activateUser(selectIds.substring(0,selectIds.length-1));
			}
			
		});
		
		function activateUser(userId) {
			layer.confirm('确定解冻账户？', {
				icon : 3,
				title : '提示'
			}, function(index) {
				$.ajax({
					type : "POST",
					url : "${domain}/account/activateUser.action",
					data : "userIds=" + userId,
					dataType : "json",
					async : false,
					success : function(data, textStatus, jqXHR) {
						if (data.success) {
							layer.alert('解冻成功!', {
								icon : 1
							}, function(index) {
								window.location.href = currentUrl;
							});
						} else {
							if(data.errCode=='1'){
								layer.alert(data.errMsg, { icon : 2 });
							}else{
								layer.alert('解冻失败!', { icon : 2 });
							}
						}
					}
				});
			});
		}
		
		$("#freezeUsers").click(function(){
			if($("input[name='userIds']:checked").length==0){
			layer.alert('请至少选择一条记录！', { icon : 2 });
			}else{
			var selectIds = "";
					$("input[name='userIds']:checked").each(function(){
						selectIds = selectIds+$(this).val()+",";
					});
					freezeUser(selectIds.substring(0,selectIds.length-1));
			}
			
		});
		
		function freezeUser(userId) {
			layer.confirm('确定冻结账户？', {
				icon : 3,
				title : '提示'
			}, function(index) {
				$.ajax({
					type : "POST",
					url : "${domain}/account/freezeUser.action",
					data : "userIds=" + userId,
					dataType : "json",
					async : false,
					success : function(data, textStatus, jqXHR) {
						if (data.success) {
							layer.alert('冻结成功!', {
								icon : 1
							}, function(index) {
								window.location.href = currentUrl;
							});
						} else {
							if(data.errCode=='1'){
								layer.alert(data.errMsg, { icon : 2 });
							}else{
								layer.alert('冻结失败!', {
									icon : 2
								});
							}
						}
					}
				});
			});
		}
	</script>
</body>
</html>
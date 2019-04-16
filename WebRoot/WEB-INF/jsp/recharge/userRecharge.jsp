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
		<form class="form-inline" action="getUserRecharge.action" id="getUserRecharge">
		<div class=" row">
			<div class="col-md-12">
			<input type="text" class="form-control" id="userAccount" name="userAccount" value="${userAccount }" placeholder="用户联系方式">
			<input type="text" class="form-control" id="userName" name="userName" value="${userName }" placeholder="用户姓名">

			<button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
	<div class="panel panel-info">
		<div class="panel-heading"><h3 class="panel-title">人工充值</h3></div>
    	<div class="panel-body">
		
		<table class="table table-bordered table-striped table-hover" style="margin-top: 10px;">
    			<thead>
	    			<tr>
	    				<td style="display:none">用户ID</td>
	    				<td>用户联系方式</td>
	    				<td width="7%">用户姓名</td>
	    				<td width="5%">账户状态</td>
	    				<td colspan="5">D钱包</td>
	    				<td colspan="5">J钱包</td>
	    				<td colspan="5">Z钱包</td>
	    			</tr>
    			</thead>
    			<c:forEach var="user" items="${data}">
    				<tr>
	    				<td style="display:none">${user.id}</td>
	    				<td >${user.userAccount}</td>
	    				<td>${user.userName}</td>
	    				<td><c:if test="${user.state==0}">未激活</c:if> <c:if
									test="${user.state==1}">已激活</c:if> <c:if
									test="${user.state==2}">已冻结</c:if></td>
	    				
	    					<td align="right">${user.packageD}</td>
	    					<td width="2%"><a title="增加" onclick="addD(${user.id},${user.state})"><i class="fa fa-plus-square "></i></a></td>  
	    					<td width="2%"><a title="减少" onclick="delD(${user.id},${user.state})"><i class="fa fa-minus-square "></i></a> </td>      
	    					<td width="4%"><input type="number" id="addAD${user.id}" style="width:45px"></td>   
	    					<td align="left"><a  onclick="addAD(${user.id},${user.state})" style="text-decoration: none;">充值</a> </td>
	    				
	    					<td align="right">${user.packageJ}</td>
	    					<td width="2%"><a title="增加" onclick="addJ(${user.id},${user.state})"><i class="fa fa-plus-square "></i></a></td>   
	    					<td width="2%"><a title="减少" onclick="delJ(${user.id},${user.state})"><i class="fa fa-minus-square "></i></a></td>       
	    					<td width="4%"><input type="number" id="addAJ${user.id}" style="width:45px"> </td>  
	    					<td align="left"><a  onclick="addAJ	(${user.id},${user.state})" style="text-decoration: none;">充值</a> </td>
	    					
	    					<td align="right">${user.packageZ}</td>
	    				    <td width="2%"><a title="增加" onclick="addZ(${user.id},${user.state})"><i class="fa fa-plus-square "></i></a>  </td> 
	    					<td width="2%"><a title="减少" onclick="delZ(${user.id},${user.state})"><i class="fa fa-minus-square "></i></a> </td>      
	    					<td width="4%"><input type="number" id="addAZ${user.id}" style="width:45px">   </td>
	    					<td align="left"><a  onclick="addAZ(${user.id},${user.state} )"  style="text-decoration: none;">充值</a> </td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div>
		</div>
	</div>
</div>	    	
<script type="text/javascript">
	$(function(){
		$('#manage-recharge').trigger('click');
		$("#recharge-uesr").addClass("active");
	})
	var currentUrl=window.location.href;
	$("#resetBtn").click(function() {
			$("#userAccount").val("");
			$("#userName").val("");
			$("#getUserRecharge").submit();
		});
	$(function(){
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count) {
				window.location.href="${domain}/manager/user/getUserRecharge.action?page="+(pagecount-1)+"&&rows="+size+"&userAccount="+$("#userAccount").val()
				+"&userName="+$("#userName").val();
			}
		});	
	})
	
	function addD(userId,state){
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			$.post("${domain}/manager/user/addOneMoney.action", {
					userId:userId,
					wallet:'packageD'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
		}
	function addJ(userId,state){
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			$.post("${domain}/manager/user/addOneMoney.action", {
					userId:userId,
					wallet:'packageJ'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
		}		
	function addZ(userId,state){
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			$.post("${domain}/manager/user/addOneMoney.action", {
					userId:userId,
					wallet:'packageZ'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
		}
		
	function delD(userId,state){
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			$.post("${domain}/manager/user/delOneMoney.action", {
					userId:userId,
					wallet:'packageD'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
		}
	function delJ(userId,state){
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			$.post("${domain}/manager/user/delOneMoney.action", {
					userId:userId,
					wallet:'packageJ'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
		}		
	function delZ(userId,state){
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			$.post("${domain}/manager/user/delOneMoney.action", {
					userId:userId,
					wallet:'packageZ'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
		}
	
	function addAD(id,state) {
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			var x = document.getElementById("addAD"+id).value;
			if (x == "") {
				layer.alert('请填写数额!', {icon : 3});
				return false;
			}else
			/* var reg=/^([A-Za-z]|[\u4E00-\u9FA5])+$/;
			if(reg.text(x)){
				layer.alert('请填写正确的金额!', {icon : 3});
				return false;
			}else */
			var msg = "为账户充值" + x + "币？";
			if (confirm(msg) == true) {
				$.post("${domain}/manager/user/changeMoney.action", {
					userId : id,
					num : x,
					wallet:'packageD'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}else
					if (result == "error") {
						layer.alert('充值失败,金额必须为整数!', {icon : 2});
					}else
					if (result == "max") {
						layer.alert('充值失败,金额不能为零!', {icon : 2});
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
			} else {
				return false;
			}
		}
	function addAJ(id,state) {
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			var x = document.getElementById("addAJ"+id).value;
			if (x == "") {
				layer.alert('请填写数额!', {icon : 3});
				return false;
			}
			var msg = "为账户充值" + x + "币？";
			if (confirm(msg) == true) {
				$.post("${domain}/manager/user/changeMoney.action", {
					userId : id,
					num : x,
					wallet:'packageJ'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}else
					if (result == "error") {
						layer.alert('充值失败,金额必须为整数!', {icon : 2});
					}else
					if (result == "max") {
						layer.alert('充值失败,金额不能为零!', {icon : 2});
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
			} else {
				return false;
			}
		}
	function addAZ(id,state) {
			if(state==0){
				layer.alert('账户未激活!', {icon : 3});
				return false;
			}else
			if(state==2){
				layer.alert('账户已被冻结!', {icon : 3});
				return false;
			}
			var x = document.getElementById("addAZ"+id).value;
			if (x == "") {
				layer.alert('请填写数额!', {icon : 3});
				return false;
			}
			var msg = "为账户充值" + x + "币？";
			if (confirm(msg) == true) {
				$.post("${domain}/manager/user/changeMoney.action", {
					userId : id,
					num : x,
					wallet:'packageZ'
				}, function(result) {
					if (result == "true") {
						window.location.href=currentUrl;
					}else
					if (result == "error") {
						layer.alert('充值失败,金额必须为整数!', {icon : 2});
					}else
					if (result == "max") {
						layer.alert('充值失败,金额不能为零!', {icon : 2});
					}
					else
						layer.alert('充值失败!', {icon : 2});
				});
			} else {
				return false;
			}
		}
</script>
</body>
</html>
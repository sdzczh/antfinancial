<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>蚂蚁理财管理中心</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<%@include file="../../../common/common-js.jsp" %>
<%@include file="../../../common/common-css.jsp" %>

<style type="text/css">
	.error{
		box-shadow: 0px 0px 5px red;
	}
</style>
<script type="text/javascript">

	$(function() {
		$.backstretch("${domain}/img/bg.jpg", {
			speed : 500
		});
		
		$("#submitButton").click(function(){
			$("#registration").hide();
			$("input").removeClass("error");
			var userAccount=$("#userAccount").val();
			var userPwd=$("#userPwd").val();
			
			if($.isNull(userAccount)){
				$("#userAccount").addClass("error");
				$("#msg").text("请输入账号");
				$("#registration").show();
				return false;
			}
			if($.isNull(userPwd)){
				$("#userPwd").addClass("error");
				$("#msg").text("请输入密码");
				$("#registration").show();
				return false;
			}
			
			var account = $.base64('encode', userAccount);
			var pwd = hex_md5(userPwd);
			
			$.ajax({
				url:'${domain}/manager/user/adminLogin.action',
				type:'POST',
				dataType:'JSON',
				data:{'userAccount':account,'userPassword':pwd},
				success:function(data){
					if(data.state==true){
						window.location.href='${domain}/system/info.action';
					}else{
						$("#msg").text("账号或密码错误");
						$("#registration").show();
					}
				}
			});
			
		});
		
		document.onkeydown=function(event){
		    var e = event || window.event || arguments.callee.caller.arguments[0];
		   
		    if(e && e.keyCode==13){ // enter 键
		    	$("#submitButton").trigger('click');
		    }		
		}
	})
</script>

</head>

<body>

	<div id="login-page">
		<div class="container">

			<form class="form-login">
				<h2 class="form-login-heading">蚂蚁理财管理中心</h2>
				<div class="login-wrap">
					<input type="text" class="form-control" placeholder="账号" id="userAccount" value="" autofocus> <br> 
					<input type="password" class="form-control" placeholder="密码 " id="userPwd" value="" > <br>
					<button class="btn btn-theme btn-block" type="button" id="submitButton">
						<i class="fa fa-lock"></i> 登录
					</button>
		            <div class="registration" id="registration"  style="display: none;">
						<hr>		            
		                <span id="msg" class="msg"></span><br/>
		            </div>
				</div>
			</form>

		</div>
	</div>

</body>
</html>

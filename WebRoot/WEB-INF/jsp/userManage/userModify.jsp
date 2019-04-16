<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>修改会员信息</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content">
    	<div class="wrapper wrapper-content animated fadeInRight ecommerce">
	    	<form action="" class="form form-horizontal" id="userModifyForm" data-parsley-validate>
	    	<input type="hidden" name="currentUrl" id="currentUrl" value="${currentUrl}"/>
			<div class="panel panel-info">
				<div class="panel-heading"><h3 class="panel-title">修改会员</h3></div>
			    	<div class="panel-body">
	    				<div class="row">
	    					<div class="col-lg-8 col-md-offset-2">
			    		
				    		<div class="form-group">
				    			<label class="control-label  col-sm-2"><span class="text-danger">*</span>用户账号：</label>
								<div class="col-sm-6">
								
									<input type="text" class="form-control" value="${user.userAccount}"  id="user.userAccount" name="userAccount" required="true" readonly="readonly">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>用户姓名：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${user.userName }"  id="user.userName" name="userName" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>推荐人账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${user.referenceAccount }"  id="referenceAccount" name="referenceAccount" required="true"
									 readonly="readonly"	>
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>支付宝账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.alipayNumber }"  id="alipayNumber" name="alipayNumber" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>微信账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.webcatNumber }"  id="webcatNumber" name="webcatNumber" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>银行名称：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.bankName }"  id="account.bankName" name="bankName" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>银行账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.bankNumber }"  id="account.bankNumber" name="bankNumber" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>J钱包：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.packageJ }"  id="account.packageJ" name="packageJ" required="true" readonly="readonly">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>D钱包：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.packageD }"  id="account.packageD" name="packageD" required="true" readonly="readonly">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>Z钱包：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${account.packageZ }"  id="account.packageZ" name="packageZ" required="true" readonly="readonly">
								</div>
				    		</div>
				    		<br>
				    		<div class="form-group">
							    <div class="col-sm-offset-2 col-sm-6">
							      <input type="button" id="submitModify" class="btn btn-w-m btn-success" value="修改">&nbsp;&nbsp;&nbsp;&nbsp;
                    			  <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
							    </div>
							</div>
			    		</div>
			    	</div>
	    		
	    		</div>
	    		
	    	</div>
	    	</form>
    	</div>
    </div>
    
  </body>
  <script type="text/javascript">
  $(function(){
  	$("#manage-user").trigger('click');
		$("#user-list").addClass("active");
  });
  $("#submitModify").click(function(){
 	  var userName = document.getElementById('user.userName').value;
 	  var bankNumber = document.getElementById('account.bankNumber').value;
 	  reg=/^[\u4e00-\u9fa5a-zA-Z]{2,4}$/;
 	  bankReg=/^(\d{16}|\d{19})$/;
 	 
 	  if(userName!=""){
	 	  if(!reg.test(userName)){
	 	  layer.alert('姓名必须为2-4字的中文或英文',{icon:2});
	 	  	return false;
	 	  }
	  }if(bankNumber!=""){
	 	  if(!bankReg.test(bankNumber)){
	 	  layer.alert('卡号不合规范',{icon:2});
	 	  	return false;
	 	  }
 	  } 
	  if($("#userModifyForm").parsley().validate()){
	  if(checkReferenceAccount($("#userAccount").val())){
			layer.alert('用户账号已经注册，请检查!',{icon:2});
		}else{
	  		$("#userModifyForm").ajaxSubmit({
	  			type: 'post',
                dataType: 'json',
                url: "${domain}/manager/user/userModify.action",
                success: function (data, textStatus, jqXHR) {
                    if (data.success) {
                        layer.alert('修改成功!',{icon:1},function(index){
                        	window.location.href=decodeURIComponent($("#currentUrl").val());
                        });
                    } else {
                        layer.alert('修改失败!',{icon:2});
                    }
                }
			});
	  	}
	  		
	  	}
  });
  	
  	$("#back").click(function (){
  		window.history.back(); 
  	});
  	
  	function checkReferenceAccount(referenceAccount){
  		var flag = false;
  		$.ajax({
				type: "POST",
				url: "${domain}/manager/user/checkAccount.action",
				data: "referenceAccount=" + referenceAccount,
				dataType: "json",
				async: false,
				success: function (data, textStatus, jqXHR) {
					if (data.success) {
						flag = true;//账号存在
					}
				}
			});
		return flag;
  	}
  	
  	
  	
  	
  	 
  </script>
</html>

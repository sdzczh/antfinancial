<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>用户添加</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content">
    	<div class="wrapper wrapper-content animated fadeInRight ecommerce">
	    	<form action="" class="form form-horizontal" id="userAddForm" data-parsley-validate>
			<div class="panel panel-info">
				<div class="panel-heading"><h3 class="panel-title">添加会员</h3></div>
			    	<div class="panel-body">
	    				<div class="row">
	    					<div class="col-lg-8 col-md-offset-2">
			    		
				    		<div class="form-group">
				    			<label class="control-label  col-sm-2"><span class="text-danger">*</span>用户账号：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" value=""  id="userAccount" name="userAccount" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>用户姓名：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value=""  id="userName" name="userName" required="true">
								</div>
				    		</div>
				    		<!-- <div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>推荐人账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value=""  id="referenceAccount" name="referenceAccount" required="true" 
									   >
								</div>
				    		</div> -->
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>支付宝账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value=""  id="alipayNumber" name="alipayNumber" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>微信账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value=""  id="webcatNumber" name="webcatNumber" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>银行名称：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value=""  id="bankName" name="bankName" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>银行账号：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value=""  id="bankNumber" name="bankNumber" required="true">
								</div>
				    		</div>
				    		<br>
				    		<div class="form-group">
							    <div class="col-sm-offset-2 col-sm-6">
							      <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="添加">&nbsp;&nbsp;&nbsp;&nbsp;
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
	
	/* window.Parsley.addAsyncValidator('checkAccount', function (xhr) {
               return xhr.responseJSON.data;
           }, '${ctxPath}/api/1/factorLibrary/checkCode?id='+''
   ); */
  });
  $("#submitAdd").click(function(){
 	  var userAccount = $("#userAccount").val();
 	  var userName = $("#userName").val();
 	  var bankNumber = $("#bankNumber").val();
 	  reg=/^[\u4e00-\u9fa5a-zA-Z]{2,4}$/;
 	  phoneReg=/^1[0-9]{10}$/;
 	  bankReg=/^(\d{16}|\d{19})$/;
 	  if(!phoneReg.test(userAccount)){
 	  layer.alert('账号格式不正确',{icon:2});
 	  	return false;
 	  }
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
	  if($("#userAddForm").parsley().validate()){
		if(checkReferenceAccount($("#userAccount").val())){
			layer.alert('用户账号已经注册，请检查!',{icon:2});
		}else{
	  		$("#userAddForm").ajaxSubmit({
	  			type: 'post',
                dataType: 'json',
                url: "${domain}/manager/user/userAdd.action",
                success: function (data, textStatus, jqXHR) {
                    if (data.success) {
                        layer.alert('添加成功!',{icon:1},function(index){
                        	window.location.href="${domain}/manager/user/getUserList.action";
                        });
                    } else {
                        layer.alert('添加失败!',{icon:2});
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

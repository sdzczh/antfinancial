<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>修改参数</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content">
    	<div class="wrapper wrapper-content animated fadeInRight ecommerce">
	    	<form action="" class="form form-horizontal" id="paramModifyForm" data-parsley-validate>
	    	<input type="hidden"  value="${params.id }"  id="id" name="id">
	    	<input type="hidden" name="currentUrl" id="currentUrl" value="${currentUrl}"/>
	    	<div class="panel panel-info">
				<div class="panel-heading"><h3 class="panel-title">修改参数</h3></div>
		    	<div class="panel-body">
		    		<div class="row">
	    		<div class="col-lg-8 col-md-offset-2">
			    	<div class="ibox float-e-margins">
			    		<div class="ibox-title"><h5></h5></div>
			    		<div class="ibox-content">
				    		<div class="form-group">
				    			<label class="control-label  col-sm-2"><span class="text-danger">*</span>变量名：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" value="${params.keyName }"  id="keyName" name="keyName" required="true" readonly>
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>变量值：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${params.val }"  id="val" name="val" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>备注：</label>
								<div class=" col-sm-6">
									<input type="text" class="form-control" value="${params.remark }"  id="remark" name="remark" required="true">
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
		    	</div>
		    </div>
	    	
	    	</form>
    	</div>
    </div>
  </body>
  <script type="text/javascript">
  $(function(){
  	$('#manage-system').trigger('click');
		$("#system-params").addClass("active");
  });
  $("#submitModify").click(function(){
	  if($("#paramModifyForm").parsley().validate()){
	  		$("#paramModifyForm").ajaxSubmit({
	  			type: 'post',
                dataType: 'json',
                url: "${domain}/systemParam/paramModify.action",
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
  });
  	
  	$("#back").click(function (){
  		window.history.back(); 
  	});
  </script>
</html>

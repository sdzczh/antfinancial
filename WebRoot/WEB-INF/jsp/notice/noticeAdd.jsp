<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>公告添加</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content">
    	<div class="wrapper wrapper-content animated fadeInRight ecommerce">
	    	<form action="" class="form form-horizontal" id="noticeAddForm" data-parsley-validate>
	    	<div class="panel panel-info">
				<div class="panel-heading"><h3 class="panel-title">添加公告</h3></div>
		    	<div class="panel-body">
		    		<div class="row">
	    		<div class="col-lg-8 col-md-offset-2">
			    	<div class="ibox float-e-margins">
			    		<div class="ibox-title"><h5></h5></div>
			    		<div class="ibox-content">
				    		<div class="form-group">
				    			<label class="control-label  col-sm-2"><span class="text-danger">*</span>公告标题：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" value=""  id="title" name="title" required="true" data-parsley-maxlength="255">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>公告内容：</label>
								<div class=" col-sm-6">
									<textarea name="content" id="content" cols="" rows="10" class="form-control"  placeholder="请填写内容..." dragonfly="true"  required="true" data-parsley-maxlength="255"></textarea>
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>接受角色：</label>
								<div class="col-sm-6">
									<input name="sendRole" type="radio" id="sex-1" checked value="0" required="true">会员用户
									<input type="radio" id="sex-2" name="sendRole" value="1" required="true">管理员
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
		    	</div>
		    </div>
	    	
	    	</form>
    	</div>
    </div>
  </body>
  <script type="text/javascript">
  $(function(){
  	$('#manage-system').trigger('click');
	$("#system-notice").addClass("active");
  });
  $("#submitAdd").click(function(){
	  if($("#noticeAddForm").parsley().validate()){
	  		$("#noticeAddForm").ajaxSubmit({
	  			type: 'post',
                dataType: 'json',
                url: '${domain}/notice/noticeAdd.action',
                success: function (data, textStatus, jqXHR) {
                    if (data.success) {
                        layer.alert('添加成功!',{icon:1},function(index){
                        	window.location.href="${domain}/notice/notice.action";
                        });
                    } else {
                        layer.alert('添加失败!',{icon:2});
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

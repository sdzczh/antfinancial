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
	    	<form action="" class="form form-horizontal" id="noticeModifyForm" data-parsley-validate>
	    	<input type="hidden" name="currentUrl" id="currentUrl" value="${currentUrl}"/>
	    	<input type="hidden" name="id" id="noticeId" value="${notice.id }"/>
	    	<input type="hidden" name=createDate id="createDate" value="${notice.createDate }"/>
	    	<input type="hidden" name=createId id="createId" value="${notice.createId }"/>
	    	<input type="hidden" name=state id="state" value="${notice.state }"/>
	    	<div class="panel panel-info">
				<div class="panel-heading"><h3 class="panel-title">修改公告</h3></div>
		    	<div class="panel-body">
		    		<div class="row">
	    		<div class="col-lg-8 col-md-offset-2">
			    	<div class="ibox float-e-margins">
			    		<div class="ibox-title"><h5></h5></div>
			    		<div class="ibox-content">
				    		<div class="form-group">
				    			<label class="control-label  col-sm-2"><span class="text-danger">*</span>公告标题：</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" value="${notice.title}"  id="title" name="title" required="true">
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>公告内容：</label>
								<div class=" col-sm-6">
									<textarea name="content" id="content" cols="" rows="10" class="form-control"  placeholder="请填写内容..." dragonfly="true"  required="true">${notice.content}</textarea>
								</div>
				    		</div>
				    		<div class="form-group">
				    			<label class="control-label col-sm-2"><span class="text-danger">*</span>接受角色：</label>
								<div class="col-sm-6">
									<input name="sendRole" type="radio" id="sex-1" checked value="0" required="true" <c:if test="${notice.sendRole==0}">checked="checked"</c:if> >用户
									<input type="radio" id="sex-2" name="sendRole" value="1" required="true" <c:if test="${notice.sendRole==1}">checked="checked"</c:if>>管理员
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
	$("#system-notice").addClass("active");
  });
  $("#submitModify").click(function(){
	  if($("#noticeModifyForm").parsley().validate()){
	  		$("#noticeModifyForm").ajaxSubmit({
	  			type: 'post',
                dataType: 'json',
                url: '${domain}/notice/noticeModify.action',
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

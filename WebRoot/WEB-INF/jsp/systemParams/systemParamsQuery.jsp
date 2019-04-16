<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>公告查询</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content paramsinfo">
    <br/><br/>
		<form class="form-inline" action="paramList.action" id="paramListFrom">
		<div class=" row">
			<div class="col-md-12">
			<input type="text" class="form-control" id="keyName" name="keyName" value="${keyName}" placeholder="变量名">
			<input type="text" class="form-control" id="remark" name="remark" value="${remark}" placeholder="备注">
			<button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
    <div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">参数设置列表</h3>
		</div>
		<div class="panel-body">
			<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>变量名</td>
	    				<td>变量值</td>
	    				<td>备注</td>
	    				<td>操作</td>
	    			</tr>
    			</thead>
    			<c:forEach var="params" items="${paramList}">
    				<tr>
    					<td>${params.keyName}</td>
    					<td>${params.val}</td>
    					<td>${params.remark}</td>
    					<td>
							<span class="fa-stack fa-lg">
							  <a title="修改" id="modifyParam" onclick="modifyParam(${params.id})" >
						  	  <i class="fa fa-wrench fa-stack-2x"></i>
							  </a> 
							</span>
							<%-- <span class="fa-stack fa-lg">
							  <a title="删除" id="paramDelete" onclick="deleteParam(${params.id})">
							  	<i class="fa fa-trash-o fa-stack-2x"></i>
							  </a>
							</span> --%>
						</td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div><div class="text-center">共${count }条</div>
			
		</div>
	</div>
    		
			<!-- <span class="fa-stack fa-lg">
			  <a title="添加" id="addParam">
		  	  <i class="fa fa-plus-circle fa-stack-2x"></i>
			  </a>
			</span> -->
</div>
<script type="text/javascript">
var currentUrl=window.location.href;
	$(function(){
		$('#manage-system').trigger('click');
		$("#system-params").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count) {
				window.location.href="${domain}/systemParam/paramList.action?page="+(pagecount-1)+"&&rows="+size+"&keyName="+$("#keyName").val()+"&remark="+$("#remark").val();
			}
		});	
	});
	$("#resetBtn").click(function() {
			$("#keyName").val("");
			$("#remark").val("");
			$("#paramListFrom").submit();
		});
	$("#addParam").click(function(){
		window.location.href="${domain}/systemParam/toParamAdd.action";
	});
	function modifyParam(paramId){
		window.location.href="${domain}/systemParam/toParamModify.action?paramId="+paramId+"&currentUrl="+encodeURIComponent(currentUrl);
	}
	function deleteParam(paramId){
		layer.confirm('确定删除？',{
			icon: 3, title:'提示'
		},function(index){
			$.ajax({
				type: "POST",
				url: "${domain}/systemParam/paramDelete.action",
				data: "paramId=" + paramId,
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

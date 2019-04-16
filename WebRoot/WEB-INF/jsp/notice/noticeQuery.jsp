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
    <div class="right-content noticeinfo">
    <br/><br/>
		<form class="form-inline" action="notice.action" id="noticeFrom">
		<div class=" row">
			<div class="col-md-12">
			<input type="text" class="form-control" id="title" name="title" value="${title}" placeholder="公告标题">
			<select class="form-control" id="sendRole" name="sendRole">
			    	<option value="">--请选择接受角色--</option>
			    	<option  value="0" <c:if test="${sendRole=='0'}">selected="selected"</c:if>>会员用户</option>
			    	<option value="1" <c:if test="${sendRole=='1'}">selected="selected"</c:if>>管理员</option>
			    </select>
			<button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
    <div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">公告列表</h3>
		</div>
		<div class="panel-body">
			<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>标题</td>
	    				<td>内容</td>
	    				<td>接受角色</td>
	    				<td>创建人</td>
	    				<td>创建时间</td>
	    				<td>操作</td>
	    			</tr>
    			</thead>
    			<c:forEach var="notice" items="${noticeList}">
    				<tr>
    					<td>${notice.title}</td>
    					<td>${notice.content}</td>
    					<td>
    						<c:if test="${notice.sendRole==0}">
    							会员用户
    						</c:if>
    						<c:if test="${notice.sendRole==1}">
    							管理员
    						</c:if>
    					</td>
    					<td>${notice.createName}</td>
    					<td>${notice.createDate}</td>
    					<td>
							<span class="fa-stack fa-lg">
							  <a title="修改" id="modifyNotice" onclick="modifyNotice(${notice.id})" >
						  	  <i class="fa fa-wrench fa-stack-2x"></i>
							  </a>
							</span>
							<span class="fa-stack fa-lg">
							  <a title="删除" id="noticeDelete" onclick="deleteNotice(${notice.id})">
							  	<i class="fa fa-trash-o fa-stack-2x"></i>
							  </a>
							</span>
						</td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div><div class="text-center">共${count }条</div>
			
		</div>
	</div>
    		
			<span class="fa-stack fa-lg">
			  <a title="添加" id="addNotice">
		  	  <i class="fa fa-plus-circle fa-stack-2x"></i>
			  </a>
			</span>
</div>
<script type="text/javascript">
var currentUrl=window.location.href;
	$(function(){
		$('#manage-system').trigger('click');
		$("#system-notice").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count){
				window.location.href="${domain}/notice/notice.action?page="+(pagecount-1)+"&&rows="+size+"&title="+$("#title").val()+"&sendRole="+$("#sendRole").val();
			}
		});	
	});
	$("#resetBtn").click(function() {
			$("#title").val("");
			$("#sendRole").val("");
			$("#noticeFrom").submit();
		});
	$("#addNotice").click(function(){
		window.location.href="${domain}/notice/toNoticeAdd.action";
	});
	function modifyNotice(noticeId){
		window.location.href="${domain}/notice/toNoticeModify.action?noticeId="+noticeId+"&currentUrl="+encodeURIComponent(currentUrl);
	}
	function deleteNotice(noticeId){
		layer.confirm('确定删除？',{
			icon: 3, title:'提示'
		},function(index){
			$.ajax({
				type: "POST",
				url: "${domain}/notice/noticeDelete.action",
				data: "noticeId=" + noticeId,
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

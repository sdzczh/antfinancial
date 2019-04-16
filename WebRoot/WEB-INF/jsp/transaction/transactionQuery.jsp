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
    
    <title>交易记录查询</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content orderinfo layer-photos-demo" id="layer-photos-demo" >
    <br/><br/>
		<form class="form-inline" action="transactionQuery.action" id="transactionForm">
		<div class=" row">
			<div class="col-md-12">
			   <input type="text" class="form-control" id="buyAccount" name="buyAccount" value="${buyAccount }" placeholder="买入会员账号">
			   <input type="text" class="form-control" id="saleAccount" name="saleAccount" value="${saleAccount }"  placeholder="卖出会员账号">
			   <select class="form-control" id="state" name="state">
			    	<option value="">--请选择交易状态--</option>
			    	<option  value="0" <c:if test="${state=='0'}">selected="selected"</c:if>>等待付款</option>
			    	<option value="1" <c:if test="${state=='1'}">selected="selected"</c:if>>确认打款</option>
			    	<option value="2" <c:if test="${state=='2'}">selected="selected"</c:if>>确认收款</option>
			    	<option value="3" <c:if test="${state=='3'}">selected="selected"</c:if>>已取消</option>
			    </select>
			    <input type="text" id="createTimeStart" name="createTimeStart" value="${createTimeStart }" placeholder="开始匹配日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})">
		    	-
		    	<input type="text" id="createTimeEnd" name="createTimeEnd" value="${createTimeEnd }" placeholder="结束匹配日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeStart\')}'})">
			    
			  &nbsp;&nbsp;
			    <button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  		<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
    <div class="panel panel-info">
    
		<div class="panel-heading">
			<h3 class="panel-title">交易记录</h3>
		</div>
		<div class="panel-body " >
			<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>买入会员账户</td>
	    				<td>卖出会员账户</td>
	    				<td>金额</td>
	    				
	    				<td>图片</td>
	    				<td>匹配备注</td>
	    				<td>匹配时间</td>
	    				<td>状态</td>
	    				<td>操作</td>
	    			</tr>
    			</thead>
    			<c:forEach var="trans" items="${transList}">
    				<tr>
    					<td>${trans.buyAccount}</td>
    					<td>${trans.saleAccount}</td>
    					<td>${trans.amount}</td>
    					
    					<td>
    						<c:if test="${trans.imgSrc!=null&&trans.imgSrc!=''}">
    							<img layer-pid="" layer-src="${trans.imgSrc }" src="${trans.imgSrc}" alt="付款凭证" height="50" width="60">
    						</c:if>
							
						</td>
    					<td>${trans.remark}</td>
    					<td>${trans.createTime}</td>
    					<td>
    						<c:if test="${trans.state==0}">
    							等待付款
    						</c:if>
    						<c:if test="${trans.state==1}">
    							确认打款
    						</c:if>
    						<c:if test="${trans.state==2}">
    							确认收款
    						</c:if>
    						<c:if test="${trans.state==3}">
    							已取消
    						</c:if>
    					</td>
    					<td>
				 	 <c:if test="${trans.state=='0'||trans.state=='1'}"> 
 							<span class="fa-stack fa-lg">
							  <a title="取消订单" id="cancelOrder" onclick="cancelOrder(${trans.id})" >
						  	  <i class="fa fa-times-rectangle-o fa-stack-2x"></i>
							  </a>
							</span>
						  </c:if>  
						<c:if test="${trans.state=='1'}">
							<span class="fa-stack fa-lg">
							  <a title="确认收款" id="matchOrder" onclick="confirmCollection(${trans.id})" >
						  	  <i class="fa fa-check-square-o fa-stack-2x"></i>
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
</div>
<script type="text/javascript">

	 
	var currentUrl=window.location.href;
	$(function(){
		layer.photos({
		  photos: '#layer-photos-demo',
		  shift: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		});
		
		$('#manage-transactions').trigger('click');
		$("#transaction-history").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count){
				window.location.href="${domain}/transaction/transactionQuery.action?page="+(pagecount-1)+"&&rows="+size+"&buyAccount="+$("#buyAccount").val()+
					"&saleAccount="+$("#saleAccount").val()+"&state="+$("#state").val()+"&createTimeStart="+$("#createTimeStart").val()+"&createTimeEnd="+$("#createTimeEnd").val();
			}
		});	
	});
	
	$("#resetBtn").click(function() {
			$("#buyAccount").val("");
			$("#saleAccount").val("");
			$("#state").val("");
			$("#createTimeStart").val("");
			$("#createTimeEnd").val("");
			$("#transactionForm").submit();
		});
	function cancelOrder(transId){
		layer.confirm('确定取消？',{
			icon: 3, title:'提示'
		},function(index){
			$.ajax({
				type: "POST",
				url: "${domain}/transaction/cancelOrder.action",
				data: "transId=" + transId,
				dataType: "json",
				async: false,
				success: function (data, textStatus, jqXHR) {
					if (data.success) {
						layer.alert('取消成功!',{icon:1},function(index){
							window.location.href=currentUrl;
						});
					} else {
						layer.alert('取消失败!',{icon:2});
					}
				}
			});
		});
	}
	function confirmCollection(transId){
		layer.confirm('确认收款？',{
			icon: 3, title:'提示'
		},function(index){
			$.ajax({
				type: "POST",
				url: "${domain}/transaction/confirmCollection.action",
				data: "transId=" + transId,
				dataType: "json",
				async: false,
				success: function (data, textStatus, jqXHR) {
					if (data.success) {
						layer.alert('收款成功!',{icon:1},function(index){
							window.location.href=currentUrl;
						});
					} else {
						layer.alert('收款失败!',{icon:2});
					}
				}
			});
		});
	}
	
</script>
  </body>
</html>

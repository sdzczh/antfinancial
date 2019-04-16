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
    
    <title>买入记录查询</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content orderinfo">
    <div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">买入记录</h3>
		</div>
		<div class="panel-body">
			<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>会员账户</td>
	    				<td>会员名称</td>
	    				<td>买入金额</td>
	    				<td>提交日期</td>
	    				<td>状态</td>
	    				<td>操作</td>
	    			</tr>
    			</thead>
    			<c:forEach var="order" items="${orderList}">
    				<tr>
    					<td>${order.userAccount}</td>
    					<td>${order.userName}</td>
    					<td>${order.amount}</td>
    					<td>${order.createDate}</td>
    					<td>
    						<c:if test="${order.state==0}">
    							正在匹配
    						</c:if>
    						<c:if test="${order.state==1}">
    							匹配成功
    						</c:if>
    						<c:if test="${order.state==2}">
    							已过期
    						</c:if>
    					</td>
    					
    					<td>
    					<c:if test="${order.state==0}">
							<span class="fa-stack fa-lg">
							  <a title="匹配" id="matchOrder" onclick="matchOrder(${order.id},${order.amount})" >
						  	  <i class="fa fa-sitemap fa-stack-2x"></i>
							  </a>
							</span>
							</c:if>
						</td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div>
			
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#manage-transactions').trigger('click');
		$("#transaction-buy").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count){
				window.location.href="${domain}/order/orderBuyQuery.action?page="+(pagecount-1)+"&&rows="+size;
			}
		});	
	}); 
	 
	function matchOrder(orderId,amount){
		layer.open({
		  type: 2,  
		  area: ['1250px', '700px'],
		  offset: '100px',
		  content: "${domain}/order/toMatchOrderSale.action?orderId="+orderId+"&amount="+amount 
		}); 
	}
	
	function refashOrder(){
		window.location.href="${domain}/order/orderBuyQuery.action";
	}
</script>
  </body>
</html>

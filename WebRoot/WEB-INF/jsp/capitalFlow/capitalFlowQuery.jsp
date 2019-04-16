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
    
    <title>交易流水查询</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content orderinfo layer-photos-demo" id="layer-photos-demo" >
    <br/><br/>
		<form class="form-inline" action="capitalFlowQuery.action" id="capitalFlowForm">
		<div class=" row">
			<div class="col-md-12">
			<input type="text" class="form-control" id="userAccount" name="userAccount" value="${userAccount}" placeholder="用户账号">
			<select class="form-control" id="packageType" name="packageType">
		    	<option value="">--请选择钱包类型--</option>
		    	<option  value="0" <c:if test="${packageType=='0'}">selected="selected"</c:if>>J钱包</option>
		    	<option value="1" <c:if test="${packageType=='1'}">selected="selected"</c:if>>D钱包</option>
		    	<option value="2" <c:if test="${packageType=='2'}">selected="selected"</c:if>>Z钱包</option>
		    </select>
		    <select class="form-control" id="type" name="type">
		    	<option value="">--请选择操作类型--</option>
		    	<option  value="0" <c:if test="${type=='0'}">selected="selected"</c:if>>静态收益</option>
		    	<option value="1" <c:if test="${type=='1'}">selected="selected"</c:if>>动态收益</option>
		    	<option value="2" <c:if test="${type=='2'}">selected="selected"</c:if>>签到奖励</option>
		    	<option value="3" <c:if test="${type=='3'}">selected="selected"</c:if>>推广奖励</option>
		    	<option value="4" <c:if test="${type=='4'}">selected="selected"</c:if>>买入</option>
		    	<option value="5" <c:if test="${type=='5'}">selected="selected"</c:if>>卖出</option>
		    	<option value="6" <c:if test="${type=='6'}">selected="selected"</c:if>>激活用户</option>
		    	<option value="7" <c:if test="${type=='7'}">selected="selected"</c:if>>转账(J>Z)</option>
		    	<option value="8" <c:if test="${type=='8'}">selected="selected"</c:if>>转账(D>Z)</option>
		    	<option value="9" <c:if test="${type=='9'}">selected="selected"</c:if>>开启增值包</option>
		    	<option value="10" <c:if test="${type=='10'}">selected="selected"</c:if>>订单取消</option>
		    	<option value="11" <c:if test="${type=='11'}">selected="selected"</c:if>>充值</option>
		    </select>
		    <input type="text" id="createTimeStart" name="createTimeStart" value="${createTimeStart }" placeholder="开始交易日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})">
		    -
		    <input type="text" id="createTimeEnd" name="createTimeEnd" value="${createTimeEnd }" placeholder="结束交易日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeStart\')}'})">
		    <button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
			</div>
		</div>
		</form>
		<br>
    <div class="panel panel-info">
    
		<div class="panel-heading">
			<h3 class="panel-title">交易流水</h3>
		</div>
		<div class="panel-body " >
			<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>会员账户</td>
	    				<td>会员姓名</td>
	    				<td>钱包类型</td>
	    				<td>操作类型</td>
	    				<td>金额</td>
	    				<td>手续费</td>
	    				<td>交易时间</td>
	    			</tr>
    			</thead>
    			<c:forEach var="flows" items="${flowList}">
    				<tr>
    					<td>${flows.userAccount}</td>
    					<td>${flows.userName}</td>
    					<td>
    						<c:if test="${flows.packageType==0}">
    							J钱包
    						</c:if>
    						<c:if test="${flows.packageType==1}">
    							D钱包
    						</c:if>
    						<c:if test="${flows.packageType==2}">
    							Z钱包
    						</c:if>
    					</td>
    					<td>
    						<c:if test="${flows.type==0}">
    							静态收益
    						</c:if>
    						<c:if test="${flows.type==1}">
    							动态收益
    						</c:if>
    						<c:if test="${flows.type==2}">
    							签到奖励
    						</c:if>
    						<c:if test="${flows.type==3}">
    							推广奖励
    						</c:if>
    						<c:if test="${flows.type==4}">
    							买入
    						</c:if>
    						<c:if test="${flows.type==5}">
    							卖出
    						</c:if>
    						<c:if test="${flows.type==6}">
    							激活用户
    						</c:if>
    						<c:if test="${flows.type==7}">
    							转账(J>Z) 
    						</c:if>
    						<c:if test="${flows.type==8}">
    							转账(D>Z) 
    						</c:if>
    						<c:if test="${flows.type==9}">
    							开启增值包
    						</c:if>
    						<c:if test="${flows.type==10}">
    							订单取消
    						</c:if>
    						<c:if test="${flows.type==11}">
    							充值
    						</c:if>
    					</td>
    					<td><fmt:formatNumber value="${flows.amount}" pattern="#.##" type="number"/> </td>
    					<td>${flows.fee}</td>
    					<td>${flows.createDateTime}</td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div id="pageTool"></div><div class="text-center">共${count }条</div>
			
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#manage-transactions').trigger('click');
		$("#capital-Flow").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 10, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count){
				window.location.href="${domain}/capitalFlow/capitalFlowQuery.action?page="+(pagecount-1)+"&&rows="+size+"&userAccount="+$("#userAccount").val()+
					"&packageType="+$("#packageType").val()+"&type="+$("#type").val()+"&createTimeStart="+$("#createTimeStart").val()+"&createTimeEnd="+$("#createTimeEnd").val();
			}
		});	
	});
	
	$("#resetBtn").click(function() {
			$("#userAccount").val("");
			$("#packageType").val("");
			$("#type").val("");
			$("#createTimeStart").val("");
			$("#createTimeEnd").val("");
			$("#capitalFlowForm").submit();
		});
	
</script>
  </body>
</html>

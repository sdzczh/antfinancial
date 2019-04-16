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
    
    <title>买入匹配</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
    <div class=" orderinfo">
    <div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">买入匹配&nbsp;&nbsp;&nbsp;&nbsp;（买入金额：${amount }）</h3>
		</div>
		<div class="panel-body">
		<form action="" id="matchForm">
			<input type="hidden" name="buyId" id="buyId" value="${buyId}">
			<input type="hidden" name="amount" id="amount" value="${amount}">
			<table class="table table-bordered table-striped table-hover">
    			<thead>
	    			<tr>
	    				<td>全选/反选</td>
	    				<td>会员账户</td>
	    				<td>会员名称</td>
	    				<td>卖出金额</td>
	    				<td>提交日期</td>
	    			</tr>
    			</thead>
    			<c:forEach var="order" items="${orderList}">
    				<tr>
    					<td><input type="checkbox" id="ck1" name="ids"  value="${order.id }" AMOUNTVALUE="${order.amount}"></td>
    					<td><label for="ck1">${order.userAccount}</label></td>
    					<td><label for="ck1">${order.userName}</label></td>
    					<td><label for="ck1">${order.amount}</label></td>
    					<td><label for="ck1">${order.createDate}</label></td>
    				</tr>
    			</c:forEach>
	    	</table>
			<div class="form-group ">
			    <div class="text-center">
			      <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="匹配">&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
			    </div>
			</div>
		</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	
	var remarkVal;
	$("#submitAdd").click(function(){
	  if($("input[name='ids']:checked").length>0){
	  		if(!amountCheck()){
	  			layer.alert("累计卖出金额与买入金额不对等，请重新选择！");
	  		}else{
	  			//输入备注
				layer.prompt({
				  formType: 2,
				  value: '',
				  title: '请输入备注',
				  
				}, function(value, index, elem){
				  $("#remarkVal").val(value); //得到value
				  layer.close(index);
				  matchSub(value);
				});	
	  		}

	  	}else{
	  		layer.alert("请至少选择一条记录");
	  	}
    });
	
	$("#back").click(function (){
  		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index);
  	});
  	
  	function amountCheck(){
  		var buyAmount = $("#amount").val();
  		var saleAmount = 0.0;
  		$("input[name='ids']:checked").each(function(index,element){
  			saleAmount +=Number($(this).attr("AMOUNTVALUE"));
  		});
  		return buyAmount == saleAmount;
  	}
  	function matchSub(remark){
 		
  		$("#matchForm").ajaxSubmit({
  			type: 'post',
             dataType: 'json',
             contentType: "application/x-www-form-urlencoded; charset=utf-8",
             url: "${domain}/order/matchBuyOrder.action?remarkVal="+remark,
             success: function (data, textStatus, jqXHR) {
                 if (data.success) {
                     layer.alert('匹配成功!',{icon:1},function(index){
                     	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.refashOrder();
						parent.layer.close(index);
                     });
                 }else {
                 	if(data.errorCode=="01"){
                 		layer.alert('匹配失败,没有对等金额的卖家!',{icon:2});
                 	}else{
                 		layer.alert('匹配失败!',{icon:2});
                 	}
                 }
             }
	});
  	}
</script>
  </body>
</html>

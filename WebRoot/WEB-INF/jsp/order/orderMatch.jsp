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
    
    <title>交易匹配</title>
    <%@include file="../../../common/common-css.jsp" %>
	<%@include file="../../../common/common-js.jsp" %>
  </head>
  
  <body>
  <%@include file="../../../common/left-menum.jsp" %>
    <div class="right-content orderinfo">
    <br/><br/>
		<form class="form-inline" action="orderQuery.action" id="orderQueryFrom">
		<div class=" row">
			<div class="col-md-12">
			<input type="checkbox" value="0" id="buyCheckbox" name="queryType" class="i-checks" <c:if test="${queryType=='0'||queryType=='0,1'}">checked</c:if>><strong>买入</strong>
			<input type="checkbox" value="1" id="saleCheckbox" name="queryType" class="i-checks" <c:if test="${queryType=='1'||queryType=='0,1'}">checked</c:if>><strong>卖出</strong>
			<input type="text" class="form-control" id="userAccount" name="userAccount" value="${userAccount}" placeholder="用户账号">
			
		    <input type="text" id="createTimeStart" name="createTimeStart" value="${createTimeStart }" placeholder="开始申请日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})">
		    -
		    <input type="text" id="createTimeEnd" name="createTimeEnd" value="${createTimeEnd }" placeholder="结束申请日期" class="form-control Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeStart\')}'})">
		    <input type="text" class="form-control" id="remain" name="remain" value="${remain}" placeholder="剩余金额">
		    <button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
		    <button type="button" class="btn btn-primary"  id="allBtn">全&nbsp;部</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="matchBtn">匹&nbsp;配</button>&nbsp;&nbsp;
		  	<button type="button" class="btn btn-primary" id="delBtn">删&nbsp;除</button>&nbsp;&nbsp;
			</div>
		</div>
		
		<br>
    <div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">买卖记录</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-6">
					<table class="table table-striped  table-hover">
		    			<thead>
			    			<tr>
			    				<td><input type="checkbox" name="buySelectAll" id="buySelectAll"></td>
			    				<td>会员账户</td>
			    				<td>会员编号</td>
			    				<td>买入金额</td>
			    				<td>未匹配金额</td>
			    				<td>申请日期</td>
			    			</tr>
		    			</thead>
		    			<c:forEach var="buyOrder" items="${buyOrderList}">
		    				<tr>
		    					<td><input type="checkbox" id="ck1" name="buyIds"  value="${buyOrder.id }" AMOUNTVALUE="${buyOrder.remain}" onclick="sumBuyAmount(${buyOrder.remain},this)"></td>
		    					<td>${buyOrder.userAccount}</td>
		    					<td>${buyOrder.userCode}</td>
		    					<td>${buyOrder.amount}</td>
		    					<td>${buyOrder.remain}</td>
		    					<td>${buyOrder.createDate}</td>
		    				</tr>
		    				
		    			</c:forEach>
		    			<tr>
		    					<td colspan="6"><div class="text-center"><span>已选择买入总金额：</span><span id="buySelectAmount" style="font-weight:bold">0.0</span></div></td>
		    				 </tr>
			    	</table>
				</div>
				<div class="col-md-6">
					<table class="table table-striped  table-hover">
		    			<thead>
			    			<tr>
			    				<td><input type="checkbox" name="saleSelectAll" id="saleSelectAll"></td>
			    				<td>会员账户</td>
			    				<td>会员编号</td>
			    				<td>卖出金额</td>
			    				<td>未匹配金额</td>
			    				<td>申请日期</td>
			    			</tr>
		    			</thead>
		    			<c:forEach var="saleOrder" items="${saleOrderList}">
		    				<tr>
		    					<td><input type="checkbox" id="ck2" name="saleIds"  value="${saleOrder.id }" AMOUNTVALUE="${saleOrder.remain}" onclick="sumSaleAmount(${saleOrder.remain},this)"></td>
		    					<td>${saleOrder.userAccount}</td>
		    					<td>${saleOrder.userCode}</td>
		    					<td>${saleOrder.amount}</td>
		    					<td>${saleOrder.remain}</td>
		    					<td>${saleOrder.createDate}</td>
		    				</tr>
		    			</c:forEach>
		    			<tr>
	    					<td colspan="6"><div class="text-center"><span>已选择卖出总金额：</span><span id="saleSelectAmount" style="font-weight:bold">0.0</span></div></td>
	    				 </tr>
			    	</table>
				</div>
			</div>
			
			<div id="pageTool"></div>
			
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">
 var currentUrl=window.location.href;
	$(function(){
		$('#manage-transactions').trigger('click');
		$("#transaction-match").addClass("active");
		var page = new Paging();
		page.init({			
				target: $('#pageTool'), pagesize: 20, count: parseInt("${count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count){
				window.location.href="${domain}/order/orderQuery.action?page="+(pagecount-1)+"&&rows="+size+"&queryType="+queryType+"&userAccount="+
				$("#userAccount").val()+"&createTimeStart="+$("#createTimeStart").val()+"&createTimeEnd="+$("#createTimeEnd").val()+"&remain="+$("#remain").val();
			}
		});	
	}); 
	$("#allBtn").click(function() {
		$("#buyCheckbox, #saleCheckbox").iCheck('uncheck');
		$("#queryType").val("");
		$("#userAccount").val("");
		$("#createTimeStart").val("");
		$("#createTimeEnd").val("");
		$("#remain").val("");
		$("#orderQueryFrom").submit();
	});
	$("#buySelectAll").click(function(){
		var isChecked = $(this).prop("checked");
	    $("input[name='buyIds']").prop("checked", isChecked);	
		$("#buySelectAmount").text(sumAmount("buyIds"));
	});
	
	$("#saleSelectAll").click(function(){
		var isChecked = $(this).prop("checked");
	    $("input[name='saleIds']").prop("checked", isChecked);
		$("#saleSelectAmount").text(sumAmount("saleIds"));
	});
	$("#buyCheckbox").click(function(){
		var isChecked = $(this).prop("checked");
	    $("input[name='saleCheckbox']").prop("checked", !isChecked);
	});
	$("#saleCheckbox").click(function(){
		var isChecked = $(this).prop("checked");
	    $("input[name='buyCheckbox']").prop("checked", !isChecked);
	});
	
	function sumAmount(idName){
		var count = 0.0;
  		$("input[name='"+idName+"']:checked").each(function(index,element){
  			count +=Number($(this).attr("AMOUNTVALUE"));
  		});
  		
  		return count;
	}
	
	function sumBuyAmount(amount,buyObj){
		var buyAmount = $("#buySelectAmount").text();
		if($(buyObj).is(":checked")){
			$("#buySelectAmount").text(Number(buyAmount)+amount);
		}else{
			$("#buySelectAmount").text(Number(buyAmount)-amount);
		}
	}
	function sumSaleAmount(amount,saleObj){
		var saleAmount = $("#saleSelectAmount").text();
		if($(saleObj).is(":checked")){
			$("#saleSelectAmount").text(Number(saleAmount)+amount);
		}else{
			$("#saleSelectAmount").text(Number(saleAmount)-amount);
		}
	}


	$("#delBtn").click(function(){
		if($("input[name$='Ids']:checked").length==0){
			layer.alert('请至少选择一条记录!', { icon : 2 });
			return;
		}
		layer.confirm('确定删除？', {
				icon : 3,
				title : '提示'
			}, function(index) {
				$("#orderQueryFrom").ajaxSubmit({
		 			type: 'post',
		              dataType: 'json',
		              contentType: "application/x-www-form-urlencoded; charset=utf-8",
		              url: "${domain}/order/orderDelete.action",
		              success: function (data, textStatus, jqXHR) {
		                  if (data.success) {
		                      layer.alert('删除成功!',{icon:1},function(index){
		                      	window.location.href=currentUrl;
		                      });
		                  } else {
		                  	if(data.errorCode=='01'){
		                  		layer.alert(data.errorMsg,{icon:2});
		                  	}else{
		                  		layer.alert('删除失败!',{icon:2});
		                  	}
		                      
		                  }
		              }
				});
			
			});
	});
	
	$("#matchBtn").click(function(){
		if($("input[name='buyIds']:checked").length==0){
			layer.alert('请至少选择一条买入记录!', { icon : 2 });
			return;
		}
		if($("input[name='saleIds']:checked").length==0){
			layer.alert('请至少选择一条卖出记录!', { icon : 2 });
			return ;
		}
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
		
		
	});
	
	function matchSub(remark){
		$("#orderQueryFrom").ajaxSubmit({
 			type: 'post',
              dataType: 'json',
              contentType: "application/x-www-form-urlencoded; charset=utf-8",
              url: "${domain}/order/orderMatch.action?remarkVal="+remark,
              success: function (data, textStatus, jqXHR) {
                  if (data.success) {
                      layer.alert('匹配成功!',{icon:1},function(index){
                      	window.location.href=currentUrl;
                      });
                  } else {
                      layer.alert('匹配失败!',{icon:2});
                  }
              }
		});
	}
	$(document).ready(function(){
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
</script>
  </body>
</html>

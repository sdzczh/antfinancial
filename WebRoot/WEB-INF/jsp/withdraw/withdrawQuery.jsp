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

    <title>提现订单查询</title>
    <%@include file="../../../common/common-css.jsp" %>
    <%@include file="../../../common/common-js.jsp" %>
</head>

<body>
<%@include file="../../../common/left-menum.jsp" %>
<div class="right-content noticeinfo">
    <br/><br/>
    <form class="form-inline" action="${domain}/withdraw/getWithdrawList.action" id="noticeFrom">
        <div class=" row">
            <div class="col-md-12">
                <input type="text" class="form-control" id="userAccount" name="userAccount" value="${userAccount}" placeholder="用户账号">
                <select class="form-control" id="state" name="state">
                    <option value="">--请选择订单状态--</option>
                    <option  value="0" <c:if test="${state=='0'}">selected="selected"</c:if>>未确认</option>
                    <option value="1" <c:if test="${state=='1'}">selected="selected"</c:if>>已提现</option>
                    <option value="1" <c:if test="${state=='2'}">selected="selected"</c:if>>已取消</option>
                </select>
                <button type="submit" class="btn btn-primary">搜&nbsp;索</button>&nbsp;&nbsp;
                <button type="button" class="btn btn-primary" id="resetBtn">全&nbsp;部</button>&nbsp;&nbsp;
            </div>
        </div>
    </form>
    <br>
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">提现列表</h3>
        </div>
        <div class="panel-body">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>手机号</td>
                    <td>提现账户类型</td>
                    <td>收款地址</td>
                    <td>备注信息</td>
                    <td>金额</td>
                    <td>订单状态</td>
                    <td>操作</td>
                </tr>
                </thead>
                <c:forEach var="withdraw" items="${data.withdrawList}">
                    <tr>
                        <td>${withdraw.id}</td>
                        <td>${withdraw.userAccount}</td>
                        <td>
                            <c:if test="${withdraw.account_type==0}">
                               J钱包
                            </c:if>
                            <c:if test="${withdraw.account_type==1}">
                                D钱包
                            </c:if>
                            <c:if test="${withdraw.account_type==2}">
                                Z钱包
                            </c:if>
                        </td>
                        <td>${withdraw.address}</td>
                        <td>${withdraw.remark}</td>
                        <td>${withdraw.amount}</td>
                        <td>
                            <c:if test="${withdraw.state==0}">
                             未确认
                            </c:if>
                            <c:if test="${withdraw.state==1}">
                              已确认
                            </c:if>
                            <c:if test="${withdraw.state==2}">
                              已取消
                            </c:if>
                        </td>
                        <td>
							  <a onclick="deleteNotice(${withdraw.id})">删除</a>
                            <c:if test="${withdraw.state==0}">
							  <a onclick="updateNotice(${withdraw.id}, 1)">确认</a>
							  <a onclick="updateNotice(${withdraw.id}, 2)">取消</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="pageTool"></div><div class="text-center">共${data.count}条</div>

        </div>
    </div>
</div>
<script type="text/javascript">
    var currentUrl=window.location.href;
    $(function(){
        $('#manage-recharge').trigger('click');
        $("#withdraw-uesr").addClass("active");
        var page = new Paging();
        page.init({
            target: $('#pageTool'), pagesize: 10, count: parseInt("${data.count}"), current:parseInt("${page}")==0?1:(parseInt("${page}")+1),callback: function (pagecount, size, count){
                window.location.href="${domain}/withdraw/getWithdrawList.action?page="+(pagecount-1)+"&&rows="+size+"&userAccount="+$("#userAccount").val()+"&state="+$("#state").val();
            }
        });
    });
    $("#resetBtn").click(function() {
        $("#userAccount").val("");
        $("#state").val("");
        $("#noticeFrom").submit();
    });

    function deleteNotice(id){
        layer.confirm('确定删除？',{
            icon: 3, title:'提示'
        },function(index){
            $.ajax({
                type: "POST",
                url: "${domain}/withdraw/withdrawDelete.action",
                data: "id=" + id,
                dataType: "json",
                async: false,
                success: function (data, textStatus, jqXHR) {
                    alert('删除成功!');
                }
            });
        });
    }

    function updateNotice(id, state) {
        layer.confirm('确定修改订单状态？',{
            icon: 3, title:'提示'
        },function(index){
            $.post("${domain}/withdraw/update.action",{
                id : id, state : state
            },function (result) {
                alert('操作成功!');
            })
        })
    }
</script>
</body>
</html>

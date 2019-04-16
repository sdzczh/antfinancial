<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<aside class="left-menum"> 
	<section class="sidebar" id="sidebar">
		<ul class="sidebar-menu">
			<div class="header">蚂蚁理财管理中心</div>
			<div style="color: red;text-align: center;">
				<a href="${domain}/manager/user/loginOut.action">退出</a>
			</div>
			<li class="treeview">
				<a href="#" id="manage-user"> 
					<i class="fa fa-user"></i>
					会员管理
					<i class="fa fa-angle-left pull-right"></i> 
				</a>
				<ul class="treeview-menu">
					<li>
						<a href="${domain}/manager/user/getUserList.action" id="user-list">
							<i class="fa fa-circle-o"></i> 
							会员列表
						</a>
					</li>
					<%-- <li>
						<a href="${domain}/manager/user/getDeletedUserList.action" id="user-del">
							<i class="fa fa-circle-o"></i> 
							删除的会员
						</a>
					</li> --%>
				</ul></li>
			<li class="treeview">
				<a href="#" id="manage-transactions"> 
					<i class="fa fa-tasks"></i>
					<span>交易管理</span> 
					<i class="fa fa-angle-left pull-right"></i> 
				</a>
				<ul class="treeview-menu" style="display: none;">
					<li>
						<a href="${domain}/transaction/transactionQuery.action" id="transaction-history">
							<i class="fa fa-circle-o"></i>
							交易记录
						</a>
					</li>
					<li>
						<a href="${domain}/order/orderQuery.action" id="transaction-match">
							<i class="fa fa-circle-o"></i>
							匹配买卖
						</a>
					</li>
					<li>
						<a href="${domain}/capitalFlow/capitalFlowQuery.action" id="capital-Flow">
							<i class="fa fa-circle-o"></i>
							交易流水
						</a>
					</li>
				</ul>
			</li>
			<li class="treeview">
				<a href="#" id="manage-system"> 
					<i class="fa fa-desktop"></i>
					<span>系统管理</span> 
					<i class="fa fa-angle-left pull-right"></i> 
				</a>
				<ul class="treeview-menu" style="display: none;">
					<li>
						<a href="${domain}/system/info.action" id="system-info">
							<i class="fa fa-circle-o"></i>
							系统信息
						</a>
					</li>
					<li>
						<a href="${domain}/systemParam/paramList.action" id="system-params">
							<i class="fa fa-circle-o"></i>
							参数设置
						</a>
					</li>
					<li>
						<a href="${domain}/notice/notice.action" id="system-notice">
							<i class="fa fa-circle-o"></i>
							系统公告
						</a>
					</li>
					<li>
						<a href="${domain}/system/getLoginHistoryById.action" id="system-login-history">
							<i class="fa fa-circle-o"></i>
							登录记录
						</a>
					</li>
                    <li>
                        <a href="${domain}/file/info.action" id="system-rechange">
                            <i class="fa fa-circle-o"></i>
                            收款二维码上传
                        </a>
                    </li>
				</ul>
			</li>
			<li class="treeview">
				<a href="#" id="manage-recharge"> 
					<i class="fa fa-shield "></i>
					充值管理
					<i class="fa fa-angle-left pull-right"></i> 
				</a>
				<ul class="treeview-menu" style="display: none;">
					<li>
						<a href="${domain}/manager/user/getUserRecharge.action" id="recharge-uesr">
							<i class="fa fa-circle-o"></i> 
							人工充值
						</a>
					</li>
				</ul>
			</li>
		</ul>
	</section> 
</aside>
<script>
$(function(){
	$.sidebarMenu($('#sidebar'))
})
</script>
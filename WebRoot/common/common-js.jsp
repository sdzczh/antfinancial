<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript" src="${domain}/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/jquery-form/jquery.form.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/sidebar/js/sidebar-menu.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/backstretch/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/base64/jquery.base64.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/md5/md5.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/pading/js/paging.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${domain}/js/orm.js"></script>
<script type="text/javascript" src="${domain}/js/validator.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/parsley/parsley.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/parsley/zh_cn.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/parsley/validators.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${domain}/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript">
/*关闭弹出框口*/
function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>
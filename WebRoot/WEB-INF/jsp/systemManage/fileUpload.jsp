<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="domain" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>蚂蚁理财管理中心</title>

<%@include file="../../../common/common-css.jsp" %>
<%@include file="../../../common/common-js.jsp" %>
</head>
<body>
<%@include file="../../../common/left-menum.jsp" %>
<%--
<%@include file="common/top.jsp" %>
--%>
<div class="right-content sysinfo">
    <div class="form-group">
            <label class="col-sm-2 control-label">收款二维码:</label>
            <div class="col-sm-10">
            <input type="hidden" name="img"  id="photoUrl"/>
            <input type="file" name="logoFile" id="logoFile" onchange="setImg(this)">
            <span><img id="photourlShow" src="" width="300" height="197"/></span>
            </div>
    </div>
</div>
<script type="text/javascript">
	$(function(){
		$('#manage-system').trigger('click');
		$("#system-info").addClass("active");
	});
	
	setInterval("flushTime()",50);
	function flushTime(){
		var myDate = new Date();  
		var year = myDate.getFullYear();
		var mon = myDate.getMonth();
		var day = myDate.getDate();
		var h = myDate.getHours();
		var m = myDate.getMinutes();
		var s = myDate.getSeconds();
	  $("#serverDate").text(year+"-"+(mon+1)+"-"+day+" "+h+":"+m+":"+s);
	  }


    function setImg(obj){
        var f=$(obj).val();
        console.log(obj);
        if(f == null || f ==undefined || f == ''){
            return false;
        }
        if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
        {
            alert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
            $(obj).val('');
            return false;
        }
        var data = new FormData();
        console.log(data);
        $.each($(obj)[0].files,function(i,file){
            data.append('file', file);
        });
        console.log(data);
        $.ajax({
            type: "POST",
            url: "${domain}/file/upload.action",
            data: data,
            cache: false,
            contentType: false,    //不可缺
            processData: false,    //不可缺
            dataType:"json",
            success: function(ret) {
                console.log(ret);
                if(ret.code== 0){
                    $("#photoUrl").val(ret.result.url);//将地址存储好
                    $("#photourlShow").attr("src",ret.result.url);//显示图片
                    alert(ret.message);
                }else{
                    alertError(ret.message);
                    $("#url").val("");
                    $(obj).val('');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("上传失败，请检查网络后重试");
            }
        });
    }
</script>
</body>
</html>
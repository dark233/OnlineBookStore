<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发货</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/qikoo.js'/>"></script>	
	<script type="text/javascript">
		function checkForm() {
			if(!$("#expressOrder").val()) {
				qikoo.dialog.alert("快速单号不能为空！");
				return false;
			}else{
				var reg = /^[1-9]{1}[0-9\-@]{11}$/;
				if(!reg.test($("#expressOrder").val())){
					qikoo.dialog.alert("快速单号格式为12位数字且不能以0开头！");
					return false;
				}
			}
			if(!$("#dictid").val()) {
				qikoo.dialog.alert("快递公司不能为空！");
				return false;
			}
			return true;
		}
	</script>
<style type="text/css">
	body {background: rgb(254,238,189);}
</style>
  </head>
  
  <body>
    <h3>发货</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${msg }</p>
    <p style="font-weight: 900;">订单编号：${tradeid}</p>
    <form action="<c:url value='/backstage/HandleServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="methodName" value='ship'/>
    	<input type="hidden" name="tradeid" value='${tradeid }'/>
    	<input type="hidden" name="handleid" value='${handleid }'/>
    	快递单号：<input type="text" name="expressOrder" id="expressOrder"/><br/>
    	快递公司：<select name="dictid" id="dictid">
    		<option value="">===选择快递公司===</option>
<c:forEach items="${expressCompList}" var="expressComp">
    		<option value="${expressComp.dictid }"  >${expressComp.val }</option>
</c:forEach>
    	</select><br/>
    	<input type="submit" value="发货"/>
    	<input type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	 <link rel="stylesheet" type="text/css" href="<c:url value = '/adminjsps/login.css'/>" />
	
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/loginCookie.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/adminjsps/login.js'/>"></script>

<style type="text/css">
#bg {
	background-size: 100%;
	height: 100%;
	width: 100%;
	background-image: url("${pageContext.request.contextPath}/img/tu.jpg");
	background-repeat: no-repeat;
}
</style>

</head>

<body id="bg" style="overflow:hidden;">
	<div class="div1">
		<br /> <br />
<!-- 		<div style="border:1;border-color: red;margin: auto;width: 400px;height: 400px;">
 -->		
		<center><h1>后台用户登录</h1></center>
		<form id = "loginform" action="${pageContext.request.contextPath}/backstage/BackStageUserServlet" method="post">
			<input type="hidden" name="methodName" value="login"/>
			<table>
			<tr>
				<td align="right">用户名:</td>
				<td><input class="input" type="text" name="username" id="username" placeholder="输入用户名或手机号码"/></td>
				<td><label id="usernameError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">密码:</td>
				<td><input class="input" type="password" name="password" id="password"/></td>
				<td><label id="passwordError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">验证码:</td>
				<td>
				  <input class="input" type="text" name="verifyCode" id="verifyCode"/>
				</td>
				<td><label id="verifyCodeError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
				  <img id="vCode" src="<c:url value='/GetImage'/>" border="1"/>
		    	  <a href="javascript:_change();">看不清，换一张</a>
				</td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><input type="checkbox" name="isRemeber"/>Remeber me</td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><input id="login" type="button" value="立即登录"/></td>
			</tr>
		</table>
			<input class = "input" type="hidden" name="finalvalidate"/>
		</form>
		<!-- </div> -->
	</div>

</body>
</html>

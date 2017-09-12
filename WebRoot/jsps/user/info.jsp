<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>个人信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/pwd.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
	<script src="<c:url value='/jsps/js/user/info.js'/>"></script>
	
	<style type="text/css">
		.info-head-img {
			width: 50px;
			height: 50px;
			border-radius: 50%;
			float:left;
			border: 1px solid #404040;
		}
	</style>
  </head>
  
  <body>
  
    <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
  
    <div class="div0">
    	<span>用户信息</span>
    </div>
	<div class="div1">
		<table>
			<tr>
				<td align="right">头像:</td>
				<td></td>
				<td  align="right"><img class="info-head-img" src="<c:url value='/${requestScope.user.headImgUrl }'/>" ></td>
			</tr>
			<tr>
				<td align="right">用户名:</td>
				<td><label>${requestScope.user.username }</label></td>
				<td><a class="updateUsername" id = "${requestScope.user.userid }UpdateUsername" >修改用户名</a></td>
			</tr>
			<tr>
				<td colspan="2"></td>
				<td ><a href = "<c:url value = '/jsps/user/pwd.jsp'/>">修改密码</a></td>
			</tr>
			<tr>
				<td align="right">等级:</td>
				<td><label>
					<c:choose>
						<c:when test="${requestScope.user.level eq 1 }">普通用户</c:when>
						<c:when test="${requestScope.user.level eq 2 }">白银用户</c:when>
						<c:when test="${requestScope.user.level eq 3 }">黄金用户</c:when>
					</c:choose>
				</label></td>
			</tr>
			<tr>
				<td align="right">手机号码:</td>
				<td><label>${requestScope.user.tel }</label></td>
				<td><a href = "<c:url value = ''/>">修改绑定手机号码</a></td>
			</tr>
			<tr>
				<td align="right">邮箱:</td>
				<td><label>${requestScope.user.email }</label></td>
				<td><a href = "<c:url value = ''/>">修改绑定邮箱</a></td>
			</tr>
			<tr>
				<td align="right">余额:</td>
				<td><label>${requestScope.user.balance }</label></td>
				<td><a href = "<c:url value = '/jsps/user/recharge.jsp'/>">充值</a></td>
			</tr>
			<tr>
				<td align="right">注册日期:</td>
				<td><label>${requestScope.user.registDate }</label></td>
			</tr>
			<tr>
				<td align=right><a href = "<c:url value = '/UserServlet?methodName=shippingAddrPre'/>">收货地址管理</a></td>
			</tr>
		</table>
	</div>
  </body>
</html>

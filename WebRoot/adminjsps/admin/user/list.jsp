<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title> 前台用户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/user/list.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/qikoo.js'/>"></script>
  </head>
  
  <body>
<p class="pLink">
  <a href="<c:url value='/backstage/UserServlet?methodName=findPageWithIsBan'/>">全部用户</a>(${requestScope.banNum+requestScope.noBanNum})  | 
  <a href="<c:url value='/backstage/UserServlet?methodName=findPageWithIsBan&isBan=1'/>">封禁用户</a>(${requestScope.banNum})  | 
  <a href="<c:url value='/backstage/UserServlet?methodName=findPageWithIsBan&isBan=2'/>">未封禁用户</a>(${requestScope.noBanNum})  | 
  
</p>
<div class="divMain">
	<table align="center" border="1" width="100%" cellpadding="0" cellspacing="0" style="margin-top: 7px">
		<tr class = "title">
			<td>用户编号</td>
			<td>用户名</td>
			<td>余额</td>
			<td>注册时间</td>
			<td>手机号码</td>
			<td>email</td>
			<td>用户等级</td>
			<td>操作</td>
		</tr>
	
<c:forEach items="${pages.list}" var="user">	

		<tr class = "tt" style="padding-top: 10px; padding-bottom: 10px;">
			<td>${user.userid }</td>
			<td>${user.username }</td>
			<td>${user.balance }</td>
			<td>${user.registDate }</td>
			<td>${user.tel }</td>
			<td>${user.email }</td>
			<td>${user.level }</td>
			<td>
			<c:choose>
				<c:when test="${user.isBan eq 1 }">
				<a href = "<c:url value='/backstage/UserServlet?methodName=banUser&userid=${user.userid }&isBan=2'/>">解禁</a>
				</c:when>		
				<c:when test="${user.isBan eq 2 }">
				<a href = "<c:url value='/backstage/UserServlet?methodName=banUser&userid=${user.userid }&isBan=1'/>">封禁</a>
				</c:when>		
			</c:choose>
			</td>
		</tr>
</c:forEach>

	</table>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	.wholeView {
		background: #4169E1;
		margin:0px;
		color: #ffffff;
	}
	.topa {
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
	} 
	.topa:hover {
		text-decoration: underline;
		color: #ffffff;
		font-weight: 900;
	}
	.webName {
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
	}

	.logoDiv{
		margin-bottom: 20px;
	}
	.head-img {
		width: 80px;
		height: 80px;
		border-radius: 50%;
		float:left;
	}
</style>
  </head>
  
  <body>
<div class="wholeView">
<div class="logoDiv">
<a href="<c:url value='/enter.jsp'/>" target="_top"><img src="<c:url value='/images/mylogo.png'/>" border="0" style="width: 120px;height: 50px"/></a>
</div>
<%-- <a id="logo" href="<c:url value='/enter.jsp'/>" target="_top"></a> --%>
<%-- <h1 style="text-align: center;"><a class = "webName" href="<c:url value='/enter.jsp'/>" target="_top">NETBOOKSTORE</a></h1> --%>
<div style="font-size: 10pt; line-height: 10px;">

<%-- 根据用户是否登录，显示不同的链接 --%>
<c:choose>
	<c:when test="${empty sessionScope.user }">
		  <a class = "topa" href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">登录</a> |&nbsp; 
		  <a class = "topa" href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">注册</a>	
	</c:when>
	<c:otherwise>
<%-- 			<a class = "" href = "<c:url value = '/UserServlet?methodName=userInfo'/>" target = "_top">
			<img class="head-img" src="<c:url value='/${sessionScope.user.headImgUrl }'/>" ></a> --%>
		      用户名：<a class = "topa" href = "<c:url value = '/UserServlet?methodName=info'/>" target = "_top">${sessionScope.user.username }</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a class = "topa" href="<c:url value='/ShoppingCartServlet?methodName=findShoppingCartWithUserId'/>" target="_top">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a class = "topa" href="<c:url value='/TradeServlet?methodName=findTradePage'/>" target="_top">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<%-- 		  <a class = "topa" href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
 --%>		  <a class = "topa" href="<c:url value='/UserServlet?methodName=exit'/>" target="_parent">退出</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a class = "topa" href="http://120.24.240.47：8080/BookStore" target="_top">联系我们</a>	
	</c:otherwise>
</c:choose>


</div>
</div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>湘科院网上书店</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="common page1">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
<style>
#div4{height: 200px;width: 100%;background-color: aqua}
#div1{float: left}
#div2{float: right}
#div3{margin-bottom: 0;}
font{font-family: 华文琥珀;font-size: 50px;color: ff0033}
</style>
  </head>
  
  <body>
  <div id = "div4"> 
  <div id = "div1">
   <a href = "${pageContext.request.contextPath }/firstPage.jsp">首页|</a>
  <c:if test="${sessionScope.user==null }">
  <a href = "${pageContext.request.contextPath }/login.jsp">登录|</a>
  </c:if>
  <a href = "${pageContext.request.contextPath }/regist.jsp?methodName=findList&pageNo=${bookpage.pageNo }&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}">注册</a>
  <c:if test="${sessionScope.user!=null }">
  <a href = "${pageContext.request.contextPath}/userInfo.jsp" >${sessionScope.user.username }</a>
  <a href = "${pageContext.request.contextPath}/UserServlet?methodName=exit" >|退出|</a>
  <a href = "${pageContext.request.contextPath}/recharge.jsp">充值</a>
  
  </c:if>
  </div>
  <center>
 <!--  <marquee direction="right" behavior="alternate" scrollamount="10"><font>NETBOOKSTORE</font></marquee> -->
  </center>
  <div id = "div2">
  <a href = "${pageContext.request.contextPath }/BookServlet?methodName=findShoppingCartWithUserId">查看购物车|</a>
  <a href = "${pageContext.request.contextPath }/BookServlet?methodName=findTradeWithUserid">查看订单</a>
  </div>
  <br/>
  <div id = "div3">
  <a href="${pageContext.request.contextPath }/BookServlet?methodName=findList&pageNo=1">全部图书</a>
  </div>
  </div>
  </body>
</html>

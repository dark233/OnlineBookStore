<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
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
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/trade/list.css'/>" />
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
  <a href="<c:url value='/backstage/HandleServlet?methodName=findPageWithOperateAndStatus&operate=${requestScope.operate }'/>">全部</a>(${requestScope.handlingNum+requestScope.handledNum})  | 
  <a href="<c:url value='/backstage/HandleServlet?methodName=findPageWithOperateAndStatus&operate=${requestScope.operate }&status=2'/>">未处理</a>(${requestScope.handlingNum})  | 
  <a href="<c:url value='/backstage/HandleServlet?methodName=findPageWithOperateAndStatus&operate=${requestScope.operate }&status=1'/>">已处理</a>(${requestScope.handledNum})  | 
  
</p>
<div class="divMain">
<!-- 	<div class="title">
		<div style="margin-top:7px;">
			<span style="margin-left: 150px;margin-right: 280px;">商品信息</span>
			<span style="margin-left: 40px;margin-right: 100px;">金额</span>
			<span style="margin-left: 50px;margin-right: 53px;">订单状态</span>
			<span style="margin-left: 100px;">操作</span>
		</div>
	</div>
	<br/> -->
	<table align="center" border="1" width="100%" cellpadding="0" cellspacing="0" style="margin-top: 7px">
		<tr class = "title">
			<td>处理记录编号</td>
			<td>订单编号</td>
			<td>提交时间</td>
			<td>订单金额</td>
			<td>操作</td>
		</tr>
	
<c:forEach items="${pages.list}" var="handle">	

		<tr class = "tt" style="padding-top: 10px; padding-bottom: 10px;">
			<td>${handle.handleid }</td>
			<td>${handle.tradeid }</td>
			<td>${handle.createTime }</td>
			<td>${handle.trade.tradeMoney }</td>
			<td>
			<c:choose>
				<c:when test="${handle.operate eq 1 }">
					<c:choose>
						<c:when test="${handle.status_ eq 1 }"><a href = "<c:url value='/backstage/HandleServlet?methodName=load&operate=1&tradeid=${handle.tradeid }'/>">查看</a></c:when>
						<c:when test="${handle.status_ eq 2 }"><a href = "<c:url value='/backstage/HandleServlet?methodName=shipPre&tradeid=${handle.tradeid }&handleid=${handle.handleid }'/>">发货</a></c:when>
					</c:choose>
				</c:when>		
				<c:when test="${handle.operate eq 2 }">
					<a href = "<c:url value='/backstage/HandleServlet?methodName=load&operate=2&tradeid=${handle.tradeid }'/>">查看</a>
					<c:choose>
						<c:when test="${handle.status_ eq 2 }">
						<a href = "<c:url value = '/backstage/HandleServlet?methodName=agreeRefund&tradeid=${handle.tradeid }&handleid=${handle.handleid }'/>">同意</a>&nbsp;
						<a href="<c:url value='/backstage/HandleServlet?methodName=refuseRefundPre&tradeid=${handle.tradeid }&handleid=${handle.handleid }'/>">拒绝</a>
						</c:when>
					</c:choose>
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

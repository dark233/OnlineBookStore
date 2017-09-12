<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>收货地址管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/shippingAddr.css'/>">
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript">
		var linkUrl = "${pageContext.request.contextPath}/enter.jsp";

	</script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
	<script src="<c:url value='/jsps/js/user/shippingAddr.js'/>"></script>
	
  </head>
  
<body>
  <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
	
	
	<div class="divOrder">
		<span>收货地址</span>
	</div>
	<div class="divContent">
		<div class="div2">
			<dl>
				<dt>收货地址</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">收货人</th>
							<th class="tt" align="left">所在地区</th>
							<th class="tt" align="left">详细地址</th>
							<th class="tt" align="left">电话</th>
							<th class="tt" align="left">操作</th>
						</tr>

<c:forEach items="${shippingAddrList }" var="shippingAddr">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td">${shippingAddr.cnee}</td>
							<td class="td">${shippingAddr.province}&nbsp;${shippingAddr.city}&nbsp;${shippingAddr.district_county}</td>
							<td class="td">${shippingAddr.detailAddr}</td>
							<td class="td">${shippingAddr.tel}</td>
							<td class="td"><a href="#">修改</a>|<a href="#">删除</a></td>
							<td class="td">
							<c:choose>
								<c:when test="${shippingAddr.isDefu eq 1 }"><font>默认地址</font></c:when>
								<c:otherwise><a href="#">设为默认地址</a></c:otherwise>
							</c:choose>
							</td>
						</tr>
</c:forEach>
					</table>
				</dd>
			</dl>
		</div>
		
		<div class="div2">
			<dl>
				<dt>收货地址</dt>
				<dd>
					<form action="<c:url value='/UserServlet'/>" method="post" id="form" target="_top">
					<c:choose>
						<c:when test="${btn eq 1 }"><input type="hidden" name = "methodName" value="addShippingAddr"/></c:when>
						<c:when test="${btn eq 2 }">
						<input type="hidden" name = "methodName" value="updateShippingAddr"/>
						<input type="hidden" name = "shipAddrid" value="${shippingAddr.shipAddrid }"/>
						</c:when>
					</c:choose>
					
					<table>
						<tr>
							<td>所在地区：</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<textarea name = "detailAddr" rows="6" cols="100"></textarea>
					
					</form>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px ;">
			<a id = "confirm" href = "javascript:$('#form').submit();">保存</a>
		</div>
	</div>
</body>
</html>


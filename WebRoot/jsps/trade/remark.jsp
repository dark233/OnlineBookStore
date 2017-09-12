<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>评价</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/trade/remark.css'/>">
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript">
		var linkUrl = "${pageContext.request.contextPath}/enter.jsp";

	</script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
	<script src="<c:url value='/jsps/js/trade/remark.js'/>"></script>
	
  </head>
  
<body>
  <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
	
	<div class="divOrder">
		<span>订单号：${trade.tradeid }</span>
	</div>
	<div class="divContent">
		<div class="div2">
			<dl>
				<dt>评价清单</dt>
				<dd>
					<form action="<c:url value='/TradeServlet'/>" method="post" id="form1" target="_top">
					<input type="hidden" name = "methodName" value="remark"/>
					<input type="hidden" name = "tradeid" value="${trade.tradeid }"/>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">图书名称</th>
							<th class="tt" align="left">评价</th>
						</tr>

<c:forEach items="${trade.tradeItems }" var="tradeItem">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/${tradeItem.book.imgCacheUrl }'/>"/>
								  <a class = "linkTitle" href="<c:url value='/BookServlet?methodName=findOneBook&bookid=${tradeItem.book.id}'/>">${tradeItem.book.title }</a>
								</div>
							</td>
							<td>
								<input type = "radio" name = "${tradeItem.itemid }Level" value = "1" checked="checked"/>好评
								<input type = "radio" name = "${tradeItem.itemid }Level" value = "2"/>中评
								<input type = "radio" name = "${tradeItem.itemid }Level" value = "3"/>差评<br/>
								<textarea name = "${tradeItem.itemid }Text" rows="5" cols="55" required="required"></textarea>
							</td>	
						</tr>
</c:forEach>


					</table>
					</form>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px 10px 550px;">
			<a class = "remarkBtn" href = "javascript:$('#form1').submit();">评价订单</a>
		</div>
	</div>
</body>
</html>


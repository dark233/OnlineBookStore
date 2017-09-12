<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/trade/desc.css'/>">
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript">
		var linkUrl = "${pageContext.request.contextPath}/enter.jsp";
		var tradeServletUrl = "${pageContext.request.contextPath}/TradeServlet";
		var tradeStatus = "${status}";
		var tradeid = "${trade.tradeid}";
		var payExpirtyTime = "${trade.payExpirtyTime}";
		var confirmReceiptExpirtyTime = "${trade.confirmReceiptExpirtyTime}";
		var sh;
	</script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
	<script src="<c:url value='/jsps/js/trade/desc.js'/>"></script>
  </head>
  
<body>
  <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
	
	<div class="divOrder">
		<span>订单号：${trade.tradeid }
<c:choose>
	<c:when test="${not empty trade.handle }">
		<c:choose>
			<c:when test="${trade.handle.refundRecords.status_ eq 1}">(退款中)</c:when>
			<c:when test="${trade.handle.refundRecords.status_ eq  2}">(退款被拒)</c:when>
		</c:choose>
	</c:when>
	<c:when test="${status eq 1 }">(等待付款)</c:when>
	<c:when test="${status eq 2 }">(准备发货)</c:when>
	<c:when test="${status eq 3 }">(等待确认)</c:when>
	<c:when test="${status eq 4  || status eq 8}">(交易成功)</c:when>
	<c:when test="${status eq 5 }">(已取消)</c:when>
	<c:when test="${status eq 7 }">(退款成功)</c:when>
</c:choose>	
		　　　下单时间：${trade.tradeTime }</span>

	</div>
	
	<div class="divOrder">
		<span id = "leftTime"></span>
	</div>
	
	<div class="divContent" >
		<c:if test="${status eq 3 || status eq 4 || status eq 8 }">
		<div class="div2">
			<dl>
				<dt>快递信息</dt>
				<dd>快递公司：${trade.expressComp }</dd>
				<dd>单号：${trade.expressOrder}</dd>
			</dl>
		</div>
		</c:if>
		<div class="div2">
			<dl>
				<dt>收货人信息</dt>
				<dd>${trade.shipAddr }&nbsp;&nbsp;${trade.cnee }&nbsp;&nbsp;${trade.tel }</dd>
			</dl>
		</div>
		<div class="div2">
			<dl>
				<dt>图书清单</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">图书名称</th>
							<th class="tt" align="left">单价</th>
							<th class="tt" align="left">数量</th>
							<th class="tt" align="left">小计</th>
						</tr>


<c:forEach items="${trade.tradeItems }" var="tradeItem">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/${tradeItem.book.imgCacheUrl }'/>"/>
								  <a class = "linkTitle" href="<c:url value='/BookServlet?methodName=findOneBook&bookid=${tradeItem.book.id}'/>">${tradeItem.book.title }</a>
								</div>
							</td>
							<td class="td" >
								<span>&yen;${tradeItem.price }</span>
							</td>
							<td class="td">
								<span>${tradeItem.quantity }</span>
							</td>
							<td class="td">
								<span>&yen;${tradeItem.price*tradeItem.quantity }</span>
							</td>			
						</tr>
</c:forEach>


					</table>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px 10px 550px;">
			<span style="font-weight: 900; font-size: 15px;">合计金额：</span>
			<span class="price_t">&yen;${trade.tradeMoney }</span><br/>
<c:if test="${status eq 1 }">
	<a href="<c:url value='/TradeServlet?methodName=paymentPre&tradeid=${trade.tradeid }'/>" class="pay"></a><br/>
    <a class = "cancelBtn" id="${trade.tradeid }Cancel">取消订单</a><br/>
</c:if>
<c:if test="${status eq 2 }">
	<a class = "refundBtn" href="<c:url value='/TradeServlet?methodName=refundPre&tradeid=${trade.tradeid}'/>">发起退款</a>
</c:if>
<c:if test="${status eq 3}">
	<a class="confirmBtn" id = "${trade.tradeid }Confirm" >确认收货</a><br/>
</c:if>	
<c:if test="${status eq 4 }">
	<a class = "remarkBtn" href="<c:url value='/TradeServlet?methodName=remarkPre&tradeid=${trade.tradeid}'/>">评价订单</a>
</c:if>
<c:if test="${status eq 6 }">
		<a class = "refundBtn" href="<c:url value='/TradeServlet?methodName=refundPre&tradeid=${trade.tradeid}'/>">发起退款</a>
	<a class = "cancelRefundBtn" id = "${trade.tradeid }CancelRefund">取消退款</a>
</c:if>
		</div>
	</div>
</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mfn" uri="http://www.taunton.com/jstl/myElFunction" %>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/trade/list.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript">
		var linkUrl = "${pageContext.request.contextPath}/enter.jsp";
		var tradeServletUrl = "${pageContext.request.contextPath}/TradeServlet";
	</script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/js/common.js'/>"></script>
	<script src="<c:url value='/jsps/js/trade/list.js'/>"></script>
	
  </head>
  
  <body>
  
    <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
  
<p class="pLink" >
  <a href="<c:url value='/TradeServlet?methodName=findTradePage'/>">全部订单</a>  | 
  <a href="<c:url value='/TradeServlet?methodName=findTradePage&status=1'/>">待付款</a>  | 
  <a href="<c:url value='/TradeServlet?methodName=findTradePage&status=2'/>">待发货</a>  | 
  <a href="<c:url value='/TradeServlet?methodName=findTradePage&status=3'/>">待收货</a>  | 
  <a href="<c:url value='/TradeServlet?methodName=findTradePage&status=4'/>">待评价</a>  | 
  <a href="<c:url value='/TradeServlet?methodName=findTradePage&status=5'/>">取消订单</a>  | 
  <a href="<c:url value='/TradeServlet?methodName=findTradePage&status=6'/>">退款</a>  | 
  
</p>

<div class="divMain">
	<div class="divTitle">
		<span style="margin-left: 150px;margin-right: 280px;">图书信息</span>
		<span style="margin-left: 40px;margin-right: 38px;">金额</span>
		<span style="margin-left: 50px;margin-right: 40px;">订单状态</span>
		<span style="margin-left: 50px;margin-right: 50px;">操作</span>
	</div>
	<br/>
	
	<c:set var="currTime" value="${mfn:getcurrtime(\"yyyy-MM-dd HH:mm:ss\") }" scope="page"/>
	
	<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">

<c:forEach items="${pages.list }" var="trade">

		<%-- <c:remove var="status" scope="page"/>
		<c:choose>
			<c:when test="${trade.status_  eq 1 && mfn:comparedate(trade.payExpirtyTime,currTime)}">
				<c:set var = "status" value="1"/>
			</c:when>
			<c:when test="${trade.status_ eq 3 && mfn:comparedate(trade.confirmReceiptExpirtyTime,currTime)}">
				<c:set var = "status" value = "3"/>
			</c:when>
			<c:when test="${trade.status_ eq 4 || (trade.status_ eq 3 && mfn:comparedate(currTime,trade.confirmReceiptExpirtyTime)) }">
				<c:set var = "status" value = "4"/>
			</c:when>
			<c:when test="${trade.status_ eq 5 || (trade.status_ eq 1 && mfn:comparedate(currTime,trade.payExpirtyTime)) }">
				<c:set var = "status" value = "3"/>
			</c:when>
			<c:when test="${trade.status_ eq 2 || trade.status_ eq 6 || trade.status_ eq 7 || trade.status_ eq 8 }">
				<c:set var = "status" value = "${trade.status_ }"/>
			</c:when>
		</c:choose> --%>
		
		<tr class="tt" >
			<td width="320px">订单号：<a  href="<c:url value='/TradeServlet?methodName=load&tradeid=${trade.tradeid}'/>">${trade.tradeid}</a></td>
			<td width="200px">下单时间：${trade.tradeTime }</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2" >
			
	  <c:forEach items="${trade.tradeItems }" var="tradeItem" varStatus="vs">
	  <!--只展示两条订单项  -->
	  	<c:if test="${vs.count le 2 }">
			<a class="link2" href="<c:url value='/BookServlet?methodName=findOneBook&bookid=${tradeItem.book.id }'/>">
			    <img border="0" width="70" src="<c:url value='/${tradeItem.book.imgCacheUrl }'/>"/>
			</a>
		</c:if>
		<c:if test="${vs.last && fn:length(trade.tradeItems) gt 2 }">
			<span>等${fn:length(trade.tradeItems)}本图书</span>
		</c:if>
	  </c:forEach>

			</td>
			<td width="115px">
				<span class="price_t">&yen;${trade.tradeMoney }</span>
			</td>
			<td id = "${trade.tradeid }TradeStatus" width="142px">
				<c:choose>
					<c:when test="${not empty trade.handle }">
						<c:choose>
							<c:when test="${trade.handle.isValid eq 2 }">(取消退款)</c:when>
							<c:when test="${trade.handle.status_ eq 2}">(退款中)</c:when>
							<c:when test="${trade.handle.refundRecords.status_ eq  2}">(退款被拒)</c:when>
							<c:when test="${trade.handle.refundRecords.status_ eq 3 }">(退款成功)</c:when>
						</c:choose>
					</c:when>
					<c:when test="${trade.status_ eq 1 }">(等待付款)</c:when>
					<c:when test="${trade.status_ eq 2 }">(等待发货)</c:when>
					<c:when test="${trade.status_ eq 3 }">(等待确认)</c:when>
					<c:when test="${trade.status_ eq 4 || trade.status_ eq 8 }">(交易成功)</c:when>
					<c:when test="${trade.status_ eq 5 }">(已取消)</c:when>
					<c:when test="${trade.status_ eq 6 }">(退款中)</c:when>
					<c:when test="${trade.status_ eq 7 }">(退款成功)</c:when>
				</c:choose>			

			</td>
			<td id="${trade.tradeid }Operate">
			<a href="<c:url value='/TradeServlet?methodName=load&tradeid=${trade.tradeid}'/>">查看</a><br/>
			<c:if test="${trade.status_ eq 1 }">
				<a href="<c:url value='/TradeServlet?methodName=paymentPre&tradeid=${trade.tradeid }'/>">支付</a><br/>
				<a class = "cancelBtn" id="${trade.tradeid }Cancel" >取消</a><br/>						
			</c:if>
			<c:if test="${trade.status_ eq 2}">
				<a href="<c:url value='/TradeServlet?methodName=refundPre&tradeid=${trade.tradeid}'/>">退款</a><br/>						
			</c:if>
			<c:if test="${trade.status_ eq 3 }">
				<a class = "confirmBtn" id = "${trade.tradeid }Confirm">确认收货</a><br/>						
			</c:if>
			<c:if test="${trade.status_ eq 4 }">
				<a href="<c:url value='/TradeServlet?methodName=remarkPre&tradeid=${trade.tradeid}'/>">评价</a><br/>						
			</c:if>
			<c:if test="${trade.status_ eq 6 }">
				<a href="<c:url value='/TradeServlet?methodName=refundPre&tradeid=${trade.tradeid}'/>">退款</a><br/>						
				<a class = "cancelRefundBtn" id = "${trade.tradeid }CancelRefund">取消退款</a><br/>						
			</c:if>
			</td>
		</tr>
</c:forEach>



	</table>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>
  </body>
</html>

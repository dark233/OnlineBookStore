<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>退款</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/trade/refund.css'/>">
	
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
	<script src="<c:url value='/jsps/js/trade/refund.js'/>"></script>
	
  </head>
  
<body>
  <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
	
	<c:set var = "recentlyHandle" value="${trade.handles[fn:length(trade.handles)-1] }"></c:set>
	
	<div class="divOrder">
		<span>订单号：${trade.tradeid }</span>
	</div>
	<div class="divContent">
		<div class="div2">
			<dl>
				<dt>退款记录
					<c:choose>
						<c:when test="${recentlyHandle.isValid eq 2 }">(已取消)</c:when>
						<c:when test="${recentlyHandle.status_ eq 2 }">(退款处理中)</c:when>
						<c:when test="${recentlyHandle.refundRecords.status_ eq 2 }">(退款被拒)</c:when>
						<c:when test="${recentlyHandle.refundRecords.status_ eq 3 }">(退款成功)</c:when>
					</c:choose>
				</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">退款原因</th>
							<th class="tt" align="left">回复</th>
						</tr>

<c:forEach items="${trade.handles }" var="handle">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="120px">
								<div class="divRefundRecords" align="left">
									<p>
									${handle.refundRecords.refundReason }
									</p>
									<br/>
									<span>${handle.createTime }</span>
								</div>
							</td>
							<td class="td" width="120px">
									<c:choose>
										<c:when test="${handle.refundRecords.status_ eq 2 }">
										<div class="divRefundRecords" align="right">
											<p>拒绝退款：${handle.refundRecords.refusedReason}</p>
											<br/>
											<span>${handle.createTime }</span>
										</div>
										</c:when>
										<c:when test="${handle.refundRecords.status_ eq 3 }">
										<div class="divRefundRecords" align="right">
											<p>同意退款</p>
											<br/>
											<span>${handle.createTime }</span>
										</div>
										</c:when>
									</c:choose>
							</td>
						</tr>
</c:forEach>


					</table>
				</dd>
			</dl>
		</div>
		
		<c:if test="${isForm eq 1 }">
		<div class="div2">
			<dl>
				<dt>申请退款</dt>
				<dd>
					<form action="<c:url value='/TradeServlet'/>" method="post" id="form1" target="_top">
					<input type="hidden" name = "methodName" value="validateRefund"/>
					<input type="hidden" name = "tradeid" value="${trade.tradeid }"/>
					<span class = "title">退款原因</span><br/>
					<textarea name = "refundReason" rows="6" cols="100"></textarea>
					</form>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px ;">
			<a id = "refund" href = "javascript:$('#form1').submit();">申请退款</a>
		</div>
		</c:if>
	</div>
</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mfn" uri="http://www.taunton.com/jstl/myElFunction" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>结算页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/showitem.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript">
		var tradeUrl = "${pageContext.request.contextPath}/TradeServlet";
		var cartUrl = "${pageContext.request.contextPath}/ShoppingCartServlet";
		var linkBookDescUrl = "${pageContext.request.contextPath}/enter.jsp";
	</script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/jsps/js/cart/showitem.js'/>"></script>
  </head>
  
  <body>
  
    <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>
  
  <c:choose>
  	<c:when test="${empty sc || empty sc.shopppingCartItems }">
  		<jsp:forward page="/enter.jsp"></jsp:forward>
  	</c:when>
  	<c:otherwise>
<%-- <form id="form1" action="<c:url value='/TradeServlet'/>" method="post">
	<input type="hidden" name="cartItemIds" value="${cartItemIds }"/>
	<input type="hidden" name="methodName" value="createOrder"/> --%>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	
	<tr>
		<td colspan="6" bgcolor="#efeae5"><span style="font-weight: 900">收货地址</span></td>
	</tr>
	
	<c:forEach items="${shippingAddressList }" var="shippingAddress">
		<tr>
			<td colspan="6">
				<input id="${shippingAddress.shipAddrid }Addr" type="radio" name = "shippingAddress" value = "${shippingAddress.shipAddrid }" <c:if test="${shippingAddress.isDefu eq 1 }">checked="checked"</c:if> />
				${shippingAddress.shipAddr }&nbsp;&nbsp;(${shippingAddress.cnee }&nbsp&nbsp收)&nbsp&nbsp${shippingAddress.tel }&nbsp;&nbsp;
				<c:if test="${shippingAddress.isDefu eq 1 }"><font>默认地址</font></c:if>
			</td>
		</tr>
	</c:forEach>

	<tr bgcolor="#efeae5">
		<td width="400px" colspan="6"><span style="font-weight: 900;">生成订单</span></td>
	</tr>
	<tr align="center">
		<td width="10%">&nbsp;</td>
		<td width="50%" align="left">图书名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>


<c:forEach items="${sc.shopppingCartItems }" var="cartItem">
	<tr align="center">
		<td align="right">
			<a class="linkImage" href="${pageContext.request.contextPath }/BookServlet?methodName=findOneBook&bookid=${cartItem.book.id }"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.imgCacheUrl }'/>"/></a>
		</td>
		<td align="left">
			<a class = "linkTitle" href="${pageContext.request.contextPath }/BookServlet?methodName=findOneBook&bookid=${cartItem.book.id }"><span>${cartItem.book.title }</span></a>
		</td>
		<td>&yen;${cartItem.book.currPrice }</td>
		<td>${cartItem.quantity }</td>
		<td>
			<span class="price_n">&yen;<span class="subtotal">${cartItem.quantity*cartItem.book.currPrice }</span></span>
		</td>
		
		<td id="${cartItem.itemid}Status">
				<c:choose>
				<c:when test="${cartItem.book.isShow eq 2 }">
					<span style="color: #d0d0d0;font-size: 10pt;">已下架</span>			
				</c:when>
				<c:when test="${cartItem.book.storeNumber le 0 }">
					<span style="color: #c30;font-size: 10pt;">暂时无货</span>
				</c:when>
				<c:when test="${cartItem.book.storeNumber lt cartItem.quantity }">
					<span style="color: #c30;font-size: 10pt;">库存不足</span>
				</c:when>
			</c:choose>
		</td>
	</tr>
</c:forEach>
	
	
	







	<tr>
		<td colspan="6" align="right">
			<span>总计：</span><span class="price_t">&yen;<span id="total">${sc.shoppingCartMoney }</span></span>
		</td>
	</tr>
	
	<tr>
		<td style="border-top-width: 4px;" colspan="5" align="right">
			<a id="linkSubmit" href="#">提交订单</a>
		</td>
	</tr>
</table>
<!-- </form> -->
  	</c:otherwise>
  </c:choose>
  </body>
</html>

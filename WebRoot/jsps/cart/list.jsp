<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script src="<c:url value='/prompt/js/jquery-ui.min.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<script type="text/javascript">
		var cartUrl = "${pageContext.request.contextPath}/ShoppingCartServlet";
		var linkBookDescUrl = "${pageContext.request.contextPath}/enter.jsp";
	
	</script>
	<script src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/jsps/js/cart/cart.js'/>"></script>
	
  </head>
  <body>
<%--   <c:if test="${empty sc }">
	  <jsp:forward page="/ShoppingCartServlet"/>
  </c:if> --%>
  
  <div style="height:100px;width: 100%;background-color: #4169E1 ">
		<%@include file="/jsps/top.jsp" %>
	</div>

<c:choose>
	<c:when test="${empty sc.shopppingCartItems }">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
			</td>
			<td>
				<span class="spanEmpty">您的购物车中暂时没有商品</span>
			</td>
		</tr>
	</table>  
	</c:when>
	<c:otherwise>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr align="center" bgcolor="#efeae5">
		<td align="left" width="50px">
			<input type="checkbox" id="selectAll" /><label for="selectAll">全选</label>
		</td>
		<td colspan="2" align="left">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
		<td>操作</td>
	</tr>



<c:forEach items="${sc.shopppingCartItems}" var="cartItem">
	<tr class = "cartItem" id="${cartItem.itemid}Tr" align="center">
		<td class = "checkItem" align="left">
			<input value="${cartItem.itemid }"  type="checkbox" name="checkboxBtn" <c:if test="${cartItem.isChecked eq 1}">checked="checked"</c:if> />
			<input class = "bookid" type="hidden" value="${cartItem.book.id}"/>
			<input class = "isShow" type="hidden" value="${cartItem.book.isShow}"/>
			<input class = "storeNumber" type="hidden" value="${cartItem.book.storeNumber}"/>
		</td>
		<td align="left" width="70px">
			<a class="linkImage" href="${pageContext.request.contextPath }/BookServlet?methodName=findOneBook&bookid=${cartItem.book.id }"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.imgCacheUrl }'/>" style="width: 100px;height: 100px"/></a>
		</td>
		<td align="left" width="400px">
		    <a class="linkTitle" href="${pageContext.request.contextPath }/BookServlet?methodName=findOneBook&bookid=${cartItem.book.id }"><span>${cartItem.book.title }</span></a>
		</td>
		<td><span>&yen;<span class="currPrice" id="${cartItem.book.currPrice}Price">${cartItem.book.currPrice }</span></span></td>
		<td>
			<a class="jian" id="${cartItem.itemid }Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.itemid }Quantity" type="text" value="${cartItem.quantity }" /><a class="jia" id="${cartItem.itemid }Jia"></a>
		</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subTotal" id="${cartItem.itemid }Subtotal">${cartItem.book.currPrice*cartItem.quantity }</span></span>
		</td>
		<td id="${cartItem.itemid}Operate">
			<a class = "deleteOne"  href="#">删除</a>
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
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDelete();">批量删除</a>
		</td>
		<td colspan="3" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="right">
			<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	<form id="jieSuanForm" action="<c:url value='ShoppingCartServlet'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<input type="hidden" name="methodName" value="loadCartItems"/>
	</form>

	</c:otherwise>
</c:choose>
  </body>
</html>

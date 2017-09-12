<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书详细</title>
    
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
    <%-- <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script> --%>
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/desc.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	
	<script type="text/javascript">
		var username = "${sessionScope.user.username}";
		username = $.trim(username);
		var loginUrl = "${pageContext.request.contextPath}/jsps/user/login.jsp";
		var cartUrl = "${pageContext.request.contextPath}/ShoppingCartServlet?methodName=findShoppingCartWithUserId";
		/* var formdata=new FormData(); */  
		var bookid = "${book.id}";
		var currPrice = "${book.currPrice}";
		var isShow = "${book.isShow}";
		var storeNumber = "${book.storeNumber}";
	/* 	formdata.append("bookid",bookid);
		formdata.append("currPrice",currPrice);
		formdata.append("methodName","addToShoppingCart"); */
		var formdata = {"bookid":bookid,"currPrice":currPrice,"methodName":"addToShoppingCart"}; 
		var addToCartUrl = "${pageContext.request.contextPath}/ShoppingCartServlet";
		var formdata2 = {"bookid":bookid,"methodName":"validateAddToShoppingCart"};
		var validateAddToShoppingCartUrl = "${pageContext.request.contextPath}/ShoppingCartServlet";
	</script>
	
	<script src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script src="<c:url value='/jsps/js/book/desc.js'/>"></script>
  </head>
  
  <body>
  <div class="divBookName">${book.title }</div>
  <div>
    <img align="top" src="<c:url value='/${book.imgCacheUrl }'/>" class="img_image_w"/>
    <div class="divBookDesc">
	    <ul>
	    	<li>商品编号：${book.id }</li>
	    	<li>会员价：<span class="price_n">&yen;${book.currPrice }</span></li>
	    	<li>定价：<span class="spanPrice">&yen;${book.price }</span>　折扣：<span style="color: #c30;">${book.discount }</span>折</li>
	    	<li>销量：${book.salesAmount }</li>
	    	<li>库存：${book.storeNumber }</li>
	    </ul>
		<hr class="hr1"/>
		<table>
			<tr>
				<td colspan="3">
					作者：${book.author } 著
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：${book.press }
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：${book.publishingDate }</td>
			</tr>
			<tr>
				<td>版次：${book.edition }</td>
				<td>页数：${book.pageNum }</td>
				<td>字数：${book.wordNum }</td>
			</tr>
			<tr>
				<td width="180">印刷时间：${book.printDate }</td>
				<td>开本：${book.bookSize }开</td>
				<td>纸张：${book.paper }</td>
			</tr>
		</table>
		<div class="divForm">
			<%-- <form id="form1" action="<c:url value='/ShoppingCartServlet'/>" method="post">
				<input type="hidden" name="methodName" value="addToShoppingCart"/>
				<input type="hidden" name="bookid" value="${book.id }"/>
  				我要买：<input id="cnt" style="width: 40px;text-align: center;" type="text" name="quantity" value="1" />件
  			</form> --%>
<%--   			<a id="btn"   			
  				<c:choose>
	  			  <c:when test="${not empty sessionScope.user }">href="javascript:$('#form1').submit();"</c:when>
	  			  <c:otherwise>href="${pageContext.request.contextPath}/login.jsp" target = "_top"</c:otherwise>
  				</c:choose>>
  			</a> --%>
			<a id = "btn" ></a>
  		</div>
	</div>
  </div>
  
  
  
  	<div style="float:left; width: 100%; ">
		<hr/>
		<br/>
		<%@include file="/jsps/bookdescBottom/descBottom.jsp" %>
	</div>
	
	
  </body>
</html>

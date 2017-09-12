<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file = "public.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>图书展示页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="showbook page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
<style>
span{color:red;}
#div5{margin: auto;font-size: 20px;
	background-size: 100%;
	height: 100%;
	width: 100%;
	background-image: url("${pageContext.request.contextPath}/img/tu.jpg");
	background-repeat: no-repeat;
}
</style>
  </head>
  
  <body>
  <div id = "div5">
  <br/><br/>
  <center>
  <c:if test="${requestScope.book!=null }">
  <span>
  	您已成功将<a href = "${pageContext.request.contextPath }/BookServlet?methodName=findOne&pageNo=${bookpage.pageNo }&bookid=${book.id}&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}">${requestScope.book.title}</a>加入购物车
  	<a href = "${pageContext.request.contextPath}/shoppingCart.jsp?methodName=findList&pageNo=${bookpage.pageNo }&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}">查看购物车</a>
  </span>
  
  <br/>
  <c:if test="${sessionScope.sc!=null }">
  <span>
  	您的购物车里面有${sessionScope.sc.totalQuantity }件商品
  </span>
  </c:if>
  </c:if>
  </center>
  <c:if test="${bookpage!=null}">
	  <div align="center">
	  <form action="${pageContext.request.contextPath }/BookServlet" >
	  <input type = "text" name = "minPrice" placeholder="最低价格" value="${requestScope.minPrice }"/>
	  --
	  <input type = "text" name = "maxPrice" placeholder="最高价格" value="${requestScope.maxPrice }"/>
	  <input type = "hidden" name = "pageNo" value = "1"/>
	  <input type = "hidden" name = "methodName" value = "findList"/>
	  <input type = "submit" value = "搜索"/>
	  </form>
	  </div>
  
    
  <table align="center" cellpadding="10px">
  <caption></caption>
  <tr>
  <th>书名</th>
  <th>作者</th>
  <th>售价</th>
  <th>销量</th>
  <th>库存</th>
  <th></th>
  </tr>
	<c:forEach var = "book" items="${bookpage.list}">
		<tr align="center">
		<td><a href = "${pageContext.request.contextPath }/BookServlet?methodName=findOne&pageNo=${bookpage.pageNo }&bookid=${book.id}&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}" target="_blank">${book.title}</a></td>
		<td>${book.author}</td>
		<td>${book.price }</td>
		<td>${book.salesAmount }</td>
		<td>${book.storeNumber }</td>
		<td><a href = "${pageContext.request.contextPath }/BookServlet?methodName=addToShoppingCart&bookid=${book.id}&count=1&pageNo=${bookpage.pageNo }&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}">加入购物车</a></td>
		</tr>
		</c:forEach>
	  </table>
	  <center>
	
	<c:if test="${bookpage.totalPageNumber!=0 }">
	  <c:choose>
	  <c:when test="${bookpage.totalPageNumber<=10 }">
	  <c:set var = "begin" value="1"/>
	  <c:set var = "end" value = "${bookpage.totalPageNumber }"/>
	  </c:when>
	  <c:otherwise>
	  
	   <c:set var = "begin" value = "${bookpage.pageNo-4}"/>
	  <c:set var = "end" value = "${bookpage.pageNo+5}"/>
	  
	  <c:if test="${(bookpage.pageNo-4)<1}">
	  <c:set var = "begin" value = "1"/>
	  <c:set var = "end" value = "10"/>
	  </c:if>
	  
	  <c:if test="${(bookpage.pageNo+5)>(bookpage.totalPageNumber)}">
	  <c:set var = "begin" value = "${bookpage.totalPageNumber-9}"/>
	  <c:set var = "end" value = "${bookpage.totalPageNumber}"/>
	  </c:if>
	  
	  </c:otherwise>
	  </c:choose>
	  共${bookpage.totalPageNumber }页/当前第${bookpage.pageNo }页
	  <a name = "first" href = "${pageContext.request.contextPath }/BookServlet?methodName=findList&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&pageNo=1">首页</a>
	  <c:if test="${bookpage.hasPrev}">
	  <a name = "prev" href = "${pageContext.request.contextPath }/BookServlet?methodName=findList&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&pageNo=${bookpage.prevPage}">上一页</a>
	  </c:if>
	  
	  <c:forEach  var = "i" begin="${begin}" end = "${end }">
	  <a name = "num" href = "${pageContext.request.contextPath }/BookServlet?methodName=findList&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&pageNo=${i}">${i}</a>
	  </c:forEach>
	  <c:if test="${bookpage.hasNext}">
	  <a name = "next" href = "${pageContext.request.contextPath }/BookServlet?methodName=findList&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&pageNo=${bookpage.nextPage}">下一页</a>
	  </c:if>
	  
	  <a name = "last" href = "${pageContext.request.contextPath }/BookServlet?methodName=findList&minPrice=${requestScope.minPrice}&maxPrice=${requestScope.maxPrice}&pageNo=${bookpage.totalPageNumber}">末页</a>
	  
	    <form action="${pageContext.request.contextPath }/BookServlet" >
	  <input type = "number" name = "pageNo" min = "1" max = "${bookpage.totalPageNumber }" placeholder="页码"  value="${bookpage.pageNo}"/>
	  <input type = "hidden" name = "minPrice" value = "${requestScope.minPrice}"/>
	  <input type = "hidden" name = "maxPrice" value = "${requestScope.maxPrice}"/>
	  <input type = "hidden" name = "methodName" value = "findList"/>
	  <input type = "submit" value = "跳页"/>
	  </form>
	  </center>
  </c:if>
  
  <c:if test="${bookpage.totalPageNumber==0 }">
  	查询结果为空
  </c:if>
	</c:if>
	
	</div>
	<script type="text/javascript">
	</script>
  </body>

</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
	
<%--     <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
--%>	
 	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
	
		var flag = ${requestScope.search ne null};
		var search = "${requestScope.search}";
		var priceUrl = "${requestScope.priceUrl}";
	</script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
	<style type="text/css">
		.divOrderFieldBody {
			background: url(/BookStore/images/paginating_bg.png) repeat-x;
			border-top: 2px solid #FE7C2C;
			height: 35px;
			width: 100%;
			text-align: left;
		}
		.divOrderFieldContent {
			line-height: 35px;
			margin-right: 15px;
		}
		
		.spanOrderFieldDisabled {
			border: 1px solid #c1c1c1;
			text-align:center; 
			background-color: #FFFFFF; 
			color: #c1c1c1;
			padding: 4px 8px 3px 8px;
			font-weight: 900;
		}
		
		.aOrderField {
			border: 1px solid #c1c1c1; 
			text-align:center; 
			background-color: #FFFFFF; 
			color: #555555;
			padding: 4px 8px 3px 8px;
			text-decoration: none;
		}
		
		.aOrderField :HOVER {
			border: 1px solid #F75A00;
			background-color: #FE7C2C; 
			color: #FFFFFF;
		}
		.rangeOfPrice {
			height: 21px;
		}
	</style>
  </head>
  
  <body>
		<div class = "divOrderFieldBody">
			<div class = "divOrderFieldContent">
					<c:choose>
					<c:when test="${requestScope.orderField eq null}">
						<span class = "spanOrderFieldDisabled">默认排序</span>
					</c:when>
					<c:otherwise>
						<a href="${requestScope.orderUrl }" class="aOrderField">默认排序</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${requestScope.orderField ne null&&requestScope.orderField eq 'salesAmount DESC' }">
						<span class = "spanOrderFieldDisabled">销量</span>
					</c:when>
					<c:otherwise>
						<a href="${requestScope.orderUrl }&orderField=salesAmount DESC" class="aOrderField">销量</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${requestScope.orderField ne null&&requestScope.orderField eq 'currPrice ASC' }">
						<span class = "spanOrderFieldDisabled">价格从低到高</span>
					</c:when>
					<c:otherwise>
						<a href="${requestScope.orderUrl }&orderField=currPrice ASC" class="aOrderField">价格从低到高</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${requestScope.orderField ne null&&requestScope.orderField eq 'currPrice DESC' }">
						<span class = "spanOrderFieldDisabled">价格从高到底</span>
					</c:when>
					<c:otherwise>
						<a href="${requestScope.orderUrl }&orderField=currPrice DESC" class="aOrderField">价格从高到底</a>
					</c:otherwise>
				</c:choose>
				<span class = "rangeOfPrice" id = "rangeOfPrice">
				 <input type = "text" name = "minPrice" placeholder="最低价格" value="${requestScope.minPrice }"/>
					  --
				 <input type = "text" name = "maxPrice" placeholder="最高价格" value="${requestScope.maxPrice }"/>
				 <input type = "button" id="searchByPrice"  value = "搜索"/>
				 </span>
			</div>
		</div>
		
	<%-- <table>
	<c:forEach var = "i" begin = "0" end = "${fn:length(booklist)}" step="4">
	<tr> --%>
	
	
	<ul>

		<c:forEach items="${pages.list }" var="book">
		  <li>
		  <div class="inner">
		    <a class="pic" href="<c:url value='/BookServlet?methodName=findOneBook&bookid=${book.id }'/>"><img src="<c:url value='/${book.imgCacheUrl }'/>" border="0" style="width: 200px;height: 200px"/></a>
		    <p class="price">
				<span class="price_n">&yen;${book.currPrice }</span>
				<span class="price_r">&yen;${book.price }</span>
				(<span class="price_s">${book.discount }折</span>)
			</p>
			<p><a  id="bookname" title="${book.title }" href="<c:url value='/BookServlet?methodName=findOneBook&bookid=${book.id }'/>">${book.title }</a></p>
			<%-- url标签会自动对参数进行url编码 --%>
			<c:url value="/BookServlet" var="authorUrl">
				<c:param name="methodName" value="findByCombination"/>
				<c:param name="author" value="${book.author }"/>
			</c:url>
			<c:url value="/BookServlet" var="pressUrl">
				<c:param name="methodName" value="findByCombination"/>
				<c:param name="press" value="${book.press }"/>
			</c:url>
			<p><a href="${authorUrl }" name='P_zz' title='${book.author }'>${book.author }</a></p>
			<p><span>销量：</span>${book.salesAmount }</p>
			<p class="publishing">
				<span>出 版 社：</span><a href="${pressUrl }">${book.press }</a>
			</p>
			<p class="publishing_time"><span>出版时间：</span>${book.publishingDate }</p>
		  </div>
		  </li>
		</c:forEach>




	</ul>
	
	<%-- </tr>
	</c:forEach>
	</table>  --%>

	<div style="float:left; width: 100%; text-align: center;">
		<hr/>
		<br/>
		<%@include file="/jsps/pager/pager.jsp" %>
	</div>

  </body>
 
</html>


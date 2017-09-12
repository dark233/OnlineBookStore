<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>评论列表</title>
    
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
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/remark/remark.css'/>" />
 	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
 	<script type="text/javascript">
 		var remarkLevelUrl="${remarkLevelUrl}";
 	</script>
 	<script type="text/javascript" src="<c:url value='/jsps/remark/remark.js'/>"></script>
  </head>
  
  <body>
		<div class = "divOrderFieldBody">
			<div class = "divOrderFieldContent">
				<input type="radio" name="remarkLevel" value="0" <c:if test="${remarkLevel eq 0 }">checked="checked"</c:if>/>全部评论
				<input type="radio" name="remarkLevel" value="3" <c:if test="${remarkLevel eq 4 }">checked="checked"</c:if>/>好评
				<input type="radio" name="remarkLevel" value="2" <c:if test="${remarkLevel eq 3 }">checked="checked"</c:if>/>中评
				<input type="radio" name="remarkLevel" value="1" <c:if test="${remarkLevel eq 1 }">checked="checked"</c:if>/>差评
			</div>
		</div>
		
	<br/>
	<br/>
	<div class="comment-container">
		<ul>
		<c:forEach items="${pages.list }" var="remark"> 
			<li class="comment-content-li">
     			<div class="from-whom">
     				<img src="<c:url value='/${remark.user.headImgUrl }'/>" class="head-img">
     				<span class="name">${remark.user.username}</span>
     				<div class="comment-star" >
	     				<c:forEach var="i" begin="1" end="${remark.remarkLevel }">
	     				  <img  src="<c:url value='/jsps/remark/img/b_red_star.gif'/>"/>
	     				</c:forEach>
     				</div>
     			</div>
     			<div class="review-details">
     				<div class="comment-details">
     					<div class="comment-details-content">
     						${remark.remark }
     					</div>
     					<c:if test="${remark.remarkImgUrl ne null }">
     					<ul class="comment-details-img">
     						<li>
     							<img class = "remark-img" src="<c:url value='/${remark.remarkImgUrl }'/>"/>
     						</li>
     					</ul>
     					</c:if>
     					<div class="comment-details-time">
     						<span class="time">${remark.remarkDate }</span>
     						<span class="icon">
     							有用 &nbsp;(0)
     						</span>
     					</div>
     				</div>
     			</div>
			</li>
		</c:forEach>
		</ul>
	</div>


	<div style="float:left; width: 100%; text-align: center;">
		<hr/>
		<br/>
		<%@include file="/jsps/pager/pager.jsp" %>
	</div>

  </body>
 
</html>




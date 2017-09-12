<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>分类列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/category/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">
	
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/adminjsps/admin/js/category/list.js'/>"></script>
  </head>
  
  <body>
    <h2 style="text-align: center;">分类列表</h2>
    <table align="center" border="1" cellpadding="0" cellspacing="0">
    	<caption class="captionAddOneLevel">
    	  <a href="<c:url value='/adminjsps/admin/category/add.jsp'/>">添加一级分类</a>
    	</caption>
    	<tr class="trTitle">
    		<th>分类名称</th>
    		<th>描述</th>
    		<th>操作</th>
    	</tr>
    	
<c:forEach items="${parents }" var="parent">    	
    	<tr class="trOneLevel">
    		<td id="${parent.cid }Cname" width="200px;">${parent.cname }</td>
    		<td>${parent.desc }</td>
    		<td width="200px;">
    		  <a href="<c:url value='/backstage/CategoryServlet?methodName=addChildPre&pid=${parent.cid }'/>">添加二级分类</a>
    		  <a href="<c:url value='/backstage/CategoryServlet?methodName=editParentPre&cid=${parent.cid }'/>">修改</a>
    		  <a class = "delLevelOne" id = "${parent.cid }" >删除</a>
    		</td>
    	</tr>
   <c:forEach items="${parent.children }" var="child">
    	<tr class="trTwoLevel">
    		<td id="${child.cid }Cname">${child.cname }</td>
    		<td>${child.desc }</td>
    		<td width="200px;" align="right">
    		  <a href="<c:url value='/backstage/CategoryServlet?methodName=editChildPre&cid=${child.cid }'/>">修改</a>
    		  <a class = "delLevelTwo" id = "${child.cid }">删除</a>
    		</td>
    	</tr>
   </c:forEach>
</c:forEach>


    </table>
  </body>
</html>

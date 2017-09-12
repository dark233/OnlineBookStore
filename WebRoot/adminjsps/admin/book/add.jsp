<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mfn" uri="http://www.taunton.com/jstl/myElFunction" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>add.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/add.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/qikoo.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/store.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/prompt/css/alert.css'/>">

	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/jquery.alert.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/prompt/js/qikoo.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/adminjsps/admin/js/book/add.js'/>"></script>
  </head>
  
  <body>
  <div>
   <p style="font-weight: 900; color: red;">${msg }</p>
   <form action="<c:url value='/backstage/PutAwayBookServlet'/>" enctype="multipart/form-data" method="post" id="form">
    <div>
	    <ul>
	    	<li>书名：　<input id="bname" type="text" name="title" value="" style="width:500px;"/></li>
	    	<li>封面：　<input id="imgcacheurl" type="file" name="imgCacheUrl"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="" style="width:30px;"/>折</li>
	    	<li>当前价：<input id="currPrice" type="text" name="currPrice" value="" style="width:50px;"/></li>
	    	<li>库存：<input id="storeNumber" type="text" name="storeNumber" value="" style="width:50px;"/></li>

	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table>
			<tr>
				<td colspan="3">
					作者：　　<input type="text" id="author" name="author" value="" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input type="text" name="press" id="press" value="" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：<input type="text" id="publishingDate" name="publishingDate" value="${mfn:getcurrtime('yyyy-MM-dd') }" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>版次：　　<input type="text" name="edition" id="edition" value="" style="width:40px;"/></td>
				<td>页数：　　<input type="text" name="pageNum" id="pageNum" value="" style="width:50px;"/></td>
				<td>字数：　　<input type="text" name="wordNum" id="wordNum" value="" style="width:80px;"/></td>
			</tr>
			<tr>
				<td width="250">印刷时间：<input type="text" name="printDate" id="printDate" value="${mfn:getcurrtime('yyyy-MM-dd') }" style="width:100px;"/></td>
				<td width="250">开本：　　<input type="text" name="bookSize" id="bookSize" value="" style="width:30px;"/></td>
				<td>纸张：　　<input type="text" name="paper" id="paper" value="" style="width:80px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="pid" id="pid" onchange="loadChildren()">
						<option value="">====请选择1级分类====</option>
<c:forEach items="${parents }" var="parent">
			    		<option value="${parent.cid }">${parent.cname }</option>
</c:forEach>

					</select>
				</td>
				<td>
					二级分类：<select name="cid" id="cid">
						<option value="">====请选择2级分类====</option>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btn" class="btn" value="新书上架">
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>

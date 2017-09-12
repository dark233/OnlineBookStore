<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>top.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
<body>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 40px;
        background: url(<c:url value='/adminjsps/admin/main/images/layout-browser-hd-bg.gif'/>) #7f99be repeat-x center 50%;
        line-height: 30px; color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float: right; padding-right: 20px;" class="head">欢迎 未登录 <a href="#" id="editpass">
            修改密码</a> <a href="#" id="loginOut">安全退出</a></span> <span style="padding-left: 10px;
                font-size: 16px; float: left;">
                <img src="<c:url value='/adminjsps/admin/main/images/blocks.gif'/>" width="20" height="20" align="absmiddle" />
                NETBOOKSTORE</span>
        <ul id="css3menu" style="padding: 0px; margin: 0px; list-type: none; float: left;
            margin-left: 40px;">
            <li><a class="active" name="basic" href="javascript:;" title="常用菜单">常用菜单</a></li>
<!--             <li><a name="point" href="javascript:;" title="邮件列表">邮件列表</a></li>
 -->        </ul>
    </div>
</body>
</html>
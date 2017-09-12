<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ include file="public.jsp"%> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>登录界面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>

<style type="text/css">
#bg {
	background-size: 100%;
	height: 100%;
	width: 100%;
	background-image: url("${pageContext.request.contextPath}/img/tu.jpg");
	background-repeat: no-repeat;
}
</style>

</head>

<body id="bg" style="overflow:hidden;">
	<div style="width: 500px;
	margin-left:730px;
	margin-top:180px;
	font-size: 12px;
	border: 1px solid #D2D3D5;
	background-color: #8FB7EA;">
		<br /> <br />
<!-- 		<div style="border:1;border-color: red;margin: auto;width: 400px;height: 400px;">
 -->		
		
		<form id = "loginform" action="${pageContext.request.contextPath}/UserServlet" method="post">
			<table border="0" bordercolor="red">
				<caption>
					<h2>登录用户</h2>
				</caption>
				<tr>
					<td align="center">账号</td>
					<td><input type="text" name="username" placeholder="输入用户名或手机号码" maxlength="20"/></td>
				</tr>
				<tr>
					<td height="20px" colspan="2" align="right"><span name="s1" ></span></td>
				</tr>
				<tr>
					<td align="center">密码</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td height="20px" colspan="2" align="right"><span name="s2"></span></td>
				</tr>
				<tr>
					<td align="center">验证码</td>
					<td><input type="text" name="check" maxlength="4" /> 
					<img id="img" src="${pageContext.request.contextPath}/GetImage" align="middle"></td>
				</tr>
				<tr>
					<td height="20px" colspan="2" align="right"><span name="s3"></span></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><a id="change" href="#">看不清,换一张</a></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><a id="change" href="${pageContext.request.contextPath }/jsps/user/forgetpwd.jsp">忘记密码</a></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button"  value="立即登录"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="hidden" name="methodName"
						value="login" /></td>

				</tr>
				<tr>
					<td  align="center"><input type = "checkbox" name = "isRemeber" value="Remeberme"/>Remeber me</td>
					<td  align="center">还没账号?<a href="${pageContext.request.contextPath }/jsps/user/regist.jsp">立即注册</a></td>
				</tr>
			</table>
		</form>
		<!-- </div> -->
	</div>

	<script type="text/javascript">
	$(function(){
		function keyUp(event){
	        if (event.keyCode === 13){
	        	$("[value='立即登录']").click();
	        }
		};
		document.onkeyup=keyUp;
		
	});
	
		var $username = $("input[name='username']");
		var $password = $("input[name='password']");
		var $code = $("input[name='check']");
		var $login = $("[value='立即登录']");
		var $span1 = $("span[name='s1']");
		var $span2 = $("span[name='s2']");
		var $span3 = $("span[name='s3']");
		
		/*获取cookie  */
		function getCookie(name) {
			if (document.cookie.length > 0) {
				start = document.cookie.indexOf(name + "=");
				if (start != -1) {
					start = start + name.length + 1;
					end = document.cookie.indexOf(";", start);
					if (end == -1)
						end = document.cookie.length;
					return decodeURI(document.cookie.substring(start, end));
				}
			}
			return "";
		}

		/*添加cookie  */
		function setCookie(name, value, expiredays) {
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + expiredays);
			document.cookie = name
					+ "="
					+ escape(value)
					+ ((expiredays == null) ? "" : ";expires="
							+ exdate.toGMTString());
		}

		/* 检查cookie */
		function checkCookie() {
			username = getCookie('username');
			password = getCookie('pwd');
			if (username != null && username != "")
				$username.val(username);
			if(password != null && password !="")
				$password.val(password);
		}

		$(function() {
			checkCookie();
		});

		$("#change").click(
				function() {
					var src = "${pageContext.request.contextPath}/GetImage?"
							+ new Date().getTime();
					$("#img").attr("src", src);
				});
		$username.blur(function() {
			var value = $(this).val();
			//var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
			value = $.trim(value);
			if (value == "") {
				$span1.attr("id", "0");
				$span1.text("用户名不能为空!").css("color", "red");
				return;
			} /* else if (!(reg.test(value))) {
												$span.attr("id", "0");
												$span.text("用户名必须以字母开头长度为3-20").css("color", "red");
												return;
											}  */
			else {
				$span1.attr("id", "1");
				$span1.text("√").css("color", "green");
				return;
			}
		});

		$password.blur(function() {
			var value = $(this).val();
			var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
			value = $.trim(value);
			if (value == "") {
				$span2.attr("id", "0");
				$span2.text("密码不能为空!").css("color", "red");
				return;
			} else if (!(reg.test(value))) {
				$span2.attr("id", "0");
				$span2.text("密码必须以字母开头长度为3-20").css("color", "red");
				return;
			} else {
				$span2.attr("id", "1");
				$span2.text("√").css("color", "green");
				return;
			}
		});
		$code.blur(function() {
			var value = $(this).val();
			var reg = /^[a-zA-Z_0-9\-@]{4}$/;
			value = $.trim(value);
			if (value == "") {
				$span3.attr("id", "0");
				$span3.text("验证码不能为空!").css("color", "red");
				return;
			} else if (!(reg.test(value))) {
				$span3.attr("id", "0");
				$span3.text("验证码必须为四位数字或字母").css("color", "red");
				return;
			} else {
				var sendData = {
					"img" : value,
					"methodName" : "validateImage"
				};
				var url = "${pageContext.request.contextPath}/UserServlet";
				$.post(url, sendData, function(a, b, ajax) {
					var msg = ajax.responseText;
					if (msg == "0") {
						$span3.attr("id", "0");
						$span3.text("验证码有误").css("color", "red");
						return;
					}
				});
				$span3.attr("id", "1");
				$span3.text("√").css("color", "green");
				return;
			}
		});
		$login
				.click(function() {
					var f1 = $span1.attr("id");
					var f2 = $span2.attr("id");
					var f3 = $span3.attr("id");
					if (f1 == undefined || f1 == "") {
						$username.blur();
						f1 = $span1.attr("id");
					}
					if (f2 == undefined || f2 == "") {
						$password.blur();
						f2 = $span2.attr("id");
					}
					if (f3 == undefined || f3 == "") {
						$code.blur();
						f3 = $span3.attr("id");
					}
					if (f1 == 1 && f2 == 1 && f3 == 1) {
						var sendData = $("form").serialize();
						var url = "${pageContext.request.contextPath}/UserServlet";
						$
								.post(
										url,
										sendData,
										function(a, b, ajax) {
											var msg = ajax.responseText;
											if (msg == "0") {
												$span1.text("用户名或密码有误").css(
														"color", "red");
												var src = "${pageContext.request.contextPath}/GetImage?"
														+ new Date().getTime();
												$("#img").attr("src", src);
												return;
											} else if (msg == "2") {
												$span1.text("您已被封禁，请联系工作人员解禁")
														.css("color", "red");
												var src = "${pageContext.request.contextPath}/GetImage?"
														+ new Date().getTime();
												$("#img").attr("src", src);
												return;
											} else if (msg == "1") {
												window.location.href = "${pageContext.request.contextPath}/enter.jsp";
											}
										});
					}
				});
	</script>
</body>
</html>

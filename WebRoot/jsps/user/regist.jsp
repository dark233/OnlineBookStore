<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ include file="public.jsp"%> --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>注册页面</title>

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
		<br /> <br /> <br />
		<form action="${pageContext.request.contextPath}/UserServlet?methodName=regist" method="post">
			<table>
			<caption><h2>注册用户</h2></caption>
				<tr>
					<td align="right">用户名</td>
					<td><input type="text" name="username"/></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s1"></span></td>
				</tr>
				<tr>
					<td align="right">密码</td>
					<td><input type="password" name="pwd"/></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s2"></span></td>
				</tr>
				<tr>
					<td align="right">确认密码</td>
					<td><input type="password" name="pwd2"/></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s3"></span></td>
				</tr>
				<tr>
					<td align="right">支付密码</td>
					<td><input type="password" name="pay_pwd" maxlength="6"/></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s4"></span></td>
				</tr>
				<tr>
					<td align="right">确认支付密码</td>
					<td><input type="password" name="pay_pwd2" maxlength="6"/></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s5"></span></td>
				</tr>
				
				<tr>
					<td align="right">手机号码</td>
					<td><input type="text"  name = "tel" maxlength="11"/></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align=left><span name = "s6"></span></td>
				</tr>
				<tr>
					<td align="right">手机验证码</td>
					<td><input type="text"  name = "telCode" maxlength="4"/></td>
					<td><input type="button"  value="获取验证码" style="width: 188px;height: 23px;"></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s7"></span></td>
				</tr>
				
				<tr>
					<td align="right">验证码</td>
					<td><input type="text"  name = "check" maxlength="4"/></td>
					<td><img id="img" src="${pageContext.request.contextPath}/GetImage" align="middle"></td>
				</tr>
				<tr>
					<td></td>
					<td height="20px" colspan="2" align="left"><span name = "s8"></span></td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td align="left"><a id="change" href="#">看不清,换一张</a></td>
				</tr>

				<tr>
					<td colspan="2"><input type="hidden" name="methodName"
						value="regist" /></td>

				</tr>
				<tr>
					<td align="center" colspan="3"><input type="button" value="注册"></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	$(function(){
		function keyUp(event){
	        if (event.keyCode === 13){
	        	$("[value='注册']").click();
	        }
		};
		document.onkeyup=keyUp;
		
	});
	
	
	var $username = $("input[name='username']");
	var $pwd = $("input[name='pwd']");
	var $pwd2 = $("input[name='pwd2']");
	var $pay_pwd = $("input[name='pay_pwd']");
	var $pay_pwd2 = $("input[name='pay_pwd2']");
	var $tel = $("input[name='tel']");
	var $telCode = $("input[name='telCode']");
	var $getTelCode = $("input[value='获取验证码']");
	var $code = $("input[name='check']");
	var $regist = $("[value='注册']");
	var $span1 = $("span[name='s1']");
	var $span2 = $("span[name='s2']");
	var $span3 = $("span[name='s3']");
	var $span4 = $("span[name='s4']");
	var $span5 = $("span[name='s5']");
	var $span6 = $("span[name='s6']");
	var $span7 = $("span[name='s7']");
	var $span8 = $("span[name='s8']");
	$("#change").click(function(){
		var src = "${pageContext.request.contextPath}/GetImage?"+new Date().getTime();
		$("#img").attr("src",src);
	});
	
	$username.blur(function() {
		var value = $(this).val();
		var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
		value = $.trim(value);
		if (value == "") {
			$span1.attr("id", "0");
			$span1.text("用户名不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span1.attr("id", "0");
			$span1.text("用户名必须以字母开头长度为3-20").css("color", "red");
			return;
		} else {
			var sendData = {
					"username" : value,
					"methodName" : "validateUsername"
				};
				var url = "${pageContext.request.contextPath}/UserServlet";
				$.post(url, sendData, function(a, b, ajax) {
					var msg = ajax.responseText;
					if (msg == "1") {
						$span1.attr("id", "1");
						$span1.text("√").css("color", "green");
						return;
					}else{
						$span1.attr("id", "2");
						$span1.text("用户名已存在！").css("color", "red");
						return;
					}
				});
		}
	});
	
	
	$pwd.blur(function() {
		var value = $(this).val();
		var value2 = $pwd2.val();
		var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
		value = $.trim(value);
		value2 = $.trim(value2);
		if (value == "") {
			$span2.attr("id", "0");
			$span2.text("密码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span2.attr("id", "0");
			$span2.text("密码必须以字母开头长度为3-20").css("color", "red");
			return;
		}else if(value2 != ""){
			$pwd2.blur();
		} else {
			$span2.attr("id", "1");
			$span2.text("√").css("color", "green");
			return;
		}
	});
	
	$pwd2.blur(function() {
		var value = $(this).val();
		var value2 = $pwd.val();
		var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
		value = $.trim(value);
		value2 = $.trim(value2);
		if (value == "") {
			$span3.attr("id", "0");
			$span3.text("密码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span3.attr("id", "0");
			$span3.text("密码必须以字母开头长度为3-20").css("color", "red");
			return;
		}else if(value!=value2){
			$span3.attr("id","0");
			$span2.attr("id","0");
			$span3.text("两次密码不一致").css("color","red");
			return;
		} else {
			$span3.attr("id", "1");
			$span2.attr("id","1");
			$span3.text("√").css("color", "green");
			$span2.text("√").css("color", "green");
			return;
		}
	});
	
	
	$pay_pwd.blur(function() {
		var value = $(this).val();
		var value2 = $pay_pwd2.val();
		var reg = /^\d{6}$/;
		value = $.trim(value);
		value2 = $.trim(value2);
		if (value == "") {
			$span4.attr("id", "0");
			$span4.text("密码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span4.attr("id", "0");
			$span4.text("支付密码必须为六位数字").css("color", "red");
			return;
		}else if(value2!=""){
			$pay_pwd2.blur();
		} else {
			$span4.attr("id", "1");
			$span4.text("√").css("color", "green");
			return;
		}
	});
	
	$pay_pwd2.blur(function() {
		var value = $(this).val();
		var value2 = $pay_pwd2.val();
		var reg = /^\d{6}$/;
		value = $.trim(value);
		value2 = $.trim(value2);
		if (value == "") {
			$span5.attr("id", "0");
			$span5.text("支付密码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span5.attr("id", "0");
			$span5.text("支付密码必须为六位数字").css("color", "red");
			return;
		}else if(value!=value2){
			$span5.attr("id","0");
			$span4.attr("id","0");
			$span.text("两次密码不一致").css("color","red");
			return;
		} else {
			$span5.attr("id", "1");
			$span4.attr("id","1");
			$span5.text("√").css("color", "green");
			$span4.text("√").css("color", "green");
			return;
		}
	});
	
	
	$tel.blur(function() {
		var value = $(this).val();
		var reg = /^1[34578]\d{9}$/;
		value = $.trim(value);
		if (value == "") {
			$span6.attr("id", "0");
			$span6.text("手机号码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span6.attr("id", "0");
			$span6.text("手机号码格式错误").css("color", "red");
			return;
		}else{
			var sendData = {
					"tel" : value,
					"methodName" : "validateTel"
				};
				var url = "${pageContext.request.contextPath}/UserServlet";
				$.post(url, sendData, function(a, b, ajax) {
					var msg = ajax.responseText;
					if (msg == "1") {
						$span6.attr("id", "1");
						$span6.text("√").css("color", "green");
						return;
					}else{
						$span6.attr("id", "2");
						$span6.text("手机号码已被绑定！").css("color", "red");
						return;
					}
				});
		}
	});
	
	$telCode.blur(function(){
		var value = $(this).val();
		var reg = /^\d{4}$/;
		value = $.trim(value);
		if (value == "") {
			$span7.attr("id", "0");
			$span7.text("手机验证码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span7.attr("id", "0");
			$span7.text("手机验证码格式错误").css("color", "red");
			return;
		}else {
			var sendData = {
				"telCode" : value,
				"methodName" : "validateSMSCode"
			};
			var url = "${pageContext.request.contextPath}/UserServlet";
			$.post(url, sendData, function(a, b, ajax) {
				var msg = ajax.responseText;
				if (msg == "1") {
					$span7.attr("id", "1");
					$span7.text("√").css("color", "green");
					return;
				}else{
					$span7.attr("id", "0");
					$span7.text(msg).css("color", "red");
					return;
				}
			});

		}
		
	});
	
	
	$getTelCode.click(function() {
		var $own = $(this);
		$tel.blur();
		var flag = $span6.attr("id");
		if(flag==0){
			return;
		}
		var value = $tel.val();
		var sendData = {
				"tel" : value,
			};
			var url = "${pageContext.request.contextPath}/SendRegistSMS";
			$.post(url, sendData, function(a, b, ajax) {
				var msg = ajax.responseText;
				if(msg=="1"){
					$own.attr("disabled","disabled");
					var count = 60;
					var t = window.setInterval(function(){
						count--;
						$own.val("在"+count+"秒后可以重新获取验证码");
						if(count==0){
							$own.removeAttr("disabled");
							$own.val("获取验证码");
							window.clearInterval(t);
						}
					}, 1000);
					return;
				}
				else{
					$span7.attr("id", "0");
					$span7.text("手机验证码发送失败").css("color", "red");
					return;
				}
			});

		
	});
	
	$code.blur(function() {
		var value = $(this).val();
		var reg = /^[a-zA-Z_0-9\-@]{4}$/;
		value = $.trim(value);
		if (value == "") {
			$span8.attr("id", "0");
			$span8.text("验证码不能为空!").css("color", "red");
			return;
		} else if (!(reg.test(value))) {
			$span8.attr("id", "0");
			$span8.text("验证码必须为四位数字或字母").css("color", "red");
			return;
		} else {
			var sendData = {
				"img" : value,
				"methodName" : "validateImage"
			};
			var url = "${pageContext.request.contextPath}/UserServlet";
			$.post(url, sendData, function(a, b, ajax) {
				var msg = ajax.responseText;
				if (msg == "1") {
					$span8.attr("id", "1");
					$span8.text("√").css("color", "green");
					return;
				}else{
					$span8.attr("id", "0");
					$span8.text("验证码有误").css("color", "red");
					return;
				}
			});

		}
	});
	
	$regist
	.click(
			function() {
				var f1 = $span1.attr("id");
				var f2 = $span2.attr("id");
				var f3 = $span3.attr("id");
				var f4 = $span4.attr("id");
				var f5 = $span5.attr("id");
				var f6 = $span6.attr("id");
				var f7 = $span7.attr("id");
				var f8 = $span8.attr("id");
				
				if(f1==undefined || f1 == ""){
					$username.blur();
					f1 = $span1.attr("id");
				}
				if(f2==undefined || f2 == ""){
					$pwd.blur();
					f2 = $span2.attr("id");
				}
				if(f3==undefined || f3 == ""){
					$pwd2.blur();
					f3 = $span3.attr("id");
				}
				if(f4==undefined || f4 == ""){
					$pay_pwd.blur();
					f4 = $span4.attr("id");
				}
				if(f5==undefined || f5 == ""){
					$pay_pwd2.blur();
					f5 = $span5.attr("id");
				}
				if(f6==undefined || f6 == ""){
					$tel.blur();
					f6 = $span6.attr("id");
				}
				//if(f7==undefined || f7 == ""){
					$telCode.blur();
					f7 = $span7.attr("id");
				//}
				if(f8==undefined || f8 == ""){
					$code.blur();
					f8 = $span8.attr("id");
				}
				if (f1 == 1 && f2 == 1 && f3 == 1 && f4 == 1 && f5 == 1 && f6 == 1 && f7 == 1 && f8 ==1) {
				var sendData2 = $("form").serialize();
				window.location.href = "${pageContext.request.contextPath}/UserServlet?"+sendData2;
												
				}
			});
	
	</script>
</body>
</html>

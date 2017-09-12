$(function() {	
	/*
	 * 1. 给注册按钮添加submit()事件，完成表单校验
	 */
	$("#updatepwd").click(function(){
		var bool = true;
		$(".input").each(function() {
			var inputName = $(this).attr("name");
			if(!invokeValidateFunction(inputName)) {
				bool = false;
			}
		});
		if(bool){
			$("#pwdform").submit();
		}
//		return bool;
	});
	
	/*
	 * 3. 输入框推动焦点时进行校验
	 */
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidateFunction(inputName);
	});
	
	$(".input").focus(function() {
		var inputName = $(this).attr("name");
		$("#" + inputName + "Error").css("display", "none");
	});
});

/*
 * 输入input名称，调用对应的validate方法。
 * 例如input名称为：loginname，那么调用validateLoginname()方法。
 */
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");	
}

/*
 * 校验密码
 */
function validateLoginpass() {
	var bool = true;
	var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) {// 非空校验
		$("#loginpassError").css("display", "");
		$("#loginpassError").css("color", "#cc3300");
		$("#loginpassError").text("密码不能为空！");
		bool = false;
	} else if(!(reg.test(value))) {//长度校验
		$("#loginpassError").css("display", "");
		$("#loginpassError").css("color", "#cc3300");
		$("#loginpassError").text("密码必须以字母开头长度为3-20");
		bool = false;
	}else{
		$.ajax({
			cache: false,
			async: false,
			type: "POST",
			dataType: "text",
			data: {methodName: "validateUpdatePwd", loginpass: value},
			url: "/BookStore/UserServlet",
			success: function(flag) {
				if(flag != "1") {
					$("#loginpassError").css("display", "");
					$("#loginpassError").css("color", "#cc3300");
					$("#loginpassError").text(flag);
					bool = false;					
				}else if(flag == "1"){
					$("#loginpassError").css("display", "");
					$("#loginpassError").css("color", "green");
					$("#loginpassError").text("√");
				}
			}
		});
	};
	return bool;
}

// 校验新密码
function validateNewpass() {
	var bool = true;
	var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
	$("#newpassError").css("display", "none");
	var value = $("#newpass").val();
	value = $.trim(value);
	if(!value) {// 非空校验
		$("#newpassError").css("display", "");
		$("#newpassError").css("color", "#cc3300");
		$("#newpassError").text("新密码不能为空！");
		bool = false;
	} else if(!(reg.test(value))) {//长度校验
		$("#newpassError").css("display", "");
		$("#newpassError").css("color", "#cc3300");
		$("#newpassError").text("新密码必须以字母开头长度在3 ~ 20之间！");
		bool = false;
	}else{
		$("#newpassError").css("display", "");
		$("#newpassError").css("color", "green");
		$("#newpassError").text("√");
	}
	return bool;
}

/*
 * 校验确认密码
 */
function validateReloginpass() {
	var bool = true;	
	$("#reloginpassError").css("display", "none");
	var value = $("#reloginpass").val();
	value = $.trim(value);
	if(!value) {// 非空校验
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").css("color", "#cc3300");
		$("#reloginpassError").text("确认密码不能为空！");
		bool = false;
	} else if(value != $("#newpass").val()) {//两次输入是否一致
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").css("color", "#cc3300");
		$("#reloginpassError").text("两次密码输入不一致！");
		bool = false;
	}else{
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").css("color", "green");
		$("#reloginpassError").text("√");
	}
	return bool;	
}

/*
 * 校验验证码
 */
function validateVerifyCode() {
	var bool = true;
	$("#verifyCodeError").css("display", "none");
	var value = $("#verifyCode").val();
	value = $.trim(value);
	if(!value) {//非空校验
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").css("color", "#cc3300");
		$("#verifyCodeError").text("验证码不能为空！");
		bool = false;
	} else if(value.length != 4) {//长度不为4就是错误的
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").css("color", "#cc3300");
		$("#verifyCodeError").text("验证码长度有误");
		bool = false;
	} else {//验证码是否正确
		$.ajax({
			cache: false,
			async: false,
			type: "POST",
			dataType: "text",
			data: {methodName: "validateImage", img: value},
			url: "/BookStore/UserServlet",
			success: function(flag) {
				if(flag != "1") {
					$("#verifyCodeError").css("display", "");
					$("#verifyCodeError").css("color", "#cc3300");
					$("#verifyCodeError").text("错误的验证码！");
					bool = false;					
				}else if(flag == "1"){
					$("#verifyCodeError").css("display", "");
					$("#verifyCodeError").css("color", "green");
					$("#verifyCodeError").text("√");
				}
			}
		});
	}
	return bool;
}

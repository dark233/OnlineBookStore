 $(function() {	
		     checkCookie('backstage_username', 'backstage_password');
        	/*
        	 * 1. 给注册按钮添加submit()事件，完成表单校验
        	 */
        	$("#login").click(function(){
        		var bool = true;
        		$(".input").each(function() {
        			var inputName = $(this).attr("name");
        			if(!invokeValidateFunction(inputName)) {
        				bool = false;
        			}
        		});
        		if(bool){
        			$("#loginform").submit();
        		}
//        		return bool;
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
        	
        	function keyUp(event){
                if (event.keyCode === 13){
                	$("#login").click();
                }
        	};
        	document.onkeyup=keyUp;
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
        
        /**
         * 提交时验证
         */
        function validateFinalvalidate(){
        	var boo = true;
        	boo = invokeValidateFunction("username");
        	if(!boo){
        		return false;
        	}
        	boo = invokeValidateFunction("password");
        	if(!boo){
        		return false;
        	}
        	boo = invokeValidateFunction("verifyCode");
        	if(!boo){
        		return false;
        	}
        	var boo2 = true;
        	var username = $("#username").val();
        	var password = $("#password").val();
    		$.ajax({
    			cache: false,
    			async: false,
    			type: "POST",
    			dataType: "text",
    			data: {methodName: "validateLogin", username: username,password:password},
    			url: "/BookStore/backstage/BackStageUserServlet",
    			success: function(flag) {
    				if(flag != "1") {
    					$("#usernameError").css("display", "");
    					$("#usernameError").css("color", "#cc3300");
    					$("#usernameError").text(flag);
    					boo2 = false;					
    				}
    			}
    		});
    		return boo2;
        }
        /*
         * 校验用户名
         */
        function validateUsername() {
        	var bool = true;
        	var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
        	$("#usernameError").css("display", "none");
        	var value = $("#username").val();
        	if(!value) {// 非空校验
        		$("#usernameError").css("display", "");
        		$("#usernameError").css("color", "#cc3300");
        		$("#usernameError").text("用户名不能为空！");
        		bool = false;
        	} 
//        	else if(!(reg.test(value))) {//长度校验
//        		$("#usernameError").css("display", "");
//        		$("#usernameError").css("color", "#cc3300");
//        		$("#usernameError").text("用户名须以字母开头长度为3-20");
//        		bool = false;
//        	}
        	else{
        		$("#usernameError").css("display", "");
        		$("#usernameError").css("color", "green");
        		$("#usernameError").text("√");
        	}
        	return bool;
        }

        /*
         * 校验密码
         */
        function validatePassword() {
        	var bool = true;
        	var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
        	$("#passwordError").css("display", "none");
        	var value = $("#password").val();
        	if(!value) {// 非空校验
        		$("#passwordError").css("display", "");
        		$("#passwordError").css("color", "#cc3300");
        		$("#passwordError").text("密码不能为空！");
        		bool = false;
        	} else if(!(reg.test(value))) {//长度校验
        		$("#passwordError").css("display", "");
        		$("#passwordError").css("color", "#cc3300");
        		$("#passwordError").text("密码必须以字母开头长度为3-20");
        		bool = false;
        	}else{
        		$("#passwordError").css("display", "");
        		$("#passwordError").css("color", "green");
        		$("#passwordError").text("√");
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
        			url: "/BookStore/backstage/BackStageUserServlet",
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

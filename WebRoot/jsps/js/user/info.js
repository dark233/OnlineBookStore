$(function(){
	$(".updateUsername").click(updateUsername);
});

/**
 * 修改用户名验证
 */
var validateUsername = function(username){
			var reg = /^[a-zA-Z]{1}[a-zA-Z_0-9\-@]{2,19}$/;
			username = $.trim(username);
			if(!(reg.test(username))){
				return "用户名必须以字母开头，长度为3~20";
			}
			var msg = "error";
			$.ajax({
				async:false,//使用同步，为了使回调函数里的响应值能在函数外使用
				cache:false,
				url:"/BookStore/UserServlet",
				data:{methodName:"validateUsername",username:username},
				type:"POST",
				dataType:"text",
				success:function(result) {
					msg = result;
//					alert(msg);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
			        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
			        return "error";
			       }
			});
			return msg;
		
};

/**
 * 修改用户名
 */
var updateUsername = function(){
	var $updateUsernameBtn = $(this);
	var userid = $updateUsernameBtn.attr("id").substring(0, 18);
	hiPrompt('输入用户名', '', '修改用户名', function(v){
		if(v){
			var f = validateUsername(v);
			if(f == "1"){
				$.ajax({
					async:false,
					cache:false,
					url:"/BookStore/UserServlet",
					data:{methodName:"updateUsername",userid:userid,username:v},
					type:"POST",
					dataType:"json",
					success:function(result) {
						var errorCode = result.errorCode;
						if(errorCode == "1"){
							hiOverAlert("<span style='color:#00ff00;'>修改用户名成功</span>", 2000);
							window.history.go(0);
						}else{
							hiOverAlert("<span style='color:#c30;'>"+errorCode+"</span>", 1500);
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
				        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
				        return "error";
				       }
				});
			}else if(f == "0"){
				hiOverAlert("<span style='color:#c30;'>用户名已存在</span>", 1500);
			}else{
				hiOverAlert("<span style='color:#c30;'>"+f+"</span>", 1500);
			}
		}else{
			hiOverAlert("<span style='color:#c30;'>用户名不能为空</span>", 1500);
		}
	})
/*	 hiOverAlert("<span style='color:#00ff00;'>修改用户名成功</span>", 1500);
     hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 1500);*/


};
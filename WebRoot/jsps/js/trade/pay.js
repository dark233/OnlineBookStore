$(function() {
	$("img").click(function() {
		$("#" + $(this).attr("name")).attr("checked", true);
	});
	$("input[name='pay_way']").click(checkpayway);
	$(".linkNext").click(nextStep);
//	javascript:void $('#form1').submit();
});
var checkpayway = function(){
	var $this = $(this);
	var pay_way = $this.val();
	var $divMypay = $(".divMypay");
	var $divBank = $(".divBank");
	$divMypay.css("display","none");
	$divBank.css("display","none");
	if(pay_way == "pw-01"){
		$divMypay.css("display","block");
	}else if(pay_way == "pw-02"){
		$divBank.css("display","block");
	}
};
var nextStep = function(){
	var payway = $("input[name='pay_way']:checked").val();
	var $pay_pwd = $("#pay_pwd");
	var flag = false;
	if(payway == "pw-01"){
		var pay_pwd = $pay_pwd.val();
		pay_pwd = $.trim(pay_pwd);
		if(pay_pwd == null || pay_pwd == ""){
			$("#pay_pwd~span").remove();
			$pay_pwd.after("<span style=\"color:#c30\">支付密码不能为空</span>");
		}else{
			var result = vilidatePaypwd();
			if(result == "pe-01"){
				flag = true;
			}
		}
	}else if(payway == "pw-02"){
		flag = true;
	}
	if(flag){
		$('#form1').submit();
	}
};
/**
 * 使用余额支付时验证账户支付密码及余额
 */
var vilidatePaypwd = function(){
	var $pay_pwd = $("#pay_pwd");
	var pay_pwd = $pay_pwd.val();
	var url = vilidatePaypwdUrl;
	var tradeid = $("input[name='tradeid']").val();
	var msg = "error";
	$.ajax({
		async:false,
		cache:false,
		url:url,
		data:{methodName:"vilidatePaypwd",pay_pwd:pay_pwd,tradeid:tradeid},
		type:"POST",
		dataType:"text",
		success:function(result) {
			msg = result;
			if(result == "pe-02"){
				$("#pay_pwd~span").remove();
				$pay_pwd.after("<span style=\"color:#c30\">支付密码有误</span>");
			}else if(result == "pe-03"){
				$("#pay_pwd~span").remove();
				$pay_pwd.after("<span style=\"color:#c30\">余额不足<a href=\"\">立即充值</a></span>");
			}else if(result == "pw-04"){
				$("#pay_pwd~span").remove();
				$pay_pwd.after("<span style=\"color:#c30\">支付密码为空</span>");
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$pay_pwd.after("<span style=\"color:#c30\">error</span>");
		}
	});
	return msg;
};
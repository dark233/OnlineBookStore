$(function() {
	//对图书封面图片上的超链接进行url编码
	var $linkTitle = $("a.linkTitle");
	$linkTitle.each(function(){
		loadHref(linkUrl, $(this));
	});
	$(".cancelBtn").click(cancel);
	$(".confirmBtn").click(confirm);
	$(".cancelRefundBtn").click(cancelRefund);
	
	if(tradeStatus == "1" || tradeStatus == "3"){
		FreshTime();
		sh=setInterval(FreshTime,1000);
	}

});




/**
 * 倒计时
 */
function FreshTime()
{
	var endtime;
	if(tradeStatus == "1"){
		endtime = new Date(Date.parse(payExpirtyTime.replace(/-/g, "/")));
	}else if(tradeStatus == "3"){
		endtime = new Date(Date.parse(confirmReceiptExpirtyTime.replace(/-/g, "/")));
	}
    var nowtime = new Date();//当前时间
    var lefttime=parseInt((endtime.getTime()-nowtime.getTime())/1000); 
    d=parseInt(lefttime/3600/24);
    h=parseInt((lefttime/3600)%24);
    m=parseInt((lefttime/60)%60);
    s=parseInt(lefttime%60);
    
	if(tradeStatus == "1"){
//		alert(lefttime);
	    if(lefttime<=0){
	    	clearInterval(sh);
	        var href = tradeServletUrl+"?methodName=cancelAuto&tradeid="+tradeid;
	        window.location.href= href;
	        return;
	    }
		$("#leftTime").text("剩余支付时间：" + d+"天"+h+"小时"+m+"分"+s+"秒");
		
	}else if(tradeStatus == "3"){
//		alert(lefttime);
	    if(lefttime<=0){
	    	clearInterval(sh);
	        var href = tradeServletUrl+"?methodName=confirmAuto&tradeid="+tradeid;
	        window.location.href= href;
	        return;
	    }
		$("#leftTime").text("剩余确认收货时间：" + d+"天"+h+"小时"+m+"分"+s+"秒");
	}

}

/**
 * 取消订单验证
 */
var validateCancel = function(tradeid){
			var url = tradeServletUrl;
			var msg = "error";
			$.ajax({
				async:false,//使用同步，为了使回调函数里的响应值能在函数外使用
				cache:false,
				url:url,
				data:{methodName:"vilidateCancel",tradeid:tradeid},
				type:"POST",
				dataType:"json",
				success:function(result) {
					msg = result.errorCode;
//					alert(msg);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
			        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
			       }
			});
			return msg;
		
};

/**
 * 取消订单
 */
var cancel = function(){
	var url = tradeServletUrl;
	var $calcelBtn = $(this);
	var tradeid = $calcelBtn.attr("id").substring(0, 22);
	qikoo.dialog.confirm('确认取消订单吗？',
			function(){
					var msg = validateCancel(tradeid);
					if("1" == msg){
						$.ajax({
							async:true,//使用同步，为了使回调函数里的响应值能在函数外使用
							cache:false,
							url:url,
							data:{methodName:"cancel",tradeid:tradeid},
							type:"POST",
							dataType:"json",
							success:function(result) {
								var errorCode = result.errorCode;
								if("1" == errorCode){
									hiOverAlert("<span style='color:#00ff00;'>取消订单成功</span>", 2000);
									window.history.go(0);
								}else{
							        hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 2000);
							        window.history.go(0);
								}
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
						        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
						       }
						});
					}else{
				        hiOverAlert("<span style='color:#c30;'>"+msg+"</span>", 1500);
					}

			},
			function(){
				hiOverAlert('取消', 1500);
			}
			);
};

/**
 * 确认收货验证
 */
var validateConfirm = function(tradeid,pay_pwd){
			var url = tradeServletUrl;
			var msg = "error";
			$.ajax({
				async:false,//使用同步，为了使回调函数里的响应值能在函数外使用
				cache:false,
				url:url,
				data:{methodName:"vilidateConfirm",tradeid:tradeid,pay_pwd:pay_pwd},
				type:"POST",
				dataType:"json",
				success:function(result) {
					msg = result.errorCode;
//					alert(msg);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
			        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
			       }
			});
			return msg;
		
};

/**
 * 确认收货
 */
var confirm = function(){
	var url = tradeServletUrl;
	var $confirmBtn = $(this);
	var tradeid = $confirmBtn.attr("id").substring(0, 22);
	
	hiPrompt('请输入支付密码:', '', '支付密码', function(r) {
		if(r){
			var msg = validateConfirm(tradeid,r);
			if("1" == msg){
				$.ajax({
					async:false,
					cache:false,
					url:url,
					data:{methodName:"confirm",tradeid:tradeid},
					type:"POST",
					dataType:"json",
					success:function(result) {
						var errorCode = result.errorCode;
						if("1" == errorCode){
							hiOverAlert("<span style='color:#00ff00;'>支付成功</span>", 2000);
							window.history.go(0);
						}else{
					        hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 2000);
					        window.history.go(0);
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
				        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
				       }
				});
			}else{
		        hiOverAlert("<span style='color:#c30;'>"+msg+"</span>", 1500);
			}
		}else{
			hiOverAlert("<span style='color:#c30;'>支付密码为空</span>", 1500);
		}
	});
	
};

/**
 * 取消退款验证
 */
var validateCancelRefund = function(tradeid){
			var url = tradeServletUrl;
			var msg = "error";
			$.ajax({
				async:false,//使用同步，为了使回调函数里的响应值能在函数外使用
				cache:false,
				url:url,
				data:{methodName:"vilidateCancelRefund",tradeid:tradeid},
				type:"POST",
				dataType:"json",
				success:function(result) {
					msg = result.errorCode;
//					alert(msg);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
			        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
			       }
			});
			return msg;
		
};

/**
 * 取消退款
 */
var cancelRefund = function(){
	var url = tradeServletUrl;
	var $calcelRefundBtn = $(this);
	var tradeid = $calcelRefundBtn.attr("id").substring(0, 22);
	qikoo.dialog.confirm('确认取消退款吗？',
			function(){
					var msg = validateCancelRefund(tradeid);
					if("1" == msg){
						$.ajax({
							async:true,//使用同步，为了使回调函数里的响应值能在函数外使用
							cache:false,
							url:url,
							data:{methodName:"cancelRefund",tradeid:tradeid},
							type:"POST",
							dataType:"json",
							success:function(result) {
								var errorCode = result.errorCode;
								if("1" == errorCode){
									hiOverAlert("<span style='color:#00ff00;'>取消退款成功</span>", 2000);
									window.history.go(0);
								}else{
							        hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 2000);
							        window.history.go(0);
								}
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
						        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
						       }
						});
					}else{
				        hiOverAlert("<span style='color:#c30;'>"+msg+"</span>", 1500);
					}

			},
			function(){
				hiOverAlert('取消', 1500);
			}
			);
};
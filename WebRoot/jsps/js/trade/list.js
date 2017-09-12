$(function() {
	//对图书封面图片上的超链接进行url编码
	var $linkImage = $("a.link2");
	$linkImage.each(function(){
		loadHref(linkUrl, $(this));
	});
	$(".cancelBtn").click(cancel);
	$(".confirmBtn").click(confirm);
	$(".cancelRefundBtn").click(cancelRefund);
});

var loadSwitch = function(tradeStatus,tradeid,$tradeStatus,$operate){
	$operate.empty();
	$operate.append("<a href="+tradeServletUrl+"?methodName=load&tradeid="+tradeid+">查看</a><br/>");
	switch (tradeStatus) {
	case 1:
		$tradeStatus.text("(等待付款)");
		$operate.append("<a href="+tradeServletUrl+"?methodName=paymentPre&tradeid="+tradeid+">支付</a><br/>");
		$operate.append("<a class = cancelBtn id="+tradeid+"Cancel>取消</a><br/>	");			
		break;
	case 2:
		$tradeStatus.text("(等待发货)");
		$operate.append("<a href="+tradeServletUrl+"?methodName=refundPre&tradeid="+tradeid+">退款</a><br/>");
		break;
	case 3:
		$tradeStatus.text("(等待收货)");
		$operate.append("<a class = confirmBtn id="+tradeid+"Confirm>确认收货</a><br/>	");			
		break;
	case 4:
		$tradeStatus.text("(交易成功)");
		$operate.append("<a href="+tradeServletUrl+"?methodName=remarkPre&tradeid="+tradeid+">评价</a><br/>");
		break;
		break;
	case 5:
		$tradeStatus.text("(已取消)");
		break;
	case 6:
		$tradeStatus.text("(退款中)");
		$operate.append("<a class = cancelRefundBtn id="+tradeid+"CancelRefund>取消退款</a><br/>	");			
		break;
	case 7:
		$tradeStatus.text("(退款成功)");
		break;
	case 8:
		$tradeStatus.text("(交易成功)");
	default:
		break;
	}

};

/**
 * 重新加载订单
 */
var loadOneTrade = function(tradeStatus,tradeid,handle){
	var $tradeStatus = $("#"+tradeid+"TradeStatus");
	var $operate = $("#"+tradeid+"Operate");
	if(handle){
		loadSwitch(tradeStatus, tradeid, $tradeStatus, $operate);
		if(handle.isValid == 2){
			$tradeStatus.text("(取消退款)");
		}else if(handle.status_ == 2){
			$tradeStatus.text("(退款中)");
		}else if(handle.refundRecords.status_ == 2){
			$tradeStatus.text("(退款被拒)");
		}else if(handle.refundRecords.status_ == 3){
			$tradeStatus.text("(退款成功)");
		}
	}else{
		loadSwitch(tradeStatus, tradeid, $tradeStatus, $operate);
	}

};

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
									loadOneTrade(result.tradeStatus, tradeid, null);
									 hiOverAlert("<span style='color:#00ff00;'>取消订单成功</span>", 1500);
								}else{
							        hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 1500);
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
							loadOneTrade(result.tradeStatus, tradeid, null);
							 hiOverAlert("<span style='color:#00ff00;'>支付成功</span>", 1500);
						}else{
					        hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 1500);
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
									loadOneTrade(result.tradeStatus, tradeid, result.handle);
									 hiOverAlert("<span style='color:#00ff00;'>取消退款成功</span>", 1500);
								}else{
							        hiOverAlert("<span style='color:#c30;'>"+result.errorCode+"</span>", 1500);
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
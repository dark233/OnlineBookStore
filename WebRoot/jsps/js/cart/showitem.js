$(function() {
	setTotal();
	$("#linkSubmit").click(jiesuan);
	var $linkImage = $(".linkImage");
	var $linkTitle = $(".linkTitle");
	$linkImage.each(loadHref);
	$linkTitle.each(loadHref);
	
});

/*
 * 对超链接url地址进行URL编码后重新赋值
 */
var loadHref = function(){
	var $this = $(this);
	var href = $this.attr("href");
	var encodeHref = encodeURIComponent(href);
	var finalUrl = linkBookDescUrl+"?openUrl="+encodeHref;
	$this.attr("href",finalUrl);
};

/*
 * 结算前校验
 */
var validateShoppingCartWhenCash = function(){
	var msg = "error";
	var url = cartUrl;
	$.ajax({
		async:false,
		cache:false,
		url:url,
		data:{methodName:"validateShoppingCartWhenCash"},
		type:"POST",
		dataType:"text",
		success:function(result) {
			msg = result;
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
	        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
	       }
	});
	return msg;
}
/*
 * 结算
 */
var jiesuan = function(){
	var result = validateShoppingCartWhenCash();
	if(result == "1"){
		var $shipAddr = $("input:[name='shippingAddress']:checked");
		var shipAddrid = $shipAddr.val();
		var url = tradeUrl+"?methodName=createTrade&shipAddrid="+shipAddrid;
		window.location.href=url;
	}else{
		qikoo.dialog.alert(result,function(){
//			window.location.href=window.location.href;
//			window.location.reload();
			window.history.go(0);
		});
	}
}
/*
 * 计算总金额
 */
var setTotal = function(){
	var total = 0;
	$(".subtotal").each(function() {
		total += Number($(this).text());
	});
	$("#total").text(total);
}


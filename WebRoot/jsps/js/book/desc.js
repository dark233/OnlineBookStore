$(function() {
//	$("#cnt").blur(function() {
//		var quantity = $("#cnt").val();
//		if(!/^[1-9]\d*$/.test(quantity)) {
//			alert("数量必须是合法整数！");
//			$("#cnt").val("1");
//		}
//	});

	/*
	 * 绑定加入购物车点击事件
	 */
	$("#btn").click(function(){
		
		if(username == null || username == ""){
			window.top.location.href=loginUrl;
			return;
		}
		
		$.post(validateAddToShoppingCartUrl,formdata2,function(a, b, ajax) {
			var msg1 = ajax.responseText;
			if (msg1 != "1") {
				qikoo.dialog.alert(msg1);
				/*
				$("#addToCartResult").text(msg);
				$("#addToCartResult").css("color", "red");*/
				return;
			}else if(msg1 == "1"){
				
				$.post(addToCartUrl,formdata,function(a, b, ajax) {
					var msg2 = ajax.responseText;
					if (msg2 != "1") {
						qikoo.dialog.alert(msg2);
						/*$("#addToCartResult").text(msg);
						$("#addToCartResult").css("color", "red");*/
						return;
					}else if(msg2 == "1"){
						
						qikoo.dialog.addToCartNotice(function(){
						},function(){
							window.top.location.href=cartUrl;
						},function(){
						});
						
						/*$("#addToCartResult").text("添加到购物车成功");
						$("#addToCartResult").css("color", "green");*/
						return;
					}
				});
				
				return;
			}
		});

	});
	/**
	 * 无货或下架修改加入购物车图标为灰色，并解绑点击事件
	 */
	var isShow2 = Number(isShow);
	var storeNumber2 = Number(storeNumber);
	if(isShow2 == 2 || storeNumber2 <=0){
		$("#btn").unbind("click");
		$("#btn").css("background-position","0 -142px");
	}
});
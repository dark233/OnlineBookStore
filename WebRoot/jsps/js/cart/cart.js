$(function() {
		var $linkImage = $(".linkImage");
		var $linkTitle = $(".linkTitle");
		$linkImage.each(loadHref);
		$linkTitle.each(loadHref);

	/*
	给全选添加click事件
	*/
	$("#selectAll").click(function() {
		/*
		1. 获取全选的状态
		*/
		var bool = $("#selectAll").attr("checked");
		/*
		2. 让所有条目的复选框与全选的状态同步
		*/
		setItemCheckBox(bool);
		isSelectAll(false);
		/*
		 * 后台isChecked字段与前台同步
		 */
		isCheckedByAll(bool);
		/*
		4. 让结算按钮与全选同步
		*/
//		setJieSuan(bool);
		/*
		5. 重新计算总计
		*/
		showTotal();
	});

	/*
	给所有条目的复选框添加click事件
	*/
	$(":checkbox[name=checkboxBtn]").click(function() {
		var $this = $(this);
		var isChecked = $this.attr("checked");
		var itemid = $this.val();
		var quantity = $this.parent().parent().find("#"+itemid+"Quantity").val();
		isCheckedByOne(itemid,quantity,isChecked);
		isSelectAll(false);//进行全选按钮选定状态选择
		showTotal();//重新计算总计
	});

	/*
	给减号添加click事件
	*/
	$(".jian").click(jianclick);

	// 给加号添加click事件
	$(".jia").click(jiaclick);
	
	/* 
	 * 删除一条购物车项
	 */
	$(".deleteOne").click(deleteOneClick); 
	
	loadCartItem();//加载购物车项的样式，因为要解绑事件，所以要在事件绑定完之后再判断进行操作
	showTotal();//计算总计
	isSelectAll(false);//判断全选按钮是否应该选定
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
 * 用于绑定到jia的点击事件上的函数
*/
var jianclick = function() {
	var $this = $(this);
	var $cartItem = $this.parent().parent();
	// 获取cartItemId
	var id = $this.attr("id").substring(0, 24);
	// 获取输入框中的数量
	var quantity = $("#" + id + "Quantity").val();
	// 判断当前数量是否为1，如果为1,那就不是修改数量了，而是要删除了。
	if(quantity == "1") {
		$cartItem.find(".deleteOne").click();
	}else{
//		var result = validateShoppingCartItem(id, Number(quantity)-1);
		sendUpdateQuantity(id, Number(quantity)-1);
	}

};

/*
 * 用于绑定到jian点击事件上的函数
 */

var jiaclick = function() {
	// 获取cartItemId
	var id = $(this).attr("id").substring(0, 24);
	// 获取输入框中的数量
	var quantity = $("#" + id + "Quantity").val();
	
	//只有增加数量进行校验购物车项合法性
	var result2 = validateShoppingCartItem(id, Number(quantity)+1);
	if("1" == result2){
		sendUpdateQuantity(id, Number(quantity)+1);
	}else{
		hiOverAlert("<span style='color:#c30;'>"+result2+"</span>", 1500);
	}
};
/*
 * 用于绑定到单项购物车项删除点击事件上的函数
 */
var deleteOneClick = function(){
	var $this = $(this);
	var $checkboxBtn = $this.parent().parent().find("input:[name='checkboxBtn']");
	var itemid = $checkboxBtn.val();
	var url = cartUrl;
	var formdata = {"methodName":"deleteShoppingCartItem","shoppingCartItemid":itemid};
	qikoo.dialog.confirm('确认删除该书吗？',
			function(){
				$.post(url,formdata,function(a, b, ajax) {
					var msg = ajax.responseText;
					if(msg != "1"){
						hiOverAlert("<span style='color:#00ff00;'>"+msg+"</span>", 1500);
					}else if(msg == "1"){
						$this.parent().parent().remove();
						isSelectAll(true);
						showTotal();
						hiOverAlert("<span style='color:#00ff00;'>删除成功</span>", 1500);
					}
				});
			},
			function(){
				hiOverAlert('取消', 1500);
			}
			);
	
};

/*
 * 单选按钮点击绑定方法之一，与后台isChecked字段同步
 */
var isCheckedByOne = function(itemid,quantity,isChecked){
	if(isChecked){
		var result2 = validateShoppingCartItem(itemid, quantity);
		if(result2 == "1"){
			forIsCheckedByOne(itemid,isChecked);
		}else{
			hiOverAlert("<span style='color:#c30;'>"+result2+"</span>", 1500);
		}
	}else{
		forIsCheckedByOne(itemid,isChecked);
	}

}

var forIsCheckedByOne = function (shoppingCartItemid,isChecked){
	var url = cartUrl;
	$.ajax({
		async:true,
		cache:false,
		url:url,
		data:{methodName:"setIsCheckedInShoppingCartItem",shoppingCartItemid:shoppingCartItemid,isChecked:isChecked},
		type:"POST",
		dataType:"text",
		success:function(result) {
			if(result != "1"){
				hiOverAlert("<span style='color:#c30;'>"+result+"</span>", 1500);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
//			throw errorThrown;
			
		}
	});
}
/*
 * 全选按钮点击事件绑定方法之一，与后台isChecked字段同步
 */
var isCheckedByAll = function(isChecked){
	var url = cartUrl;
	$.ajax({
		async:true,
		cache:false,
		url:url,
		data:{methodName:"setIsCheckedInShoppingCart",isChecked:isChecked},
		type:"POST",
		dataType:"text",
		success:function(result) {
			if(result != "1"){
				hiOverAlert("<span style='color:#c30;'>"+result+"</span>", 1500);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
//				throw errorThrown;
			
		}
	});
	
}

/*
 * 加载单项购物车项样式
 */
var loadOneCartItem = function(cartItem,itemid){
	var $cartItem = $("#"+itemid+"Tr");
	var $status = $cartItem.find("#"+itemid+"Status");
	var book = cartItem.book;
	var isShow = book.isShow;
	var storeNumber = book.storeNumber;
	var quantity = cartItem.quantity;
	if(2 == isShow || storeNumber <= 0){
		var $a = $cartItem.find("a");
		$cartItem.css({"filter":"alpha(opacity=30)","font-color":"#d0d0d0"});//给购物车项罩上灰色
		$a.attr("href","#");
		$a.removeAttr("style");
		$cartItem.find(".jian").unbind("click",jianclick);
		$cartItem.find(".jia").unbind("click");
		if(2 == isShow){
			$cartItem.find("input:[name='checkboxBtn']").remove();
			$cartItem.find("#"+itemid+"Quantity").parent().empty();
			$status.empty().append("<span style=\"color: #d0d0d0;font-size: 10pt;\">已下架</span>");
		}else if(storeNumber <= 0){
			var $checkboxBtn = $cartItem.find("input:[name='checkboxBtn']");
			$checkboxBtn.attr("disabled",true);
			$checkboxBtn.attr("checked",false);
			$status.empty().append("<span style=\"color: #c30;font-size: 10pt;\">暂时无货</span>");
		}
	}else if(quantity > storeNumber){
//		var $checkboxBtn = $cartItem.find("input:[name='checkboxBtn']");
//		$checkboxBtn.attr("checked",false);
		$status.empty().append("<span style=\"color: #c30;font-size: 10pt;\">库存不足</span>");
	}else{
		$status.empty();
	}
};

	/*
	* 加载所有购物车项的样式
	*/
var loadCartItem = function(){
		var $cartItem = $(".cartItem");
		$cartItem.each(function(){
			var $this=$(this);
			var $isShow = $this.find(".isShow");
			var $storeNumber = $this.find(".storeNumber");
			var isShow = Number($isShow.val());
			var storeNumber = Number($storeNumber.val());
			var itemid = $this.attr("id").substring(0, 24);
			if(2 == isShow || storeNumber <= 0){
				var $a = $this.find("a");
				$this.css({"filter":"alpha(opacity=30)","font-color":"#d0d0d0"});//给购物车项罩上灰色
				$a.attr("href","#");
				$a.removeAttr("style");
				$this.find(".jian").unbind("click",jianclick);
				$this.find(".jia").unbind("click");
				if(isShow == 2){
					$this.find("input:[name='checkboxBtn']").remove();
					$this.find("#"+itemid+"Quantity").parent().empty();
				}else if(storeNumber <= 0){
					var $checkboxBtn = $this.find("input:[name='checkboxBtn']");
					$checkboxBtn.attr("disabled",true);
					$checkboxBtn.attr("checked",false);
				}
			}
		});
	}
	/* 
	全选按钮是否选定判定方法
	*/
	function isSelectAll(isReloadable){
		var all = $(".cartItem").length;
		var allable = $(":checkbox[name=checkboxBtn]:not([disabled])").length;//所有有多选框且多选框为可选的条目的个数
		var select = $(":checkbox[name=checkboxBtn]:not([disabled])[checked=true]").length;//获取所有被选择条目的个数
		if(allable == select && allable !=0) {//全都选中了
			$("#selectAll").attr("checked", true);//勾选全选复选框
			setJieSuan(true);//让结算按钮有效
		} else if(select == 0 && all != 0) {//谁都没有选中
			$("#selectAll").attr("checked", false);//取消全选
			setJieSuan(false);//让结算失效
		} else if(select > 0){
			$("#selectAll").attr("checked", false);//取消全选
			setJieSuan(true);//让结算有效				
		}else if(isReloadable){
			window.location.href = window.location.href;//购物车为空且可以需要刷洗时刷新页面
			window.location.reload();
		}
	}


	// 请求服务器，修改数量。
	function sendUpdateQuantity(itemid, quantity) {
		var url = cartUrl;
		$.ajax({
			async:true,
			cache:false,
			url:url,
			data:{methodName:"changeShoppingCartItemQuantity",shoppingCartItemid:itemid,quantity:quantity},
			type:"POST",
			dataType:"json",
			success:function(result) {
				if(result.statusCode == "failed"){
		 			hiOverAlert("<span style='color:#c30;'>修改数量失败</span>",1500);
		 		}else{
		 			$("#" + itemid + "Quantity").val(result.quantity);
		 			$("#" + itemid + "Subtotal").text(result.subtotal);
		 			forIsCheckedByOne(itemid, true);
		 			$("#" + itemid + "Quantity").parent().parent().find(":checkbox[name=checkboxBtn]").attr("checked",true);
		 			if(result.quantity <= result.storeNumber){
		 				$("#"+itemid+"Status").empty();
//		 				var statusDom = $status[0];
//		 				if(statusDom){
//		 					$status.remove();
//		 				}
		 			}
		 			isSelectAll(false);
		 			showTotal();
		 			hiOverAlert("<span style='color:#00ff00;'>修改数量成功</span>", 1500);
		 		}
			}
		});
		
			/*var formdata = {"methodName":"changeShoppingCartItemQuantity","shoppingCartItemid":itemid,"quantity":quantity};
		 	$.post(url,formdata,function(a, b, ajax) {
		 		var msg = ajax.responseText;
		 		if(msg == "0"){
		 			hiOverAlert('修改数量失败',1500);
		 		}else{
		 			var result = eval("("+msg+")");
		 			$("#" + itemid + "Quantity").val(result.quantity);
		 			$("#" + itemid + "Subtotal").text(result.subtotal);
		 			showTotal();
		 			var $operate = $("#"+itemid+"Operate");
		 			var $span = $operate.find("span:contains('库存不足')");
		 			if($span){
		 				if(result.quantity>result.storeNumber){
		 					
		 				}else{
		 					$span.remove();
		 				}
		 				$operate.append("<a class = 'deleteOne'  href=''#''>删除</a>");
		 			}
		 			hiOverAlert("<span style='color:#00ff00;'>修改数量成功</span>", 1500);
		 		}
			}); */

		
	}
	/*
	 * 校验购物车项 合法性
	 */
	function validateShoppingCartItem(itemid,quantity){
/*		var url = cartUrl;
		var formdata = {"methodName":"validateShoppingCartItem","bookid":bookid};
		var msg = null;
		$.post(url,formdata,function(a, b, ajax) {
			//有可能msg在这里赋值不了,异步的 原因
			 msg = ajax.responseText;
		});*/
		//
		var url = cartUrl;
		var msg = "error";
		$.ajax({
			async:false,//使用同步，为了使回调函数里的响应值能在函数外使用
			cache:false,
			url:url,
			data:{methodName:"validateShoppingCartItem",shoppingCartItemid:itemid,quantity:quantity},
			type:"POST",
			dataType:"json",
			success:function(result) {
				msg = result.statusCode;
//				alert(msg);
				if(msg != "1"){
					loadOneCartItem(result, itemid);
					showTotal();//计算总计
					isSelectAll(false);//判断全选按钮是否应该选定
				}
				
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
		        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
		       }
		});
		return msg;
	}

	/*
	 * 计算总计
	 */
	function showTotal() {
		var total = 0;
		/*
		1. 获取所有的被勾选的条目复选框！循环遍历之
		*/
		$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
			//2. 获取复选框的值，即其他元素的前缀
			var id = $(this).val();
			//3. 再通过前缀找到小计元素，获取其文本
			var text = $("#" + id + "Subtotal").text();
			//4. 累加计算
			total += Number(text);
		});
		// 5. 把总计显示在总计元素上
		$("#total").text(round(total, 2));//round()函数的作用是把total保留2位
	}

	/*
	 * 统一设置所有条目的复选按钮
	 */
	function setItemCheckBox(bool) {
		$(":checkbox[name=checkboxBtn]:not([disabled])").attr("checked", bool);
	}


	/*
	 * 批量删除
	 */
	function batchDelete() {
		// 1. 获取所有被选中条目的复选框
		// 2. 创建一数组，把所有被选中的复选框的值添加到数组中
		// 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
//		var cartItemIdArray = new Array();
//		$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
//			cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
//		});
//		location = "/BookStore/ShoppingCartServlet?methodName=deleteShoppingCartItems&cartItemIds=" + cartItemIdArray;
		var url = cartUrl;
		$.ajax({
			async:false,
			cache:false,
			url:url,
			data:{methodName:"deleteShoppingCartItems"},
			type:"POST",
			dataType:"text",
			success:function(result) {
//				alert(msg);
				if(result != "1"){
			        hiOverAlert("<span style='color:#c30;'>"+result+"</span>", 1500);
				}else{
					window.location.href=window.location.href;
					window.location.reload();
				}
				
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
		        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
		       }
		});
	}
	
	/*
	 * 设置结算按钮样式
	 */
	function setJieSuan(bool) {
		if(bool) {
			$("#jiesuan").removeClass("kill").addClass("jiesuan");
			$("#jiesuan").unbind("click");//撤消当前元素止所有click事件
		} else {
			$("#jiesuan").removeClass("jiesuan").addClass("kill");
			$("#jiesuan").click(function() {return false;});
		}
		
	}

	/*
	 * 结算
	 */
	function jiesuan() {
		// 1. 获取所有被选择的条目的id，放到数组中
//		var cartItemIdArray = new Array();
//		$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
//			cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
//		});	
		// 2. 把数组的值toString()，然后赋给表单的cartItemIds这个hidden
//		$("#cartItemIds").val(cartItemIdArray.toString());
		// 把总计的值，也保存到表单中
//		$("#hiddenTotal").val($("#total").text());
		// 3. 提交这个表单
//		$("#jieSuanForm").submit();
		
		var result = validateShoppingCartWhenCash();
		if(result == "1"){
			var url = cartUrl+"?methodName=findPreparCashShoppingCart";
			window.location.href=url;
		}else{
//			hiOverAlert("<span style='color:#c30;'>"+result+"</span>", 2000);
			qikoo.dialog.alert(result,function(){
				window.location.href=window.location.href;
				window.location.reload();
			});
//			setTimeout(function(){
//				window.location.href=window.location.href;
//				window.location.reload();
//			}, 3000);

		}
		
	}
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
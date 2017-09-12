$(function () {
	$("#publishingDate").datepick({dateFormat:"yy-mm-dd"});
	$("#printDate").datepick({dateFormat:"yy-mm-dd"});
	$("#btn").addClass("btn1");
	$("#btn").hover(
		function() {
			$("#btn").removeClass("btn1");
			$("#btn").addClass("btn2");
		},
		function() {
			$("#btn").removeClass("btn2");
			$("#btn").addClass("btn1");
		}
	);
	
	$("#btn").click(function() {
		var bname = $("#bname").val();
		var currPrice = $("#currPrice").val();
		var price = $("#price").val();
		var storeNumber = $("#storeNumber");
		var discount = $("#discount").val();
		var author = $("#author").val();
		var press = $("#press").val();
		var pid = $("#pid").val();
		var cid = $("#cid").val();
		var imgcacheurl = $("#imgcacheurl").val();
		
		if(!bname || !currPrice || !price || !discount || !author || !press || !pid || !cid || !imgcacheurl || !storeNumber) {
			qikoo.dialog.alert("图名、当前价、定价、折扣、图书库存、作者、出版社、1级分类、2级分类、图书封面都不能为空！");
			return false;
		}
		
		if(isNaN(currPrice) || isNaN(price) || isNaN(discount)) {
			qikoo.dialog.alert("当前价、定价、折扣必须是合法小数！");
			return false;
		}
		$("#form").submit();
	});
	/**
	 * 设置折扣后当前价格
	 */
	$("#price").blur(setCurrPrice);
	$("#discount").blur(setCurrPrice);
});

function loadChildren() {
	/*
	1. 获取pid
	2. 发出异步请求，功能之：
	  3. 得到一个数组
	  4. 获取cid元素(<select>)，把内部的<option>全部删除
	  5. 添加一个头（<option>请选择2级分类</option>）
	  6. 循环数组，把数组中每个对象转换成一个<option>添加到cid中
	*/
	// 1. 获取pid
	var pid = $("#pid").val();
	// 2. 发送异步请求
	$.ajax({
		async:true,
		cache:false,
		url:"/BookStore/backstage/BookServlet",
		data:{methodName:"findChildrenByParent", pid:pid},
		type:"POST",
		dataType:"json",
		success:function(arr) {
			// 3. 得到cid，删除它的内容
			$("#cid").empty();//删除元素的子元素
			$("#cid").append($("<option>====请选择2级分类====</option>"));//4.添加头
			// 5. 循环遍历数组，把每个对象转换成<option>添加到cid中
			for(var i = 0; i < arr.length; i++) {
				var option = $("<option>").val(arr[i].cid).text(arr[i].cname);
				$("#cid").append(option);
			}
		}
	});
}
	/**
	 * 设置折扣后当前价格
	 */
	var setCurrPrice = function(){
		var $currPrice = $("#currPrice");
		var $price = $("#price");
		var $discount = $("#discount");
		var price = $.trim($price.val());
		var discount = $.trim($discount.val());
		discount = discount/10;
		if(price && discount){
			$currPrice.val(Number(price)*Number(discount));
		}
		var kk = Number(price)*Number(discount);
	};

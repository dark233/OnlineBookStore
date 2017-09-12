$(function () {
	// 日期框
	$("#publishingDate").datepick({dateFormat:"yy-mm-dd"});
	$("#printDate").datepick({dateFormat:"yy-mm-dd"});

	// 编辑和删除按钮样式
	$("#editBtn").addClass("editBtn1");
	$("#delBtn").addClass("delBtn1");
	$("#putAwayAgainBtn").addClass("delBtn1");
	$("#editBtn").hover(
		function() {
			$("#editBtn").removeClass("editBtn1");
			$("#editBtn").addClass("editBtn2");
		},
		function() {
			$("#editBtn").removeClass("editBtn2");
			$("#editBtn").addClass("editBtn1");
		}
	);
	$("#delBtn").hover(
		function() {
			$("#delBtn").removeClass("delBtn1");
			$("#delBtn").addClass("delBtn2");
		},
		function() {
			$("#delBtn").removeClass("delBtn2");
			$("#delBtn").addClass("delBtn1");
		}
	);
	$("#putAwayAgainBtn").hover(
			function() {
				$("#putAwayAgainBtn").removeClass("delBtn1");
				$("#putAwayAgainBtn").addClass("delBtn2");
			},
			function() {
				$("#putAwayAgainBtn").removeClass("delBtn2");
				$("#putAwayAgainBtn").addClass("delBtn1");
			}
	);
	
	$("#box").attr("checked", false);
	$("#formDiv").css("display", "none");
	$("#show").css("display", "");	
	
	// 操作和显示切换
	$("#box").click(function() {
		if($(this).attr("checked")) {
			$("#show").css("display", "none");
			$("#formDiv").css("display", "");
		} else {
			$("#formDiv").css("display", "none");
			$("#show").css("display", "");		
		}
	});
});



function editForm() {
	var bname = $("#bname").val();
	var currPrice = $("#currPrice").val();
	var price = $("#price").val();
	var discount = $("#discount").val();
	var author = $("#author").val();
	var press = $("#press").val();
	var pid = $("#pid").val();
	var cid = $("#cid").val();
	
	if(!bname || !currPrice || !price || !discount || !author || !press || !pid || !cid) {
		qikoo.dialog.alert("图名、当前价、定价、折扣、作者、出版社、1级分类、2级分类不能为空！");
		return false;
	}
	
	if(isNaN(currPrice) || isNaN(price) || isNaN(discount)) {
		qikoo.dialog.alert("当前价、定价、折扣必须是合法小数！");
		return false;
	}
	
//	var method = $("<input>").attr("type", "hidden").attr("name", "methodName").attr("value", "edit");
//	$("#form").append(method);
//	$("#form").submit();
	$("#method").val("edit");
	$("#form").submit();
	
}

//function delForm() {
//	var method = $("<input>").attr("type", "hidden").attr("name", "methodName").attr("value", "delete");
//	$("#form").append(method);
//	$("#form").submit();
//}

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

/*
 * 点击编辑按钮时执行本函数
 */
//function editForm() {
//	$("#method").val("edit");
//	$("#form").submit();
//}
/*
 * 点击下架图书按钮时执行本函数
 */
 function soldOut() {
//	$("#method").val("delete");
//	$("#form").submit();	
	 
		qikoo.dialog.confirm('确认下架图书吗？',
				function(){
				$.ajax({
					async:true,
					cache:false,
					url:"/BookStore/backstage/BookServlet",
					data:{methodName:"soldOut", id:bookid},
					type:"POST",
					dataType:"json",
					success:function(result) {
						if(result.flag == "success"){
							 hiOverAlert("<span style='color:#c30;'>图书下架成功</span>", 2000);
							var hr = "/BookStore//backstage/BookServlet?methodName=findOneBook&bookid="+bookid;
							window.location.href = hr;
						}else{
					        hiOverAlert("<span style='color:#c30;'>图书下架失败</span>", 2000);
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
				        hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
				       }
				});			
		},
				function(){
					hiOverAlert('取消', 1500);
		});
}
 function putAwayAgain() {
//	$("#method").val("delete");
//	$("#form").submit();	
	 
	 qikoo.dialog.confirm('确认重新上架图书吗？',
			 function(){
		 $.ajax({
			 async:true,
			 cache:false,
			 url:"/BookStore/backstage/BookServlet",
			 data:{methodName:"putAwayAgain", id:bookid},
			 type:"POST",
			 dataType:"json",
			 success:function(result) {
				 if(result.flag == "success"){
					 hiOverAlert("<span style='color:#c30;'>图书重新上架成功</span>", 2000);
					 var hr = "/BookStore//backstage/BookServlet?methodName=findOneBook&bookid="+bookid;
					 window.location.href = hr;
				 }else{
					 hiOverAlert("<span style='color:#c30;'>图书下架失败</span>", 2000);
				 }
			 },
			 error:function(XMLHttpRequest, textStatus, errorThrown){
				 hiOverAlert("<span style='color:#c30;'>"+textStatus+"</span>", 1500);
			 }
		 });			
	 },
	 function(){
		 hiOverAlert('取消', 1500);
	 });
 }
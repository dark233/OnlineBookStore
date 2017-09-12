$(function(){
	$(".delLevelOne").click(confirmDel1);
	$(".delLevelTwo").click(confirmDel2);
});
var confirmDel1 = function(){
	var cid = $(this).attr("id");
	var cname = $("#"+cid+"Cname").val();
	qikoo.dialog.confirm("确认删除一级图书分类"+cname+"?", function(){
		var hr = "/BookStore/backstage/CategoryServlet?methodName=deleteParent&cid="+cid;
		window.location.href = hr;
		return;
	}, function(){
		hiOverAlert("取消",1500);
		return;
	});
};
var confirmDel2 = function(){
	var cid = $(this).attr("id");
	var cname = $("#"+cid+"Cname").val();
	qikoo.dialog.confirm("确认删除二级图书分类"+cname+"?", function(){
		var hr = "/BookStore/backstage/CategoryServlet?methodName=deleteChild&cid="+cid;
		window.location.href = hr;
		return;
	}, function(){
		hiOverAlert("取消",1500);
		return;
	});
};
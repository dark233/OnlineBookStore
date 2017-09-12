$(function() {
	/*$("input[name='remarkLevel']").bind("click",function(){ alert($(this).val()); }); */
	/*给评论级别单选按钮添加店家事件*/
	$("input[name='remarkLevel']").click(function() {
		var $this = $(this);
		var isChecked = $this.attr("checked");
		if(isChecked){
			var value = $this.val();
/*			$("input[name='remarkLevel']").each(function(){
				var $this_=$(this);
				$this_.removeAttr("checked");
			});*/
			var url = remarkLevelUrl+"&minLevel="+value;
			window.location.href=url;
		}
	});
	/*将框架子页面中的滚动条与父页面使用同一滚动条*/
	window.document.body.scrollHeight=window.parent.document.all.iframe.style.height;
});




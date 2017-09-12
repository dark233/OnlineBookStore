$(function() {
	//对图书封面图片上的超链接进行url编码
	var $linkTitle = $("a.linkTitle");
	$linkTitle.each(function(){
		loadHref(linkUrl, $(this));
	});
});


function _change(){
	var src = "/BookStore/GetImage?"
		+ new Date().getTime();
	$("#vCode").attr("src", src);
}
function loadHref(linkUrl,$this){
	var href = $this.attr("href");
	var encodeHref = encodeURIComponent(href);
	var finalUrl = linkUrl+"?openUrl="+encodeHref;
	$this.attr("href",finalUrl);
}; 
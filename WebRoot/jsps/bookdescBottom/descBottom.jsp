<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/bookdescBottom/descBottom.css'/>" />
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<style type="text/css">
.scrollNav{
	position:fixed;
	top:0px;
}
</style>
<script type="text/javascript">
$(function(){	
	$('.tabPanel ul li').click(function(){
		$(this).addClass('hit').siblings().removeClass('hit');
		$('.panes>div:eq('+$(this).index()+')').show().siblings().hide();	
	});
});
/* 下拉550的高度时，tab固定在顶部 */
/* $(window).scroll(function() {
	var $topMenuScroll=$(window).scrollTop();
	if($topMenuScroll>=560){
	$(".topMenu").addClass("scrollNav");
	}else
	{
	$(".topMenu").removeClass("scrollNav");
	}
	}); */
</script>


</head>

 <body> 
	<div style="width:100%;margin:40px auto;">
	
	    <div class="tabPanel">
			<ul class = "topMenu">
				<li class="hit">图书评论</li>
				<li>图书详情</li>
				<li>相关图书</li>
			</ul>
			<div class="panes">
				<div class="pane" style="display:block;"><iframe frameborder="0" style = "width: 100%;height: 100%;" src="<c:url value='/RemarkServlet?methodName=findByBookId&bookid=${book.id }'/>" name="bottom"></iframe></div>
				<div class="pane">
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				<h4>Secend tab content</h4><p>图书详情</p>
				</div>
				<div class="pane">
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				<h4>Third tab content</h4><p>相关图书</p>
				</div>
			</div>
	    </div>
	
	</div>
 </body>
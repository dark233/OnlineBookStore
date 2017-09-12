
$(function(){
			$(".inner").hover(function() {
				$(this).css("border", "3px solid #FFCFB1");
			}, function() {
				$(this).css("border", "3px solid #ffffff");
			});
/*			var s = parent.find("iframe[name='search']");
			s.location.reload(false);*/
			if(!flag){
			/*	var searchDom = parent.document.getElementsByName("search")[0];
				var searchEle = searchDom.getElementsByName("search")[0];
				var $searchEle = $(searchEle);
				$searchEle.val("");*/
		   /*     var searchiFram = window.parent.document.getElementsByName("search")[0].contentWindow;  以下获取框架子窗口的方式也可*/
				var searchiFram = window.parent.search;
		        var searchDom = searchiFram.document.getElementsByName("search")[0]; 
		        var $search = $(searchDom);
		        $search.val("");
/*				var $parent = $(window.parent.document);
				var $search = $parent.find("input[type='text'][name='search']"); 
				$search.val("");*/
			}
			if(flag){
				var $booknameArr = $("a#bookname");
				search = search.toUpperCase();
				var arr = search.split(/\s+/);
				$booknameArr.each(function(){
					var text = $(this).text().toUpperCase();
					var text2 = $(this).text();
					for(var i in arr){
						if(($.trim(arr[i])).length != 0){
							var index = text.indexOf(arr[i]);
							if(index != -1){
								//var sub = text2.substr(index,arr[i].length);
								var sub = mysubstr(text2,arr[i],index, arr[i].length);
								text = text.replace(arr[i],"<font color=red>"+sub+"</font>");
								text2 = text2.replace(sub,"<font color=red>"+sub+"</font>");
//								alert(sub);
							}
						}
					}
					$(this).html(text2);
				});
				
			}
			
			$("#searchByPrice").click(function(){
				var minPrice = $("#rangeOfPrice input[name='minPrice']").val();
				var maxPrice = $("#rangeOfPrice input[name='maxPrice']").val();
				var url = priceUrl+"&minPrice="+minPrice+"&maxPrice="+maxPrice;
				window.location.href=url;
			});

		});

var mysubstr =  function (str,handleStr,index,length) {//字符串截取 包含对中文处理  
	    if (str.replace(/[\u4e00-\u9fa5]/g, "**").length <= str.length) {  
	        return str.substr(index,length);
	    }  
	    else { 
	    	var str2 = "";
//	    	var len = 0;
/*	    	for(var i = index;i<(index+length);i++){
	    		var j = str.charAt(i);
	    		if(/[\u4e00-\u9fa5]/.test(j)){
//	    			len += 2;
	    			length+=1;
	    		}else{
//	    			len += 1;
	    		}
	    	}*/
/*	    	for(var i = 0;i<handleStr.length;i++){
	    		var j = handleStr.charAt(i);
	    		if(/[\u4e00-\u9fa5]/.test(j)){
	    			length+=1;
	    		}else{
	    		}
	    	}*/
//	    	str2 = str.substr(index,len);
	    	str2 = str.substr(index,length);
	    	return str2;
	    	
	        /* var len = 0;  
	        var tmpStr = "";  
	        for (var i = 0; i < str.length; i++) {//遍历字符串  
	            if (/[\u4e00-\u9fa5]/.test(str[i])) {//中文 长度为两字节  
	                len += 2;  
	            }  
	            else {  
	                len += 1;  
	            }  
	            if (len > n) {  
	                break;  
	            }  
	            else {  
	                tmpStr += str[i];  
	            }  
	        }  
	        return tmpStr + " ...";   */
	    }  
	}; 

	

/*获取cookie  */
		function getCookie(name) {
			var c = document.cookie;
			if (document.cookie.length > 0) {
				start = document.cookie.indexOf(name + "=");
				if (start != -1) {
					start = start + name.length + 1;
					end = document.cookie.indexOf(";", start);
					if (end == -1)
						end = document.cookie.length;
					return decodeURI(document.cookie.substring(start, end));
				}
			}
			return "";
		}

		/*添加cookie  */
		function setCookie(name, value, expiredays) {
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + expiredays);
			document.cookie = name
					+ "="
					+ escape(value)
					+ ((expiredays == null) ? "" : ";expires="
							+ exdate.toGMTString());
		}

		/* 检查cookie */
		function checkCookie(username,pwd) {
			username = getCookie(username);
			password = getCookie(pwd);
			if (username != null && username != "")
				$("#username").val(username);
			if(password != null && password !="")
				$("#password").val(password);
		}
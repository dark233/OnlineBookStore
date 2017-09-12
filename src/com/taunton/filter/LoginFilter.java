package com.taunton.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.util.Login;

import org.apache.log4j.Logger;

import com.taunton.javabean.User;
import com.taunton.servlet.BookServlet;

public class LoginFilter implements Filter {
	private static Logger logger = Logger.getLogger(LoginFilter.class); 

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String referer = req.getHeader("referer");
		if(referer != null && !referer.trim().isEmpty()){
			if(user==null){
				chain.doFilter(request, response);
				logger.info("使用登录界面过滤器，已放行。user = "+user);
			}else{
				res.sendRedirect(referer);
				logger.info("使用登录界面过滤器，已拦截,原路返回。userid = "+user.getUserid());
			}
		}else{
			res.sendRedirect(req.getContextPath()+"/enter.jsp");
		}
	}

	@Override
	public void destroy() {
		
	}

}

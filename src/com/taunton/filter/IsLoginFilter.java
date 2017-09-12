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

import org.apache.log4j.Logger;

import com.taunton.javabean.User;
import com.taunton.service.UserService;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.HandleSessionAttrUtils;

public class IsLoginFilter implements Filter{

	private static Logger logger = Logger.getLogger(IsLoginFilter.class); 
//	private static UserService us = DomainFactory.createDomainInstance(UserService.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String referer = req.getHeader("referer");
		User user = (User) HandleSessionAttrUtils.getAttrInSession(req, "user");
//		boolean isBan = us.findIsBanByUserid(user.getUserid());
		if(user != null){
			chain.doFilter(req, res);
			logger.info("使用是否登录过滤器，已放行。userid = "+user.getUserid());
		}else{
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, res);
			logger.info("使用是否登录过滤器，已拦截。user = "+user);
		}
	}

	@Override
	public void destroy() {
		
	}

}

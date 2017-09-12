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

import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.User;
import com.taunton.utils.HandleSessionAttrUtils;

public class BackStageUserIsLoginFilter implements Filter{

	private static Logger logger = Logger.getLogger(BackStageUserIsLoginFilter.class); 

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String referer = req.getHeader("referer");
		BackStageUser user = (BackStageUser) HandleSessionAttrUtils.getAttrInSession(req, "backstageuser");
		if(user != null){
			chain.doFilter(req, res);
			logger.info("使用是否登录过滤器，已放行。backstageuserid = "+user.getUserid());
		}else{
			req.getRequestDispatcher("/adminjsps/login.jsp").forward(req, res);
			logger.info("使用是否登录过滤器，已拦截。backstageuser = "+user);
		}
	}

	@Override
	public void destroy() {
		
	}

}

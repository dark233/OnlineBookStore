package com.taunton.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
/**
 * 对准备结算页面的过滤器，准备结算页面只能从购物车点击结算过来
 * @author Administrator
 *
 */
public class ShowItemFilter implements Filter{

	private static Logger logger = Logger.getLogger(ShowItemFilter.class); 

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String referer = req.getHeader("referer");
		if(referer != null && !referer.trim().isEmpty()){
			String referer2 = referer.substring(referer.indexOf("BookStore"));
			List<String> filterList = new ArrayList<String>();
			filterList.add("BookStore/ShoppingCartServlet?methodName=findShoppingCartWithUserId");
			filterList.add("BookStore/ShoppingCartServlet?methodName=findPreparCashShoppingCart");
			if(filterList.contains(referer2)){
				chain.doFilter(req, resp);
				logger.info("使用准备结算页面过滤器，已放行。referer = "+referer);
			}else{
//				如果不是从购物车入口来结算页面，那么原路返回
				resp.sendRedirect(referer);
				logger.info("使用准备结算页面过滤器，已拦截。referer = "+referer);
			}
		}else{
			resp.sendRedirect(req.getContextPath()+"/enter.jsp");
			logger.info("使用准备结算页面过滤器，已拦截。referer = "+referer);

		}
	}

	@Override
	public void destroy() {
		
	}

}

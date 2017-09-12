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
import com.taunton.web.EncodingRequest;

public class EncodingFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
//		自定义Request类，重写getParmeter(),解决请求乱码
		EncodingRequest ereq = new EncodingRequest(req);
//		解决响应乱码
		resp.setContentType("text/html;charset=utf-8");
		chain.doFilter(ereq, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}

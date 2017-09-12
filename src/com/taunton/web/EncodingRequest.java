package com.taunton.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingRequest extends HttpServletRequestWrapper {

	HttpServletRequest request;

	public EncodingRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public String getParameter(String name) {
		String param = request.getParameter(name);
		byte[] b = null;
		try {
			if (param != null) {
				b = param.getBytes("iso-8859-1");
				param = new String(b,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("请求编码失败"+e);
		}
		return param;

	}

}

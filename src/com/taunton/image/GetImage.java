package com.taunton.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class GetImage extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(GetImage.class); 


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("进入图形验证码获取Servlet，通过GET请求");
		try {
			VerifyCode vc = new VerifyCode();
			BufferedImage bi = vc.getImage();
			String text = vc.getText();
			logger.info("获取验证码,text = "+text);
			request.getSession().setAttribute("text", text);
			ServletOutputStream out = response.getOutputStream();
			vc.output(bi, out);
		} catch (Exception e) {
			logger.info(e);
			logger.error(e);
		}
	}

}

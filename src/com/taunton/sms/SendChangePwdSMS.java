package com.taunton.sms;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import sun.util.logging.resources.logging;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taunton.servlet.BookServlet;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.TimeUtils;

/**
 * 发送修改密码校验码
 * 
 * @author tangdong
 * 
 */
public class SendChangePwdSMS extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SendChangePwdSMS.class); 


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text./html;charset=UTF-8");
		String tel = GetReqParamUtils.getParameter(request, "tel", null, null);
//		String operate = GetReqParamUtils.getParameter(request, "operate", null, null);
		if(tel == null || tel.trim().isEmpty()){
			return;
		}
		String reqAddr = request.getRemoteAddr();
		logger.info("IP = "+reqAddr+" 发送修改登录密码验证码");
		Random rm = new Random();
		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, 4);
		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);
		fixLenthString = fixLenthString.substring(1, 4 + 1);
		String url = "https://eco.taobao.com/router/rest";
		String appkey = "23730810";
		String secret = "03d44caf4ea79c7b7bd281baa9e8c98a";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		//公共回传参数，在“消息返回”中会透传回该参数,可选
		req.setExtend("");
		//短信类型
		req.setSmsType("normal");
		//短信签名，需要是已在阿里大于服务端配置了的签名
		req.setSmsFreeSignName("投食网");
		req.setSmsParamString("{code:'"+fixLenthString+"',operate:'修改登录密码'}");
		req.setRecNum(tel);
		//在阿里大于服务端配置的短信模板id
		req.setSmsTemplateCode("SMS_60020079");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		logger.info("发送手机修改登录密码验证码请求报文:"+req.getTextParams().toString());
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		logger.info("接收手机修改登录密码验证码响应报文:"+rsp.getBody());
		String subCode = rsp.getSubCode();
		if (subCode == null) {
			String changePwdCodeTime = TimeUtils.formatDate(new Date(),TimeUtils.FORMATTER_1);
			
			
			request.getSession().setAttribute("changePwdTel", tel);
			request.getSession().setAttribute("changePwdCode", fixLenthString);
			request.getSession().setAttribute("changePwdCodeTime", changePwdCodeTime);
			response.getWriter().print("1");
		} else {
			response.getWriter().print("0");
		}
	}

}

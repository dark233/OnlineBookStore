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
 * 通知短信发送
 * 
 * @author tangdong
 * 
 */
public class SendNoticeSMS extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SendNoticeSMS.class); 


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text./html;charset=UTF-8");
		String tel = GetReqParamUtils.getParameter(request, "tel", null, null);
		String username = GetReqParamUtils.getParameter(request, "username", null, null);
		String operate = GetReqParamUtils.getParameter(request, "operate", null, null);
		if(tel == null || tel.trim().isEmpty() || username == null || username.trim().isEmpty() || operate == null || operate.trim().isEmpty()){
			return;
		}
		String reqAddr = request.getRemoteAddr();
		logger.info("IP = "+reqAddr+" 对 "+username+" 做修改"+operate+"操作.");
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
		req.setSmsParamString("{username:'"+username+"',type:'"+operate+"'}");
		req.setRecNum(tel);
		//在阿里大于服务端配置的短信模板id
		req.setSmsTemplateCode("SMS_59935179");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		logger.info("发送"+operate+"通知请求报文:"+req.getTextParams().toString());
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		logger.info("接收"+operate+"通知响应报文:"+rsp.getBody());
		String subCode = rsp.getSubCode();
		if (subCode == null) {
			return;
		} else {
			return;
		}
	}

}

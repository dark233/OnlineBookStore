package com.taunton.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.taunton.service.BookService;
import com.taunton.service.UserService;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.JDBCUtils;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BaseServlet.class); 
	@Override
	protected void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = GetReqParamUtils.getParameter(request, "methodName", null, null);
		String sss = request.getQueryString();
		Method method = null;
		try {
			method = this.getClass().getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e1){
			throw new RuntimeException("通过方法名: "+methodName+"获取Method对象失败"+e1);
		}
//		if(methodName!=null){	
			String result = null;
			Connection conn = null;
			try {
				conn = JDBCUtils.getConnection();
				conn.setAutoCommit(false);
				ConnectionContext.getInstance().bind(conn);
				logger.info("执行"+methodName+"方法");
				result = (String)method.invoke(this, request, response);
				if(result != null && !result.trim().isEmpty()) {
					int index = result.indexOf(":");
					if(index == -1) {
						request.getRequestDispatcher(result).forward(request, response);
					} else {
						String start = result.substring(0, index);
						String path = result.substring(index + 1);
						if(start.equals("f")) {//转发
							request.getRequestDispatcher(path).forward(request, response);
						} else if(start.equals("r")) {//重定向
							response.sendRedirect(request.getContextPath() + path);
						}
					}
				}
				conn.commit();
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					logger.error("回滚失败！");
				}
				logger.error("执行"+methodName+"方法出现异常，失败"+e);
			}finally{
				ConnectionContext.getInstance().remove();
				JDBCUtils.release(conn);
				logger.info("释放数据库连接:"+conn);
			}
//			String[] rs = result.split(":");
//			if (result != null && !(result.trim().isEmpty())) {
//				if (!(result.contains(":"))) {
//					request.getRequestDispatcher(result).forward(request, response);;
//				} else if (rs[0].equals("c")) {
//					System.out.println("转发");
//					System.out.println(request.getContextPath() + rs[1]);
//					response.sendRedirect(request.getContextPath() + rs[1]);
//				} else {
//					throw new RuntimeException("返回值有误");
//				}
//			} else if (result == null) {
//				throw new RuntimeException("方法名有误");
//			} else if (result.trim().isEmpty()) {
//				throw new RuntimeException("返回值不能为空字符串");
//			}
//		}
	}
	@Override
	public void init() throws ServletException {
		super.init();
	}

}


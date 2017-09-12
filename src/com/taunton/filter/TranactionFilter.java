package com.taunton.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.taunton.service.BookService;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.JDBCUtils;


/**
 * Servlet Filter implementation class TranactionFilter
 */
public class TranactionFilter implements Filter {
	private static Logger logger = Logger.getLogger(TranactionFilter.class); 
    /**
     * Default constructor. 
     */
    public TranactionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Connection connection = null;
		
		try {
			
			connection = JDBCUtils.getConnection();
			connection.setAutoCommit(false);
			ConnectionContext.getInstance().bind(connection);
			chain.doFilter(request, response);
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("回滚失败");
				throw new RuntimeException("回滚失败"+e1);
			}
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
			
		} finally{
			ConnectionContext.getInstance().remove();
			JDBCUtils.release(connection);
			logger.info("数据库连接: "+connection+"已释放");
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

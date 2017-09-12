package com.taunton.backstage.servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Category;
import com.taunton.javabean.Dict;
import com.taunton.javabean.Handle;
import com.taunton.service.CategoryService;
import com.taunton.service.DictService;
import com.taunton.service.HandleService;
import com.taunton.service.TradeService;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DictConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.HandleSessionAttrUtils;
import com.taunton.web.CriteriaHandle;
import com.taunton.web.Page;

import org.apache.log4j.Logger;;

/**
 * 订单Servlet
 * 
 * @author taunton
 * 
 */
public class TradeServlet extends BaseServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TradeServlet.class); 
	private static TradeService ts = DomainFactory.createDomainInstance(TradeService.class);
	
}

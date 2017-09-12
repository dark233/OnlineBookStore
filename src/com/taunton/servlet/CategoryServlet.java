package com.taunton.servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taunton.javabean.Category;
import com.taunton.service.CategoryService;
import com.taunton.utils.DomainFactory;

import org.apache.log4j.Logger;;

/**
 * 图书类型Servlet
 * 
 * @author taunton
 * 
 */
public class CategoryServlet extends BaseServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2154724570439468842L;
	private static Logger logger = Logger.getLogger(CategoryServlet.class); 
	private CategoryService cs = DomainFactory.createDomainInstance(CategoryService.class);
	/**
	 * 查询所有分类
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws Exception,ServletException, IOException {
		/*
		 * 1. 通过service得到所有的分类
		 * 2. 保存到request中，转发到left.jsp
		 */
		Set<Category> parents = cs.findAll();
		logger.info("查找所有图书分类结果 : "+parents.toString());
		req.setAttribute("parents", parents);
		return "f:/jsps/left.jsp";
	}
	
}

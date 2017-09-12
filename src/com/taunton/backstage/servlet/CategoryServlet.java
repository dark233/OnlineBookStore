package com.taunton.backstage.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.Category;
import com.taunton.service.BookService;
import com.taunton.service.CategoryService;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;

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
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CategoryServlet.class); 
	private CategoryService cs = DomainFactory.createDomainInstance(CategoryService.class);
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
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
//		logger.info("查找所有图书分类结果 : "+parents.toString());
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	/**
	 * 添加一级图书分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addParent(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		/*
		 * 1. 封装表单数据到Category中
		 * 2. 调用service的add()方法完成添加
		 * 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category parent = CommonUtils.toBean(request.getParameterMap(), Category.class);
		String cname = GetReqParamUtils.getParameter(request, "cname", null, null);
		String desc = GetReqParamUtils.getParameter(request, "desc", null, null);
		parent.setCname(cname);
		parent.setDesc(desc);
		String cid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		cid+=UUIDUtils.getUUID(4);
		parent.setCid(cid);//设置cid
		cs.add(parent);
		return findAll(request, response);
	}
	
	/**
	 * 添加二级图书分类准备
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addChildPre(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = GetReqParamUtils.getParameter(request, "pid", null, null);//当前点击的父分类id
		Set<Category> parents = cs.findParents();
		request.setAttribute("pid", pid);
		request.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/add2.jsp";
	}
	
	/**
	 * 添加二级图书分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addChild(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 封装表单数据到Category中
		 * 2. 需要手动的把表单中的pid映射到child对象中
		 * 2. 调用service的add()方法完成添加
		 * 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category child = CommonUtils.toBean(request.getParameterMap(), Category.class);
		String cname = GetReqParamUtils.getParameter(request, "cname", null, null);
		String desc = GetReqParamUtils.getParameter(request, "desc", null, null);
		child.setCname(cname);
		child.setDesc(desc);
		String cid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		cid+=UUIDUtils.getUUID(4);
		child.setCid(cid);//设置cid
		cs.add(child);
		return findAll(request, response);
	}
	

	
	/**
	 * 修改一级分类准备
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editParentPre(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		/*
		 * 1. 获取链接中的cid
		 * 2. 使用cid加载Category
		 * 3. 保存Category
		 * 4. 转发到edit.jsp页面显示Category
		 */
		String cid = GetReqParamUtils.getParameter(request, "cid", null, null);
		Category parent = cs.load(cid);
		request.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	
	/**
	 * 修改一级分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editParent(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		/*
		 * 1. 封装表单数据到Category中
		 * 2. 调用service方法完成修改
		 * 3. 转发到list.jsp显示所有分类（return findAll();）
		 */
		Category parent = CommonUtils.toBean(request.getParameterMap(), Category.class);
		String cname = GetReqParamUtils.getParameter(request, "cname", null, null);
		String desc = GetReqParamUtils.getParameter(request, "desc", null, null);
		parent.setCname(cname);
		parent.setDesc(desc);
		cs.edit(parent);
		return findAll(request, response);
	}
	
	/**
	 * 修改二级图书分类准备
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editChildPre(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		/*
		 * 1. 获取链接参数cid，通过cid加载Category，保存之
		 * 2. 查询出所有1级分类，保存之
		 * 3. 转发到edit2.jsp
		 */
		String cid = GetReqParamUtils.getParameter(request, "cid", null, null);
		Category child = cs.load(cid);
		request.setAttribute("child", child);
		request.setAttribute("parents", cs.findParents());
		
		return "f:/adminjsps/admin/category/edit2.jsp";
	}
	
	/**
	 * 修改二级图书分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		/*
		 * 1. 封装表单参数到Category child
		 * 2. 把表单中的pid封装到child, ...
		 * 3. 调用service.edit()完成修改
		 * 4. 返回到list.jsp
		 */
		Category child = CommonUtils.toBean(request.getParameterMap(), Category.class);
		String cname = GetReqParamUtils.getParameter(request, "cname", null, null);
		String desc = GetReqParamUtils.getParameter(request, "desc", null, null);
		child.setCname(cname);
		child.setDesc(desc);
		cs.edit(child);
		return findAll(request, response);
	}
	
	/**
	 * 删除一级图书分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 获取链接参数cid，它是一个1级分类的id
		 * 2. 通过cid，查看该父分类下子分类的个数
		 * 3. 如果大于零，说明还有子分类，不能删除。保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = GetReqParamUtils.getParameter(request, "cid", null, null);
		int cnt = cs.findChildrenCountByParent(cid);
		if(cnt > 0) {
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "该分类下还有子分类，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			cs.delete(cid);
			return findAll(request, response);
		}
	}
	
	/**
	 * 删除二级图书分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 获取cid，即2级分类id
		 * 2. 获取该分类下的图书个数
		 * 3. 如果大于零，保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = request.getParameter("cid");
		long cnt = bs.findBookCountByCategory(cid);
		if(cnt > 0) {
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "该分类下还存在图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			cs.delete(cid);
			return findAll(request, response);
		}
	}
}

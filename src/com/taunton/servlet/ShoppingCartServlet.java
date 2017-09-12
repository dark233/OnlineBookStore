package com.taunton.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.taunton.javabean.Book;
import com.taunton.javabean.ShippingAddress;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.User;
import com.taunton.service.BookService;
import com.taunton.service.ShippingAddressService;
import com.taunton.service.ShoppingCartService;
import com.taunton.utils.CommonConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.HandleSessionAttrUtils;

public class ShoppingCartServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2196109636165591206L;
	private static Logger logger = Logger.getLogger(ShoppingCartServlet.class); 
	private ShoppingCartService scs = DomainFactory.createDomainInstance(ShoppingCartService.class);
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
	private ShippingAddressService sas = DomainFactory.createDomainInstance(ShippingAddressService.class);
	
	/**
	 * 获取选定购物车项组成的购物车
	 * @param request
	 * @param response
	 * @return
	 */
	public String findPreparCashShoppingCart(HttpServletRequest request,HttpServletResponse response){
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		ShoppingCart sc = null;;
		Set<ShoppingCartItem> sci = null;
		sci = scs.findShoppingCartItemsByShoppingCartidAndIsChecked(shoppingCartid, 1);
		for (ShoppingCartItem shoppingCartItem : sci) {
			shoppingCartItem.setBook(bs.selectBook(shoppingCartItem.getBookid()));
		}
		sc = scs.findShoppingCartWithUserid(user.getUserid());
		sc.setShopppingCartItems(sci);
		Set<ShippingAddress> shippingAddressList = sas.findShippingAddressList(user.getUserid());
		request.setAttribute("sc", sc);
		request.setAttribute("shippingAddressList", shippingAddressList);
		return "f:/jsps/cart/showitem.jsp";
		
	}
	
	/**
	 * 获取完整的购物车
	 * @param request
	 * @param response
	 * @return
	 */
	public String findShoppingCartWithUserId(HttpServletRequest request,HttpServletResponse response){
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		ShoppingCart sc = null;
		Set<ShoppingCartItem> sci = null;
		//进行购物车项检查
		scs.checkShoppingCartItems(shoppingCartid,2);
		scs.updateShoppingCartMoney(shoppingCartid);
		scs.updateShoppingCartItemsNum(shoppingCartid);
		sci = scs.findShoppingCartItemsByShoppingCartidAndIsChecked(shoppingCartid, null);
		for (ShoppingCartItem shoppingCartItem : sci) {
			shoppingCartItem.setBook(bs.selectBook(shoppingCartItem.getBookid()));
		}
		sc = scs.findShoppingCartWithUserid(user.getUserid());
		sc.setShopppingCartItems(sci);
		logger.info("获取用户 user = "+user.getUsername()+"  的购物车 shoppingCart = "+sc.toString());
		request.setAttribute("sc", sc);
		return "f:/jsps/cart/list.jsp";
	}
	
	@Test
	public void test(){
		User user = new User("tangdun", "a123");
		User user2 = new User("tangdong","a666");
		JSONArray ja = new JSONArray();
		ja.add(user);
		ja.add(user2);
		System.out.println(ja);
	}
	
	/**
	 * 加入购物车时进行校验
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void validateAddToShoppingCart(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String bookid = GetReqParamUtils.getParameter(request, "bookid", null, null);
		ShoppingCartItem sci = null;
		Book book = null;
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		sci = scs.findShoppingCartItemWithBookidAndShoppingCartId(bookid, shoppingCartid);
		book = bs.selectBook(bookid);
		int quantity = GetReqParamUtils.getIntegerParameter(request, "quantity", 1, null);
		logger.info("购物车项校验请求参数：bookid = "+bookid+",quantity = "+quantity+",userid = "+user.getUserid());
		ShoppingCart sc = scs.findShoppingCartWithUserid(user.getUserid());
		PrintWriter pw = response.getWriter();
		if(sc.getItemsNum()==CommonConstant.CARTITEMNUM && sci==null){
			pw.print("购物车已满");
			return;
		}
		if(book.getIsShow()==2){
			pw.print("图书已下架");
			return;
		}else if(book.getStoreNumber()<=0){
			pw.print("图书暂时无货");
			return;
		}else if(sci != null && (sci.getQuantity() > book.getStoreNumber() || sci.getQuantity() == book.getStoreNumber())){
			pw.print("图书库存不足");
		}
		pw.print("1");
		return;	
		
		
	}
	
	/**
	 * 在修改购物车数量时校验对应的商品是否有货，是否下架，购物车，（可选：商品限购数）
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void validateShoppingCartItem(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ShoppingCartItem sci = null;
		Book book = null;
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		String shoppingCartItemid = GetReqParamUtils.getParameter(request, "shoppingCartItemid",null, null);
		int quantity = GetReqParamUtils.getIntegerParameter(request, "quantity", 1, null);
		logger.info("购物车项校验请求参数：shoppingCartItemid = "+shoppingCartItemid+",quantity = "+quantity+",userid = "+user.getUserid());
		sci = scs.findShoppingCartItemWithItemid(shoppingCartItemid);
		book = bs.selectBook(sci.getBookid());
		sci.setBook(book);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject jo = JSONObject.fromObject(sci);
		if(book.getIsShow()==2){
			scs.updateisCheckedWithShoppingCartItemId(shoppingCartItemid, 2, shoppingCartid);
			jo.put("statusCode", "图书已下架");
			pw.print(jo.toString());
			return;
		}else if(book.getStoreNumber()<=0){
			scs.updateisCheckedWithShoppingCartItemId(shoppingCartItemid, 2, shoppingCartid);
			jo.put("statusCode", "图书暂时无货");
			pw.print(jo.toString());
			return;
		}else if(book.getStoreNumber()<quantity){
			jo.put("statusCode", "图书库存不足");
			pw.print(jo.toString());
			return;
		}
		jo.put("statusCode", "1");
		pw.print(jo.toString());
		return;	
		
		
	}
	
	/**
	 * 当在购物车界面点击结算与在准备结算页面点击提交订单时需要进行购物车选定商品校验（是否有货，是否下架）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void validateShoppingCartWhenCash(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		String checkResult = scs.checkShoppingCartItems(shoppingCartid, 1);
		PrintWriter pw = response.getWriter();
		if(checkResult == null){
			pw.print("1");
			return;
		}else{
			pw.print(checkResult);
			return;
		}
		
	}
	
	/**
	 *添加图书到购物车
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void addToShoppingCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		String bookid = GetReqParamUtils.getParameter(request, "bookid", null,null);
		Float currPrice = GetReqParamUtils.getFloatParameter(request, "currPrice", null, null);
		PrintWriter pw = response.getWriter();
		logger.info("ajax请求参数为:  bookid = "+bookid+"会话参数:  shoppingCartid = "+shoppingCartid);
		if(bookid==null || currPrice==null){
			pw.print("错误");
			return;
		}
		
		Book book = bs.selectBook(bookid);
		if(book==null){
			//查无此书；防止破坏
			pw.print("错误");
			return;
		}
		boolean flag = false;
		try {
			flag = scs.addToShoppingCart(1, bookid,currPrice, shoppingCartid);
			if(flag){
				pw.print("1");
				return;
			}else{
				pw.print("添加到购物车失败");
				return;
			}
			
		} catch (Exception e) {
			pw.print("添加到购物失败");
			logger.error(e);
			throw new RuntimeException(e);
		}

		
		//要将这里改造成ajax请求
//		request.setAttribute("book", book);
		//调用findList()
//		findList(request, response);
		
	}
	
	/**
	 * 修改购物车内单项图书的数量
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void changeShoppingCartItemQuantity(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		ShoppingCart sc = user.getShoppingCart();
		String shoppingCartid = sc.getShoppingCartid();
		String shoppingCartItemid = GetReqParamUtils.getParameter(request, "shoppingCartItemid",null, null);
		Integer quantity = GetReqParamUtils.getIntegerParameter(request, "quantity", null, null);
		PrintWriter pw = response.getWriter();
		if(quantity == null){
			pw.print("0");
			return;
		}
		boolean flag =  false;
		response.setContentType("application/json;charset=utf-8");
		try {
			flag = scs.updateQuantityOfShoppingCart(quantity, shoppingCartItemid, shoppingCartid);
			if(flag){
				ShoppingCartItem shoppingCartItem = scs.findShoppingCartItemWithItemid(shoppingCartItemid);
				Book book = bs.selectBook(shoppingCartItem.getBookid());
				StringBuilder sb = new StringBuilder("{");
				sb.append("\"statusCode\"").append(":").append("\"success\"");
				sb.append(",");
				sb.append("\"quantity\"").append(":").append(shoppingCartItem.getQuantity());
				sb.append(",");
				sb.append("\"subtotal\"").append(":").append(book.getPrice()*quantity);
				sb.append(",");
				sb.append("\"storeNumber\"").append(":").append(book.getStoreNumber());
				sb.append("}");
				pw.print(sb);
				return;
			}else{
				StringBuilder sb = new StringBuilder("{");
				sb.append("\"statusCode\"").append(":").append("\"failed\"");
				sb.append("}");
				pw.print(sb);
				return;
			}
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("{");
			sb.append("\"statusCode\"").append(":").append("\"failed\"");
			sb.append("}");
			pw.print(sb);
			logger.error(e);
			throw new RuntimeException(e);
		}
//		try {
//			response.getWriter().write("修改图书数量成功,"+sc.getTotalQuantity()+","+sc.getTotalMoney()+","+sc.getShoppingCartItem(bookid).getMoney());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	/**
	 * 删除选中购物车项
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void deleteShoppingCartItems(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		ShoppingCart sc = user.getShoppingCart();
		boolean flag = false;
		PrintWriter pw = response.getWriter();
		try {
			flag = scs.deleteShoppingCartItems(sc.getShoppingCartid());
			if(flag){
				pw.print("1");
				return;
			}else{
				pw.print("删除失败");
				return;
			}
		} catch (Exception e) {
			pw.print("删除失败");
			logger.error(e);
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 根据传入购物车项id参数删除一条购物车项
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void deleteShoppingCartItem(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		ShoppingCart sc = user.getShoppingCart();
		String shoppingCartItemid = request.getParameter("shoppingCartItemid");
		String shoppingCartid = sc.getShoppingCartid();
		boolean flag = false;
		PrintWriter pw = response.getWriter();
		try {
			flag = scs.deleteOneShoppingCartItem(shoppingCartItemid, shoppingCartid);
			if(flag){
				pw.print("1");
				return;
			}
			else{
				pw.print("删除失败");
				return;
			}
		} catch (Exception e) {
			pw.print("删除失败");
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void clearShoppingCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		boolean flag = false;
		PrintWriter pw = response.getWriter();
		try {
			flag = scs.clearShoppingCart(shoppingCartid);
			if(flag){
				pw.print("1");
				return;
			}else{
				pw.print("清空购物车失败");
				return;
			}
		} catch (Exception e) {
			pw.print("清空购物车失败");
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	/**单项
	 * 设置购物车项是否被选中
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void setIsCheckedInShoppingCartItem(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String shoppingCartItemid = GetReqParamUtils.getParameter(request, "shoppingCartItemid", null, null);
		boolean isChecked_ = GetReqParamUtils.getBooleanParameter(request, "isChecked", false);
		int isChecked = 2;
		if(isChecked_){
			isChecked = 1;
		}
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		PrintWriter pw = response.getWriter();
		if(shoppingCartItemid == null){
			pw.print("错误");
			return;
		}
		try {
			scs.updateisCheckedWithShoppingCartItemId(shoppingCartItemid, isChecked, shoppingCartid);
			pw.print("1");
		} catch (Exception e) {
			pw.print("错误");
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 设置整个购物车购物车项选定状态
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void setIsCheckedInShoppingCart(HttpServletRequest request,HttpServletResponse response) throws IOException{
		boolean isChecked_ = GetReqParamUtils.getBooleanParameter(request, "isChecked", false);
		int isChecked = 2;
		if(isChecked_){
			isChecked = 1;
		}
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		PrintWriter pw = response.getWriter();
		try {
			scs.updateisCheckedWithShoppingCartId(shoppingCartid, isChecked);
			pw.print("1");
		} catch (Exception e) {
			pw.print("错误");
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
}

package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.taunton.dao.BookDAO;
import com.taunton.javabean.Book;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.TradeItem;
import com.taunton.servlet.BookServlet;
import com.taunton.web.CriteriaBook;
import com.taunton.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO  {
	
	@Override
	public void addBook(Book book) throws SQLException {
		String sql = "INSERT INTO book(id,author,title,price,discount,currprice,publishingDate,press,edition,pageNum,wordNum,bookSize,"
				+ "printDate,paper,salesAmount,storeNumber,putawaytime,soldouttime,cid,imgCacheUrl,isUpdate,isShow)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {book.getId(),book.getAuthor(),book.getTitle(),book.getPrice(),book.getDiscount(),book.getCurrPrice(),book.getPublishingDate(),
				book.getPress(),book.getEdition(),book.getPageNum(),book.getWordNum(),book.getBookSize(),book.getPrintDate(),book.getPaper(),
				book.getSalesAmount(),book.getStoreNumber(),book.getPutAwayTime(),book.getSoldOutTime(),book.getCid(),book.getImgCacheUrl(),
				book.getIsUpdate(),book.getIsShow()};
		update(sql, obj);
	}

	@Override
	public void batchUpdateStoreNumber(
			Collection<TradeItem> tradeItems) throws SQLException {
		String sql = null;
		sql = "UPDATE book SET storeNumber = storeNumber + ? " +
					"WHERE id = ?";
		List<TradeItem> list = new ArrayList<>(tradeItems);
		Object [][] obj = new Object[tradeItems.size()][2];
		for(int i = 0;i<list.size();i++){
			obj[i][0] = list.get(i).getQuantity();
			obj[i][1] = list.get(i).getBookid();
		}
		batch(sql, obj);
	}

	@Override
	public void batchUpdateSalesAmount(Collection<TradeItem> tradeItems) throws SQLException {
		String sql = null;
		sql = "UPDATE book SET salesAmount = salesAmount + ? WHERE id = ?";
		List<TradeItem> list = new ArrayList<>(tradeItems);
		Object [][] obj = new Object[tradeItems.size()][2];
		for(int i = 0;i<list.size();i++){
			obj[i][0] = list.get(i).getQuantity();
			obj[i][1] = list.get(i).getBookid();
		}
		batch(sql, obj);
		
	}

	@Override
	public void updateIsShowWithBookId(Integer isShow,String id) throws SQLException {
		String sql = "UPDATE book SET isshow = ? WHERE id = ?";
		Object[] obj = {isShow,id};
		update(sql, obj);
	}	
	
	@Override
	public void updateBook(Book book) throws SQLException {
		String sql = "UPDATE book SET author = ?,title = ?,price = ?,discount = ?,currprice = ?,publishingDate = ?,press = ?,edition = ?,"
				+ "pageNum = ?,wordNum = ?,bookSize = ?,printDate = ?,paper = ?,salesAmount = ?,storeNumber = ?,cid = ?,"
				+ "imgCacheUrl = COALESCE(?,imgCacheUrl) "
				+ " WHERE id = ?";
		Object[] params = {book.getAuthor(),book.getTitle(),book.getPrice(),book.getDiscount(),book.getCurrPrice(),book.getPublishingDate(),book.getPress()
				,book.getEdition(),book.getPageNum(),book.getWordNum(),book.getBookSize(),book.getPrintDate(),book.getPaper(),book.getSalesAmount(),book.getStoreNumber()
				,book.getCid(),book.getImgCacheUrl(),book.getId()};
		update(sql,params);
	}
	
	@Override
	public void updateSoldTimeWithBookId(String soldOutTime,String id) throws SQLException {
		String sql = "UPDATE book SET soldouttime = ? WHERE id = ?";
		Object[] obj = {soldOutTime,id};
		update(sql, obj);		
	}

	@Override
	public Book getBook(String id) throws SQLException {
		String sql = "SELECT id,author,title,price,discount,currPrice,publishingDate,press,edition,pageNum,wordNum,bookSize,printDate,paper,"
				+ "salesAmount,storeNumber,cid,imgCacheUrl,isUpdate,isShow FROM book WHERE id = ?";
		Book book = query(sql, id);
		return book;
	}
	
	@Override
	public int getStoreNumber(String id) throws SQLException {
		String sql = "SELECT storeNumber FROM book where id = ?";
		return querySingle(sql, id);
	}
	@Override
	public Book getBookWithTitle(String title) throws SQLException{
		String sql = "SELECT id,author,title,price,discount,currPrice,publishingDate,press,edition,pageNum,wordNum,bookSize,printDate,paper,"
				+ "salesAmount,storeNumber,cid,imgCacheUrl,isUpdate,isShow FROM book WHERE id = ?";
		Book book = query(sql, title);
		return book;
	}
	@Override
	public long getBookNumWithCid(String cid) throws SQLException {
		String sql = "SELECT COUNT(*) FROM book where cid = COALESCE(?,cid)";
		return querySingle(sql, cid);
	}
	
	//根据图书类别
	@Override
	public Page<Book> getPageWithCategory(CriteriaBook cb,Integer isShow) throws SQLException {
		Page<Book> page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumberWithCategory(cb,isShow));
		//此时传入page对象里的pageNo已经被纠正了，这时也应该将cb里的pageNo纠正
		cb.setPageNo(page.getPageNo());
		page.setList(getPageListWithCategory(cb, page.getPageSize(),isShow));
		return page;
	}

	@Override
	public long getTotalBookNumberWithCategory(CriteriaBook cb,Integer isShow) throws SQLException {
		String sql = "SELECT count(*) FROM book WHERE currprice >= ? AND currprice <= ? AND cid = ? AND isShow = COALESCE(?,isShow)";
		Object[] obj = {cb.getMinPrice(),cb.getMaxPrice(),cb.getCid(),isShow};
		return querySingle(sql, obj);
	
	}

//	推测：在同时使用order by和limit时，MySQL进行了某些优化，将语句执行逻辑从"where——order by——limit"变成了"order by——limit——where"，
//	导致在某些情况下，同时使用order by和limit会导致查无结果（或结果数量不足）。具体出现问题与否是与表中数据有关的。!!!如果行不通，就只能变成子查询的格式了，子查询排序，外层分页
	@Override
	public List<Book> getPageListWithCategory(CriteriaBook cb, Integer pageSize,Integer isShow) throws SQLException {
//		String sql = null
//		Object[] obj = null;
//		if(cb.bookTypeid = 0){
//			 sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,bookTypeid,imgCacheUrl,isUpdate,isShow from book where price >= ? AND price <= ? AND booktypeid = booktypeid ORDER BY ?  limit ?,?";
//		     obj = {cb.getMinPrice(), cb.getMaxPrice(),cb.getOrderColumn(), (cb.getPageNo() - 1) * pageSize, pageSize};
//		}else{
//			 sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,bookTypeid,imgCacheUrl,isUpdate,isShow from book where price >= ? AND price <= ? AND booktypeid = ? ORDER BY ?  limit ?,?";
//			 obj = {cb.getMinPrice(), cb.getMaxPrice(),cb.getOrderColumn(),cb.getBookType(), (cb.getPageNo() - 1) * pageSize, pageSize};
//		}
		String sql = "SELECT a.id,a.author,a.title,a.price,a.discount,a.currPrice,a.publishingDate,a.press,a.edition,a.pageNum,a.wordNum,"
				+ "a.bookSize,a.printDate,a.paper,a.salesAmount,a.storeNumber,a.cid,a.imgCacheUrl,a.isUpdate,a.isShow FROM "
				+ "(SELECT id,author,title,price,discount,currPrice,publishingDate,press,edition,pageNum,wordNum,bookSize,printDate,paper,"
				+ "salesAmount,storeNumber,cid,imgCacheUrl,isUpdate,isShow FROM book WHERE currprice >= ? AND currprice <= ? AND cid = ? AND isShow = COALESCE(?,isShow) "
				+ " ORDER BY "+cb.getOrderField()+") a "
				+ "limit ?,?";
		Object[] obj = {cb.getMinPrice(), cb.getMaxPrice(),cb.getCid(),isShow,(cb.getPageNo() - 1) * pageSize, pageSize};
		return queryList(sql, obj);
	}
	
	
	
//	like模糊查询，根据图书标题,类型等
	
	@Override
	public String getSearchSql(CriteriaBook cb) {
		String search = cb.getSearch();
		if(search!=null && search.length()>0){
			String reg = "\\s+";
			search = search.trim();
			String[] arr = search.split(reg);
			int len = arr.length;
			String sqlLike1 = "";
			for (int i = 0; i < len; i++) {
				sqlLike1 += "SElECT id,salesAmount FROM book WHERE currprice >= ? AND currprice <= ? AND isShow = COALESCE(?,isShow) AND title like '%"+arr[i]+"%'";
				if (i != len-1) {
					sqlLike1 += " UNION ALL ";
				}
			}
			String sqlLike2 = "SELECT a.id,COUNT(a.id) num,a.salesAmount FROM (" + sqlLike1+ ") a GROUP BY a.id ORDER BY num DESC,a.salesAmount DESC";
			return sqlLike2;
		}
		return null;
	}
	
	@Override
	public Page<Book> getPageWithSearch(CriteriaBook cb,Integer isShow) throws SQLException{
		Page<Book> page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumberWithSearch(cb,isShow));
		cb.setPageNo(page.getPageNo());
		page.setList(getPageListWithSearch(cb, page.getPageSize(),isShow));
		return page;
	}

	@Override
	public long getTotalBookNumberWithSearch(CriteriaBook cb,Integer isShow) throws SQLException{
		String search = cb.getSearch();
		if(search!=null && search.length()>0){
			String reg = "\\s+";
			search = search.trim();
			String[] arr = search.split(reg);
			int len = arr.length;
			String sqlLike = this.getSearchSql(cb);
			String sql = "SELECT COUNT(*) FROM ("+sqlLike+") b";
//			String sql = "SELECT count(*) FROM book WHERE currprice >= ? AND currprice <= ? AND  AND isShow = ?";
			Object[] obj = new Object[len*3];
			for(int i = 0;i<obj.length;){
				obj[i++] = cb.getMinPrice();
				obj[i++] = cb.getMaxPrice();
				obj[i++] = isShow;
//				obj[i++] = arr[i-3];
			}
			return querySingle(sql, obj);
			
		}
		return 0;
	}
	
//	推测：在同时使用order by和limit时，MySQL进行了某些优化，将语句执行逻辑从"where——order by——limit"变成了"order by——limit——where"，
//	导致在某些情况下，同时使用order by和limit会导致查无结果（或结果数量不足）。具体出现问题与否是与表中数据有关的。!!!如果行不通，就只能变成子查询的格式了，子查询里排序，外面分页
	@Override
	public List<Book> getPageListWithSearch(CriteriaBook cb, Integer pageSize,Integer isShow) throws SQLException {
		
		String search = cb.getSearch();
		if(search!=null && search.length()>0){
			String reg = "\\s+";
			search = search.trim();
			String[] arr = search.split(reg);
			int len = arr.length;
			String sqlLike = this.getSearchSql(cb);
			String sql = "SELECT c.id,author,title,price,discount,currPrice,publishingDate,press,edition,pageNum,wordNum,"
					+ "bookSize,printDate,paper,c.salesAmount,storeNumber,cid,imgCacheUrl,isUpdate,isShow FROM "
					+ "(SELECT b.id,author,title,price,discount,currPrice,publishingDate,press,edition,pageNum,wordNum,bookSize,"
					+ "printDate,paper,b.salesAmount,storeNumber,cid,imgCacheUrl,isUpdate,isShow FROM ("+sqlLike+") b JOIN book ON b.id=book.id "
					+ "ORDER BY "+cb.getOrderField()+") c "
					+ "limit ?,?";
			
			Object[] obj = new Object[len*3+2];
			for(int i = 0;i<(obj.length-2);){
				obj[i++] = cb.getMinPrice();
				obj[i++] = cb.getMaxPrice();
				obj[i++] = isShow;
//				obj[i++] = arr[i-3];
			}
			obj[obj.length-2] = (cb.getPageNo() - 1) * pageSize;
			obj[obj.length-1] =  pageSize;
			return queryList(sql, obj);
		}
		return null;
	}
	

	@Override
	public Page<Book> getPageWithCombination(CriteriaBook cb, Book book,Integer isShow)
			throws Exception {
		Page<Book> page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumberWithCombination(cb, book,isShow));
		//此时传入page对象里的pageNo已经被纠正了，这时也应该将cb里的pageNo纠正
		cb.setPageNo(page.getPageNo());
		page.setList(getPageListWithCombination(cb, book, page.getPageSize(),isShow));
		return page;
	}

	@Override
	public long getTotalBookNumberWithCombination(CriteriaBook cb, Book book,Integer isShow)
			throws Exception {
		String sql = "SELECT count(*) FROM book WHERE currprice >= ? AND currprice <= ? AND author = COALESCE(?,author) AND title = COALESCE(?,title) "
				+ "AND press = COALESCE(?,press) AND isShow = COALESCE(?,isShow)";
		Object[] obj = {cb.getMinPrice(),cb.getMaxPrice(),book.getAuthor(),book.getTitle(),book.getPress(),isShow};
		return querySingle(sql, obj);
	}

	@Override
	public List<Book> getPageListWithCombination(CriteriaBook cb, Book book,
			Integer pageSize,Integer isShow) throws Exception {
		String sql = "SELECT a.id,a.author,a.title,a.price,a.discount,a.currPrice,a.publishingDate,a.press,a.edition,a.pageNum,a.wordNum,a.bookSize,"
				+ "a.printDate,a.paper,a.salesAmount,a.storeNumber,a.cid,a.imgCacheUrl,a.isUpdate,a.isShow FROM "
				+"(SELECT id,author,title,price,discount,currPrice,publishingDate,press,edition,pageNum,wordNum,bookSize,printDate,paper,"
				+ "salesAmount,storeNumber,cid,imgCacheUrl,isUpdate,isShow FROM book WHERE "
				+ "currprice >= ? AND currprice <= ? AND author = COALESCE(?,author) AND title = COALESCE(?,title) AND press = COALESCE(?,press) AND isShow = COALESCE(?,isShow)  "
				+ "ORDER BY "+cb.getOrderField()+") a "
				+ "limit ?,?";
		Object[] obj = {cb.getMinPrice(), cb.getMaxPrice(),book.getAuthor(),book.getTitle(),book.getPress(),isShow,
				(cb.getPageNo() - 1) * pageSize, pageSize};
		return queryList(sql, obj);
	}

	

	
	@Override
	public List<Book> getHotBookList() throws Exception {
		return null;
	}






	
	
}

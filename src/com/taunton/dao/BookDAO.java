package com.taunton.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.taunton.javabean.Book;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.TradeItem;
import com.taunton.web.CriteriaBook;
import com.taunton.web.Page;

	/**
	 * 图书接口
	 * @author 汤栋
	 *
	 */
	public interface BookDAO {
		
	/**
	 * 获取搜索的sql语句
	 * @param cb
	 * @return
	 */
	public String getSearchSql(CriteriaBook cb);
	/**
	 * 新增一本图书
	 * @param book
	 * @return
	 */
	public void addBook(Book book) throws Exception;
	/**
	 * 根据 id 获取指定 Book 对象
	 * @param id
	 * @return
	 */
	public abstract Book getBook(String id) throws Exception;

	/**
	 * 根据传入的 CriteriaBook 对象返回对应的 Page 对象
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPageWithCategory(CriteriaBook cb,Integer isShow) throws Exception;
	/**
	 * 根据传入的CriteriaBook对象返回对应的Page对象
	 * @param cb
	 * @return
	 */
	public Page<Book> getPageWithSearch(CriteriaBook cb,Integer isShow) throws Exception;
	/**
	 * 根据传入的CriteriaBook对象及Book对象返回对应的Page对象
	 * @param cb
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public Page<Book> getPageWithCombination(CriteriaBook cb,Book book,Integer isShow) throws Exception;

	/**
	 * 根据传入的 CriteriaBook 对象返回其对应的记录数
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumberWithCategory(CriteriaBook cb,Integer isShow) throws Exception;
	/**
	 * 根据传入的CriteriaBook返回对应的记录数
	 * @param cb
	 * @return
	 */
	public long getTotalBookNumberWithSearch(CriteriaBook cb,Integer isShow)  throws Exception;
	/**
	 * 根据传入的根据传入的CriteriaBook对象及Book对象返回对应的记录数
	 * @param cb
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public long getTotalBookNumberWithCombination(CriteriaBook cb,Book book,Integer isShow)  throws Exception;

	/**
	 * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的 List 
	 * @param cb
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageListWithCategory(CriteriaBook cb,Integer pageSize,Integer isShow) throws Exception;
	/**
	 * 根据传入的CriteriaBook 和 pageSize返回当前页对应的List
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public List<Book> getPageListWithSearch(CriteriaBook cb,Integer pageSize,Integer isShow) throws Exception;
	/**
	 * 根据传入的根据传入的CriteriaBook对象及Book对象返回对应的List
	 * @param cb
	 * @param book
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Book> getPageListWithCombination(CriteriaBook cb,Book book,Integer pageSize,Integer isShow) throws Exception;

	/**
	 * 返回指定 id 的 book 的 storeNumber 字段的值
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(String id) throws Exception;
	/**
	 * 获取今日热销图书（每个分类下销量最高的图书）
	 * @return
	 * @throws Exception
	 */
	public List<Book> getHotBookList() throws Exception; 

	/**
	 * 根据图书名称获取图书
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public Book getBookWithTitle(String title) throws Exception;
	
	/**
	 * 根据图书类型获取对应类型下的图书本数
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public long getBookNumWithCid(String cid) throws Exception;
	
	/**
	 * 根据传入的 TradeItem 的集合, 
	 * 批量更新 books 数据表的 storenumber字段的值
	 * s的值为"out"时为需要减少库存
	 * s的值为"in"时为需要增加库存
	 * @param tradeItems
	 */
	public abstract void batchUpdateStoreNumber(
			Collection<TradeItem> tradeItems) throws Exception;
	/**
	 * 根据传入的 TradeItem 的集合, 
	 * 批量更新 books 数据表的salesnumber 字段的值,销量只有在收货之后才会更新，因此不与库存进行同步
	 * s的值为"out"时为需要减少销量
	 * s的值为"in"时为需要增加销量
	 * @param tradeItems
	 */
	public void batchUpdateSalesAmount(
			Collection<TradeItem> tradeItems) throws Exception;
	/**
	 * 更新是否将图书展示出来的状态 (1表示true将图书展示给出来即在售,2表示false不将图书展示出来即下架)根据图书id，用作下架和重新上架使用
	 * @param id
	 * @return
	 */
	public void updateIsShowWithBookId(Integer isShow,String id) throws Exception;
	/**
	 * 更新图书
	 * @param book
	 * @throws Exception
	 */
	public void updateBook(Book book) throws Exception;
	/**
	 * 更新图书下架时间
	 * @param soldOutTime
	 * @param id
	 * @throws Exception
	 */
	public void updateSoldTimeWithBookId(String soldOutTime,String id) throws Exception;

}

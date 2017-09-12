package com.taunton.javabean;

import java.sql.Blob;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;

public class Book {
	
	private String id;
	private String author;
	private String title;
	private float price;
	private float discount;
	private float currPrice;
	private String publishingDate;
	private String press;
	private Integer edition;
	private Integer pageNum;
	private Long wordNum;
	private Integer bookSize;
	private String printDate;
	private String paper;
	private Integer salesAmount;	
	private Integer storeNumber;
	private String putAwayTime;
	private String soldOutTime;
	private String cid;
	private String imgCacheUrl;
	private Integer isUpdate;
	private Blob firstImg;
	private Integer isShow;
	private Set<Remark> remarks = new LinkedHashSet<>();
	private Category category=null;
	/**
	 * 获取该书id
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置该书id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取该书作者
	 * @return
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置该书作者
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取该书标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置该书标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取该书价格
	 * @return
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * 设置该书价格
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * 获取本书折扣
	 * @return
	 */
	public float getDiscount() {
		return discount;
	}
	/**
	 * 设置本书折扣
	 * @param discount
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	/**
	 * 获取图书当前价格
	 * @return
	 */
	public float getCurrPrice() {
		return currPrice;
	}
	/**
	 * 设置图书当前价格
	 * @param currPrice
	 */
	public void setCurrPrice(float currPrice) {
		this.currPrice = currPrice;
	}
	/**
	 * 获取该书出版日期
	 * @return
	 */
	public String getPublishingDate() {
		return publishingDate;
	}
	/**
	 * 设置该书出版日期
	 * @param publishingDate
	 */
	public void setPublishingDate(String publishingDate) {
		this.publishingDate = publishingDate;
	}
	/**
	 * 获取本书出版社
	 * @return
	 */
	public String getPress() {
		return press;
	}
	/**
	 * 设置本书出版社
	 * @param press
	 */
	public void setPress(String press) {
		this.press = press;
	}
	/**
	 * 获取图书版次
	 * @return
	 */
	public Integer getEdition() {
		return edition;
	}
	/**
	 * 设置图书版次
	 * @param edition
	 */
	public void setEdition(Integer edition) {
		this.edition = edition;
	}
	/**
	 * 获取图书页数
	 * @return
	 */
	public Integer getPageNum() {
		return pageNum;
	}
	/**
	 * 设置图书页数
	 * @param pageNum
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * 获取图书字数
	 * @return
	 */
	public long getWordNum() {
		return wordNum;
	}
	/**
	 * 设置图书字数
	 * @param wordNum
	 */
	public void setWordNum(long wordNum) {
		this.wordNum = wordNum;
	}
	/**
	 * 获取图书开页(即一本书一页的幅面大小)
	 * @return
	 */
	public Integer getBookSize() {
		return bookSize;
	}
	/**
	 * 设置图书开页(即一本书一页的幅面大小)
	 * @param bookSize
	 */
	public void setBookSize(Integer bookSize) {
		this.bookSize = bookSize;
	}
	/**
	 * 获取该书刷新时间
	 * @return
	 */
	public String getPrintDate() {
		return printDate;
	}
	/**
	 * 设置该书刷新时间
	 * @param printTime
	 */
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	/**
	 * 获取该书纸质
	 * @return
	 */
	public String getPaper() {
		return paper;
	}
	/**
	 * 设置该书纸质
	 * @param paper
	 */
	public void setPaper(String paper) {
		this.paper = paper;
	}
	/**
	 * 获取该书销量
	 * @return
	 */
	public Integer getSalesAmount() {
		return salesAmount;
	}
	/**
	 * 设置该书销量
	 * @param salesAmount
	 */
	public void setSalesAmount(Integer salesAmount) {
		this.salesAmount = salesAmount;
	}
	/**
	 * 获取该书库存
	 * @return
	 */
	public Integer getStoreNumber() {
		return storeNumber;
	}
	/**
	 * 设置该书库存
	 * @param storeNumber
	 */
	public void setStoreNumber(Integer storeNumber) {
		this.storeNumber = storeNumber;
	}
	/**
	 * 获取图书上架时间
	 * @return
	 */
	public String getPutAwayTime() {
		return putAwayTime;
	}
	/**
	 * 获取图书上架时间
	 * @param putAwayTime
	 */
	public void setPutAwayTime(String putAwayTime) {
		this.putAwayTime = putAwayTime;
	}
	/**
	 * 获取图书下架时间
	 * @return
	 */
	public String getSoldOutTime() {
		return soldOutTime;
	}
	/**
	 * 设置图书下架时间
	 * @param soldOutTime
	 */
	public void setSoldOutTime(String soldOutTime) {
		this.soldOutTime = soldOutTime;
	}
	/**
	 * 获取该书所属图书类型id
	 * @return
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * 设置该书所属图书类型id
	 * @param bookTypeid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * 获取图片在web服务器中的缓存路径
	 * @return
	 */
	public String getImgCacheUrl() {
		return imgCacheUrl;
	}
	/**
	 * 设置图片在web服务器中的缓存路径
	 * @param imgCacheUrl
	 */
	public void setImgCacheUrl(String imgCacheUrl) {
		this.imgCacheUrl = imgCacheUrl;
	}
	/**
	 * 获取图书图片的更新状态（1为true，2为false）
	 * @return
	 */
	public Integer getIsUpdate() {
		return isUpdate;
	}
	/**
	 * 设置图书图片的更新状态（1为true，2为false）
	 * @param isUpdate
	 */
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	/**
	 * 获取图书首页图片
	 * @return
	 */
	public Blob getFirstImg() {
		return firstImg;
	}
	/**
	 * 设置图书首页图片(在插入图书条目时，在dao层先把Blob类型转换成SerialBlob再插入，因为用SerialBlob数据类型插入更加方便)
	 * @param firstImg
	 */
	public void setFirstImg(Blob firstImg) {
		this.firstImg = firstImg;
	}
	/**
	 * 获取是否将图书展示出来的状态 (1表示true将图书展示给出来即在售,2表示false不将图书展示出来即下架)
	 * @return
	 */
	public Integer getIsShow() {
		return isShow;
	}
	/**
	 * 获取是否将图书展示出来的状态 (1表示true将图书展示给出来即在售,2表示false不将图书展示出来即下架)
	 * @param isShow
	 */
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取该图书下的评论条目集合
	 * @return
	 */
	public Set<Remark> getRemarks() {
		return remarks;
	}
	/**
	 * 设置该图书下的评论条目集合
	 * @param remarks
	 */
	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取图书所属类型的图书类型对象
	 * @return
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * 设置图书所属类型的图书类型对象
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	

	@Override
	public String toString() {
		return "Book [id=" + id + ", author=" + author + ", title=" + title
				+ ", price=" + price + ", discount=" + discount
				+ ", currPrice=" + currPrice + ", publishingDate="
				+ publishingDate + ", press=" + press + ", edition=" + edition
				+ ", pageNum=" + pageNum + ", wordNum=" + wordNum
				+ ", bookSize=" + bookSize + ", printDate=" + printDate
				+ ", paper=" + paper + ", salesAmount=" + salesAmount
				+ ", storeNumber=" + storeNumber + ", putAwayTime="
				+ putAwayTime + ", soldOutTime=" + soldOutTime + ", cid=" + cid
				+ ", imgCacheUrl=" + imgCacheUrl + ", isUpdate=" + isUpdate
				+ ", isShow=" + isShow + ", remarks=" + remarks + ", category="
				+ category + "]";
	}
	public Book() {
		super();
	}
	public Book(String id, String author, String title, float price,
			float discount, float currPrice, String publishingDate,
			String press, Integer edition, Integer pageNum, long wordNum, Integer bookSize,
			String printDate, String paper, Integer salesAmount, Integer storeNumber,
			String putAwayTime, String soldOutTime, String cid,
			String imgCacheUrl, Integer isUpdate, Integer isShow) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.price = price;
		this.discount = discount;
		this.currPrice = currPrice;
		this.publishingDate = publishingDate;
		this.press = press;
		this.edition = edition;
		this.pageNum = pageNum;
		this.wordNum = wordNum;
		this.bookSize = bookSize;
		this.printDate = printDate;
		this.paper = paper;
		this.salesAmount = salesAmount;
		this.storeNumber = storeNumber;
		this.putAwayTime = putAwayTime;
		this.soldOutTime = soldOutTime;
		this.cid = cid;
		this.imgCacheUrl = imgCacheUrl;
		this.isUpdate = isUpdate;
		this.isShow = isShow;
	}

	

	
}


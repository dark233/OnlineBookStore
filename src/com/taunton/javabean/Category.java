package com.taunton.javabean;

import java.util.LinkedHashSet;
import java.util.Set;

public class Category {
	private String cid;
	private String pid;
	private String cname;
	private String desc;
	private Category parent;
	private Set<Category> children = new LinkedHashSet<Category>();

	/**
	 * 获取图书类型id
	 * @return
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * 设置图书类型id
	 * @param cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * 获取图书类型父类id
	 * @return
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置图书类型父类id
	 * @param pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取图书类型名
	 * @return
	 */
	public String getCname() {
		return cname;
	}
	/**
	 * 设置图书类型名
	 * @param cname
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * 获取图书类型描述
	 * @return
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置图书类型描述
	 * @param desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取父级类型对象
	 * @return
	 */
	public Category getParent() {
		return parent;
	}
	/**
	 * 设置父级类型对象
	 * @param parent
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}
	/**
	 * 获取子级类型集合
	 * @return
	 */
	public Set<Category> getChildren() {
		return children;
	}
	/**
	 * 设置子级类型集合
	 * @param children
	 */
	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	@Override
	public String toString() {
		return "Category [cid=" + cid + ", pid=" + pid + ", cname=" + cname
				+ ", desc=" + desc + ", parent="
				+ parent + ", children=" + children + "]";
	}
	public Category() {
		super();
		
	}
	public Category(String cid, String pid, String cname, String desc) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.cname = cname;
		this.desc = desc;
	}
	
	
	
}

package com.taunton.javabean;

public class Dict {
private String dictid;
private int type;
private String desc;
private String val;
private String pid;
private String createTime;
private String updateTime;

/**
 * 获取数据字典项id
 * @return
 */
public String getDictid() {
	return dictid;
}
/**
 * 设置数据字典项id
 * @param dictid
 */
public void setDictid(String dictid) {
	this.dictid = dictid;
}
/**
 * 获取数据字典类型（如1.province，2.city。。。）
 * @return
 */
public int getType() {
	return type;
}
/**
 * 设置数据字典类型（如province，city。。。）
 * @param type
 */
public void setType(int type) {
	this.type = type;
}
/**
 * 获取数据字典描述
 * @return
 */
public String getDesc() {
	return desc;
}
/**
 * 设置数据字典描述
 * @param desc
 */
public void setDesc(String desc) {
	this.desc = desc;
}
/**
 * 获取数据字典值
 * @return
 */
public String getVal() {
	return val;
}
/**
 * 设置数据字典值
 * @param val
 */
public void setVal(String val) {
	this.val = val;
}

/**
 * 获取数据字典上级id
 * @return
 */
public String getPid() {
	return pid;
}
/**
 * 设置数据字典上级id
 * @param pid
 */
public void setPid(String pid) {
	this.pid = pid;
}
/**
 * 获取数据字典项创建时间
 * @return
 */
public String getCreateTime() {
	return createTime;
}
/**
 * 设置数据字典项创建时间
 * @param createTime
 */
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
/**
 * 获取数据字典更新时间
 * @return
 */
public String getUpdateTime() {
	return updateTime;
}
/**
 * 设置数据字典更新时间
 * @param updateTime
 */
public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
}
@Override
public String toString() {
	return "Dict [dictid=" + dictid + ", type=" + type + ", desc=" + desc
			+ ", val=" + val + ", pid=" + pid + ", createTime=" + createTime
			+ ", updateTime=" + updateTime + "]";
}
public Dict() {
	super();
	
}
public Dict(String dictid, int type, String desc, String val, String pid,
		String createTime, String updateTime) {
	super();
	this.dictid = dictid;
	this.type = type;
	this.desc = desc;
	this.val = val;
	this.pid = pid;
	this.createTime = createTime;
	this.updateTime = updateTime;
}


}

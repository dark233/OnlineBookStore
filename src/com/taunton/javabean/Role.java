package com.taunton.javabean;
/**
 * 这里的角色与传统意义上的角色不同，和它同角色名的那些用户表与其并无关系，
 * 这里的角色仅仅是对多种权限进行概括而取的一个代号！
 * @author huse_tangdong
 *
 */
public class Role {
private int roleid;
private String rolename;
private String fun_per;
/**
 * 获取当前角色的id
 * @return
 */
public int getRoleid() {
	return roleid;
}
/**
 * 设置当前角色的id
 * @param roleid
 */
public void setRoleid(int roleid) {
	this.roleid = roleid;
}
/**
 * 获取当前角色的角色名
 * @return
 */
public String getRolename() {
	return rolename;
}
/**
 * 设置当前角色的角色名
 * @param rolename
 */
public void setRolename(String rolename) {
	this.rolename = rolename;
}
/**
 * 获取当前角色拥有的功能权限
 * @return
 */
public String getFun_per() {
	return fun_per;
}
/**
 * 设置当前角色拥有的功能权限
 * @param fun_per
 */
public void setFun_per(String fun_per) {
	this.fun_per = fun_per;
}
@Override
public String toString() {
	return "Role [roleid=" + roleid + ", rolename=" + rolename + ", fun_per="
			+ fun_per + "]";
}
public Role() {
	super();
	// TODO Auto-generated constructor stub
}
public Role(int roleid, String rolename, String fun_per) {
	super();
	this.roleid = roleid;
	this.rolename = rolename;
	this.fun_per = fun_per;
}

}

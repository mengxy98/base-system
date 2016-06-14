package com.net.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public interface UserManagerDao {
	// 查询机构字典表
	public List<Map> findOrgDict();
	// 查询机构字典表
	public List<Map> finderoleName();
	//查询用户列表
	public List<Map> findUserManagermentListDao(Map<String,Object> map);
	public Integer findUserManagermentListCountDao(Map<String,Object> map);
	//添加用户
	public void addUserManagermentDao(Map map);
	//查找新插入用户的主键
	public List<Map> findPrimaryKeyDao(Map<String,Object> map);
	//往用户和角色关联的表插入相应的码值
	public void addUserAndRoleDao(Map map);
	//删除用户
	public void deleteUserManagermentDao(String userID);
	//修改用户
	public void modifyUserManagermentDao(Map map);
	//通过用户名查询用户
	public List<Map> getUserByName(Map map);
	//修改用户信息
	public void updateUserInfo(Map map);
	//修改密码
	public void updatePwd(Map map);
	
	//用于用户权限-通过用户名和密码查找用于关联的角色并查找操作类型
	public List<Map> findRoleAndOperaType(Map<String,Object> map);
	//用于用户登录时该用户所对应的角色有没有相应的菜单
	public List<Map> findUserAndMenu(Map<String,Object> map);
	//查看重复用户
	public List<Map> userRepeatDao(String userName);
	
}

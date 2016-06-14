package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.UserManagerDao;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserManagerDaoImpl implements UserManagerDao{
	@Resource
	BasicDao basicDao;
	// 查询机构字典表
	@Override
	public List<Map> findOrgDict() {
		return basicDao.queryForList("userManagerment.findOrgDict");
	}
	
	@Override
	public List<Map> finderoleName() {
		return basicDao.queryForList("userManagerment.finderoleName");
	}

	
	//查询用户列表
	@Override
	public List<Map> findUserManagermentListDao(Map<String, Object> map) {
		return basicDao.queryForList("userManagerment.userManagermentList",map);
	}
	//查询用户列表条数
	@Override
	public Integer findUserManagermentListCountDao(Map<String, Object> map) {
		return (Integer)basicDao.queryForObject("userManagerment.userManagermentListCount",map);
	}
	//添加用户
	@Override
	public void addUserManagermentDao(Map map) {
		basicDao.insert("userManagerment.addUserManagerment", map);
	}
	//查询用户主键
	@Override
	public List<Map> findPrimaryKeyDao(Map<String, Object> map) {
		return basicDao.queryForList("userManagerment.findPrimaryKey",map);
	}
	//添加用户和角色关系表
	@Override
	public void addUserAndRoleDao(Map map) {
		basicDao.insert("userManagerment.addUserAndRole", map);
	}
	//删除用户
	@Override
	public void deleteUserManagermentDao(String userID) {
		basicDao.delete("userManagerment.deleteUser", userID);
		basicDao.delete("userManagerment.deleteUserAndRole", userID);
	}
	//修改用户
	@Override
	public void modifyUserManagermentDao(Map map) {
		basicDao.update("userManagerment.modifyUser", map);
		basicDao.update("userManagerment.modifyUserAndRole", map);
	}
	
	public List<Map> getUserByName(Map map){
		return basicDao.queryForList("userManagerment.getUserByName",map);
	}
	
	public void updateUserInfo(Map map){
		basicDao.update("userManagerment.updateUserInfo", map);
	}
	
	public void updatePwd(Map map){
		basicDao.update("userManagerment.updatePwd", map);
	}
	
	//用于用户权限-通过用户名和密码查找用于关联的角色并查找操作类型
	@Override
	public List<Map> findRoleAndOperaType(Map<String, Object> map) {
		return basicDao.queryForList("userManagerment.findRoleAndOperaType",map);
	}
	//用于用户登录时该用户所对应的角色有没有相应的菜单
	@Override
	public List<Map> findUserAndMenu(Map<String, Object> map) {
		return basicDao.queryForList("userManagerment.findUserAndMenu",map);
	}
	
	//查询用户名是否重复
	@Override
	public List<Map> userRepeatDao(String userName) {
		return basicDao.queryForList("userManagerment.findUserRepeat",userName);
	}
}

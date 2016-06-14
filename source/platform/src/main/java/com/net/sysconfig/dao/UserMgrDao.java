package com.net.sysconfig.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.net.base.dao.BasicDao;
import com.net.sysconfig.fm.UserMgr;
import com.net.sysconfig.service.UserMgrService;

@Component
public class UserMgrDao extends BasicDao<UserMgr> {
	Logger logger = LoggerFactory.getLogger(UserMgrService.class);
	@SuppressWarnings("rawtypes")
	@Autowired
	BasicDao basicDao;
	
	public String check(String userName) {
		return (String) getSqlMapClientTemplate().queryForObject("UserMgr.check",userName);
	}
	/**
	 * 查询用户
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryUser(Map map){
		return basicDao.queryForList("UserMgr.queryUser", map);
	}
	/**
	 * 根据Id查询用户信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryUserById(Map map){
		return basicDao.queryForList("UserMgr.queryUserById", map);
	}
	/**
	 * 查询用户数量
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int queryUserCount(Map map){
		return (Integer) basicDao.queryForObject("UserMgr.queryUserCount", map);
	}
	/**
	 * 添加新用户
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addUser(Map map){
		basicDao.insert("UserMgr.insert", map);
	}
	/**
	 * 检查用户名是否存在
	 * @param map
	 * @return
	 */
	public String checkUserName(String user_name){
		return (String)basicDao.queryForObject("UserMgr.check", user_name);
	}
	/**
	 * 修改用户信息
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateUser(Map map){
		basicDao.update("UserMgr.update", map);
	}
	/**
	 * 删除用户
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteUser(Map map){
		basicDao.delete("UserMgr.destory", map);
	}
}
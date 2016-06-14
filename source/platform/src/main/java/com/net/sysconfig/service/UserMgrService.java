package com.net.sysconfig.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.base.dao.BasicDao;
import com.net.sysconfig.fm.UserMgr;

@Service
public class UserMgrService  {
	Logger logger = LoggerFactory.getLogger(UserMgrService.class);
	@Autowired
	BasicDao basicDao;

	public boolean Validator(UserMgr userMgr, StringBuffer subMsg) {
		boolean result = true;
        List userList = this.basicDao.queryForList("UserMgr.isExist", userMgr);
        if(userList.size() > 0){
        	logger.info("用户名["+userMgr.getUserName()+"]已存在！");
        	subMsg.append("用户名["+userMgr.getUserName()+"]已存在！");
        	result = result && false;
        }
		logger.info(subMsg.toString());
		return result;
	}
	
}

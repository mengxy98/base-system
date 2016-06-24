package com.net.base.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.CacheDataDao;

@Repository
public class CacheDataDaoImpl implements CacheDataDao {

	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public String getDevData(Object deviceId) {
		return "";
	}

}

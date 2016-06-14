package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.DictionaryManagerDao;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DictionaryManagerDaoImpl implements DictionaryManagerDao{
	@Resource
	BasicDao basicDao;
	
	@Override
	public List<Map> findDict(Map params) {
		return basicDao.queryForList("sc_dic.findTypeDict",params);
	}
	
	
}

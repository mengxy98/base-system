package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.LineManagerDao;

@Repository
public class LineManageDaoImpl implements LineManagerDao {

	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public int modefyMainData(Map<String, String> map) {
		return basicDao.update("sc_linedesign.modefyData",map);
	}

	@Override
	public int deleteMainData(String id) {
		return basicDao.delete("sc_linedesign.deleteData",id);
	}

	@Override
	public void addMainData(Map<String, String> param) {
	    basicDao.insert("sc_linedesign.addData",param);
	}

	@Override
	public Integer findMainDataListCount(Map<String, Object> map) {
		return basicDao.queryForObject("sc_linedesign.findDataListCount",map);
	}

	@Override
	public List<Map<String, Object>> findMainDataList(Map<String, Object> map) {
		return basicDao.queryForList("sc_linedesign.findDataList",map);
	}

	@Override
	public List<Map<String, Object>> getLineList() {
		return basicDao.queryForList("sc_linedesign.getLineList",null);
	}

}

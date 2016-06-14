package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.BoundaryManagerDao;

@Repository
public class BoundaryManageDaoImpl implements BoundaryManagerDao {

	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public int modefyMainData(Map<String, String> map) {
		return basicDao.update("sc_lineboundary.modefyData",map);
	}

	@Override
	public int deleteMainData(String id) {
		return basicDao.delete("sc_lineboundary.deleteData",id);
	}

	@Override
	public void addMainData(Map<String, String> param) {
	    basicDao.insert("sc_lineboundary.addData",param);
	}

	@Override
	public Integer findMainDataListCount(Map<String, Object> map) {
		return basicDao.queryForObject("sc_lineboundary.findDataListCount",map);
	}

	@Override
	public List<Map<String, Object>> findMainDataList(Map<String, Object> map) {
		return basicDao.queryForList("sc_lineboundary.findDataList",map);
	}

	@Override
	public List<Map<String, Object>> getLineList() {
		return basicDao.queryForList("sc_lineboundary.getLineList",null);
	}

}

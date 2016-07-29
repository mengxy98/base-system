package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.FaceDataManagerDao;

@Repository
public class FaceDataManageDaoImpl implements FaceDataManagerDao {

	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public int modefyMainData(Map<String, String> map) {
		return basicDao.update("sc_facePtData.modefyData",map);
	}

	@Override
	public int deleteMainData(String id) {
		return basicDao.delete("sc_facePtData.deleteData",id);
	}

	@Override
	public Object addMainData(Map<String, String> param) {
	    return basicDao.insert("sc_facePtData.addData",param);
	}
 
	@Override
	public Object addProcessMainData(Map<String, String> param) {
		 return basicDao.insert("sc_facePtData.addProcessData",param);
	}

	@Override
	public Integer findMainDataListCount(Map<String, Object> map) {
		return basicDao.queryForObject("sc_facePtData.findDataListCount",map);
	}

	@Override
	public List<Map<String, Object>> findMainDataList(Map<String, Object> map) {
		return basicDao.queryForList("sc_facePtData.findDataList",map);
	}

}

package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.PositionManagerDao;

@Repository
public class PositionManageDaoImpl implements PositionManagerDao {

	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public int modefyMainData(Map<String, String> map) {
		return basicDao.update("sc_positiondata.modefyData",map);
	}

	@Override
	public int deleteMainData(String id) {
		return basicDao.delete("sc_positiondata.deleteData",id);
	}

	@Override
	public Object addMainData(Map<String, String> param) {
	    return basicDao.insert("sc_positiondata.addData",param);
	}

	@Override
	public Integer findMainDataListCount(Map<String, Object> map) {
		return basicDao.queryForObject("sc_positiondata.findDataListCount",map);
	}

	@Override
	public List<Map<String, Object>> findMainDataList(Map<String, Object> map) {
		return basicDao.queryForList("sc_positiondata.findDataList",map);
	}

	@Override
	public List<Map<String, Object>> getPositionList() {
		return basicDao.queryForList("sc_positiondata.getPositionList",null);
	}

	@Override
	public String getPositionData(Map<String, Object> map) {
		return basicDao.queryForObject("sc_positiondata.getPositionData",map);
	}

}

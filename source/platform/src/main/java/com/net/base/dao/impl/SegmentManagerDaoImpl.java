package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.SegmentManagerDao;

@Repository
public class SegmentManagerDaoImpl implements SegmentManagerDao {
	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public List<Map<String, Object>> findSegmentManagermentListDao(Map<String, Object> map) {
		return basicDao.queryForList("segmentManagerment.findDataList",map);
	}

	@Override
	public Integer findSegmentManagermentListCount(Map<String, Object> map) {
		return basicDao.queryForObject("segmentManagerment.findDataListCount",map);
	}

	@Override
	public List<Map<String, Object>> getSegmentList() {
		return basicDao.queryForList("segmentManagerment.getSegmentList",null);
	}

	@Override
	public void addSegmentManagermentDao(Map<String, String> param) {
		 basicDao.insert("segmentManagerment.addData",param);
	}

	@Override
	public void deleteSegmentManagermentDao(String id) {
		basicDao.delete("segmentManagerment.deleteData",id);
	}

	@Override
	public void modefySegmentManagermentDao(Map<String, String> param) {
		basicDao.update("segmentManagerment.modefyData",param);
	}

}

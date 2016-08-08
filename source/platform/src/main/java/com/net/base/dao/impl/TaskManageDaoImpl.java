package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.TaskManagerDao;

@Repository
public class TaskManageDaoImpl implements TaskManagerDao {

	@Resource
	BasicDao<?> basicDao;
	
	@Override
	public int modefyMainData(Map<String, String> map) {
		return basicDao.update("sc_task.modefyData",map);
	}

	@Override
	public int deleteMainData(String id) {
		return basicDao.delete("sc_task.deleteData",id);
	}

	@Override
	public void addMainData(Map<String, String> param) {
	    basicDao.insert("sc_task.addData",param);
	}

	@Override
	public Integer findMainDataListCount(Map<String, Object> map) {
		return basicDao.queryForObject("sc_task.findDataListCount",map);
	}

	@Override
	public List<Map<String, Object>> findMainDataList(Map<String, Object> map) {
		return basicDao.queryForList("sc_task.findDataList",map);
	}

	@Override
	public List<Map<String, Object>> getTaskList() {
		return basicDao.queryForList("sc_task.getTaskList",null);
	}

	@Override
	public List<Map<String, Object>> findTaskSegInfo(Map<String, Object> map) {
		return basicDao.queryForList("sc_task.findTaskSegInfo",map);
	}

	
}

package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.net.base.dao.BasicDao;
import com.net.base.dao.DeviceManagerDao;

@Repository
public class DeviceManagerDaoImpl implements DeviceManagerDao{
	
	@Resource
	BasicDao<?> basicDao;

	@Override
	public List<Map<String, Object>> findDeviceManagermentListDao(Map<String, Object> map) {
		return basicDao.queryForList("deviceManagerment.findTypeDevice",map);
	}

	@Override
	public Integer findDeviceManagermentListCountDao(Map<String, Object> map) {
		return ((Integer)basicDao.queryForObject("deviceManagerment.findTypeDeviceCount",map)).intValue();
	}

	@Override
	public void addDeviceManagermentDao(Map<String, String> param) {
		 basicDao.insert("deviceManagerment.addDevice",param);
	}

	@Override
	public void deleteDeviceManagermentDao(String deviceId) {
		basicDao.delete("deviceManagerment.deleteDevice",deviceId);
	}

	@Override
	public void modefyDeviceManagermentDao(Map<String, String> param) {
		basicDao.update("deviceManagerment.modefyDevice",param);
	}

	@Override
	public List<Map<String, Object>> findDeviceList() {
		return basicDao.queryForList("deviceManagerment.findDeviceList",null);
	}

	@Override
	public List<Map<String, Object>> findTypeDeviceByIp(Map<String, Object> map) {
		return basicDao.queryForList("deviceManagerment.findTypeDeviceByIp",map);
	}

	@Override
	public List<Map<String, Object>> findTaskInfoByIp(Map<String, Object> map) {
		return basicDao.queryForList("deviceManagerment.findTaskInfoByIp",map);
	}
	
	
	
	
}

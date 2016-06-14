package com.net.base.dao;


import java.util.List;
import java.util.Map;

public interface DeviceManagerDao {
	/**
	 * 方法说明:取设备列表
	 * @param map
	 * @return
	 * Date: 2016年5月28日下午1:19:49
	 * @author mengxy
	 * @version 1.0
	 * @since:
	 */
	List<Map<String, Object>> findDeviceManagermentListDao(Map<String, Object> map);

	Integer findDeviceManagermentListCountDao(Map<String, Object> map);

	void addDeviceManagermentDao(Map<String, String> param);

	void deleteDeviceManagermentDao(String deviceId);

	void modefyDeviceManagermentDao(Map<String, String> param);

	List<Map<String, Object>> findDeviceList();
}

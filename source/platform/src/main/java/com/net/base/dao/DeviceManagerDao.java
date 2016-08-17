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
	
	List<Map<String, Object>> findTypeDeviceByIp(Map<String, Object> map);

	List<Map<String, Object>> findTaskInfoByIp(Map<String, Object> map);

	String getDeviceData(Map<String, Object> map);

	void addDevTask(Map<String, String> param);

	String getDevTask(String deviceId);

	String getAllDevInfo(Map<String, Object> map);

	String getAllPhoneOwnerInfo(Map<String, Object> map);

	String getPhoneOwnerByDevId(Map<String, Object> map);

	boolean setDevDataNew(int deviceId, String dataList);

	List<Map<String, Object>> getDeviceInfoByTaskId(Map<String, Object> map);

	String setDevDataSingle(int deviceId, String dataList);
	
}

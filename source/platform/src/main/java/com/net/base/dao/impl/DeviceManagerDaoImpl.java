package com.net.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.net.base.ThreadPool.ThreadPoolManager;
import com.net.base.dao.BasicDao;
import com.net.base.dao.DeviceManagerDao;
import com.net.base.dao.FaceDataManagerDao;
import com.net.base.dao.PositionManagerDao;
import com.net.base.util.TransData;

@Repository
public class DeviceManagerDaoImpl implements DeviceManagerDao{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	BasicDao<?> basicDao;
	
	@Autowired
	PositionManagerDao positionManagerDao;
	
	@Autowired
	FaceDataManagerDao faceDataManagerDao;

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

	@Override
	public String getDeviceData(Map<String, Object> map) {
		return basicDao.queryForObject("deviceManagerment.getDeviceData",map);
	}

	@Override
	public void addDevTask(Map<String, String> param) {
		basicDao.update("deviceManagerment.addDevTask",param);
		
	}

	@Override
	public String getDevTask(String deviceId) {
		return basicDao.queryForObject("deviceManagerment.getDevTask",deviceId);
	}
	
	/**
	 * 所有新开发的接口
	 */
	@Override
	public String getAllDevInfo(Map<String, Object> map) {
		List<Map<String, Object>> returnMap = basicDao.queryForList("deviceManagerment.getAllDevInfo",map);
		JSONArray reJsonArray = JSONArray.fromObject(returnMap);
		return reJsonArray.toString(); 
	}

	@Override
	public String getAllPhoneOwnerInfo(Map<String, Object> map) {
		List<Map<String, Object>> returnMap = basicDao.queryForList("deviceManagerment.getAllPhoneOwnerInfo",map);
		JSONArray reJsonArray = JSONArray.fromObject(returnMap);
		return reJsonArray.toString(); 
	}

	@Override
	public String getPhoneOwnerByDevId(Map<String, Object> map) {
		List<Map<String, Object>> returnMap = basicDao.queryForList("deviceManagerment.getPhoneOwnerByDevId",map);
		JSONArray reJsonArray = JSONArray.fromObject(returnMap);
		return reJsonArray.toString(); 
	}

	@Override
	public List<Map<String, Object>> getDeviceInfoByTaskId(Map<String, Object> map) {
		return basicDao.queryForList("deviceManagerment.getDeviceInfoByTaskId",map); 
	}

	@Override
	public boolean setDevDataNew(int deviceId, final String dataList) {
		try {
			ThreadPoolManager.getInstance().execute(new Runnable() {
				@Override
				public void run() {
					String[] data = dataList.split(";");
					for (int i = 0; i < data.length; i++) {
						try {
							//定位数据
							String[] column={"taskId","deviceId","longitude","latitude","elevation","X","Y","Z",
									"speed","satelliteTime","direction","GPSStatus","compactId","CMV","RMV","frequency",
									 "F1","F2","F3","temperature","angle","sensor","imageAddress","serverTime"};
							Map<String, String> param = TransData.transData(data[i],column);
							param.put("isValid", "1");
							String keyId = positionManagerDao.addMainData(param).toString();
							//表层数据表
							param.put("divNum", "1");
							param.put("thickness", "1");
							param.put("positionId",keyId);
							String faceId = (String)faceDataManagerDao.addMainData(param).toString();
							//过程数据对应表层点表 
							param.put("RPId",faceId);
							faceDataManagerDao.addProcessMainData(param);
						} catch (Exception e1) {
							logger.error(e1.getMessage());
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public String setDevDataSingle(int deviceId,String dataList) {
		try {
			//定位数据
			String[] column={"taskId","deviceId","longitude","latitude","elevation","X","Y","Z",
					"speed","satelliteTime","direction","GPSStatus","compactId","CMV","RMV","frequency",
					 "F1","F2","F3","temperature","angle","sensor","imageAddress","serverTime"};
			Map<String, String> param = TransData.transData(dataList,column);
			param.put("isValid", "1");
			String keyId = positionManagerDao.addMainData(param).toString();
			return keyId;
		} catch (Exception e1) {
			logger.error(e1.getMessage());
			return "";
		}	
	}
	
}

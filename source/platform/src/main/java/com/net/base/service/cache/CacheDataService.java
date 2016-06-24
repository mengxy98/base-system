package com.net.base.service.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.base.dao.CacheDataDao;
import com.net.base.dao.PositionManagerDao;
import com.net.base.memcache.BaseMemcache;
import com.net.base.memcache.ModelType;
import com.net.base.util.TransData;

@Service
public class CacheDataService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CacheDataDao cacheDataDao;
	
	@Autowired
	PositionManagerDao positionManagerDao;
	
	@Autowired
	private BaseMemcache baseMemcache;
	
	public String getDevData(Object deviceId){
		String mess="";
		try {
			String o = baseMemcache.getCacheData(ModelType.DEVICE_MANAGER, deviceId);
			if (null == o || o.length()<1) { //代表缓存中没有这个数据
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("deviceId", deviceId);
				mess = positionManagerDao.getPositionData(param);
				baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, deviceId ,mess);
			}else {
				//代表缓存中存在缓存的对应数据，从缓存中获取对应数据
				mess = o.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return mess;
	}

	public boolean setDevData(Object deviceId,String dataList){
		try {
			String[] column={"CMV","frequency","divNum","satelliteTime","RTKfixed","speed","elevation","longitude","latitude","taskId","X","Y","Z","direction","GPSStatus","compactId",
					"RMV","F1","F2","F3","temperature","angle","sensor","imageAddress","serverTime","isValid"};
			Map<String, String> param = TransData.transData(dataList,column);
			param.put("deviceId", deviceId.toString());
			try {
				positionManagerDao.addMainData(param);
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				return false;
			}
			return baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, deviceId ,dataList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		
	}


	public String getTaskAllDeviceData(int tid) {
		//获取任务下的所有设备id，后循环拼接数据
		return getDevData(tid);
	}
}

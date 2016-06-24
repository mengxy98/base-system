package com.net.base.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.base.dao.CacheDataDao;
import com.net.base.memcache.BaseMemcache;
import com.net.base.memcache.ModelType;

@Service
public class CacheDataService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CacheDataDao cacheDataDao;
	
	@Autowired
	private BaseMemcache baseMemcache;
	
	public String getDevData(Object deviceId){
		String mess="";
		try {
			String o = baseMemcache.getCacheData(ModelType.DEVICE_MANAGER, deviceId);
			if (null == o) { //代表缓存中没有这个数据
				mess = cacheDataDao.getDevData(deviceId);
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
			return baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, deviceId ,dataList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		
	}


	public String getTaskAllDeviceData(int tid) {
		// TODO Auto-generated method stub
		return null;
	}
}

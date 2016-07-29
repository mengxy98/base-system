package com.net.base.service.cache;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.base.ThreadPool.ThreadPoolManager;
import com.net.base.dao.CacheDataDao;
import com.net.base.dao.FaceDataManagerDao;
import com.net.base.dao.PositionManagerDao;
import com.net.base.memcache.BaseMemcache;
import com.net.base.memcache.ModelType;
import com.net.base.util.TransData;

@Service
public class CacheDataService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Map<String, Boolean> openFlagMap = new HashMap<String, Boolean>();
	
	@Autowired
	private CacheDataDao cacheDataDao;
	
	@Autowired
	PositionManagerDao positionManagerDao;
	
	@Autowired
	FaceDataManagerDao faceDataManagerDao;
	
	@Autowired
	private BaseMemcache baseMemcache;
	
	/**
	 * 方法说明:
	 * @param deviceId
	 * @return
	 * Date: 2016年7月21日下午3:03:30
	 * @author mengxy
	 * @version 1.0
	 * @since:
	 */
	public String getDevData(Object deviceId){
		String mess="";
		try {
			/**
			 * 如果数据开关是开的，取缓存数据，如果是关闭状态，就是直接返回null数据
			 */
			if(null != openFlagMap.get(ModelType.DEVICE_MANAGER+deviceId) && openFlagMap.get(ModelType.DEVICE_MANAGER+deviceId)){
				String o = baseMemcache.getCacheData(ModelType.DEVICE_MANAGER, deviceId);
				if (null != o && o.length() > 0) { //代表缓存中没有这个数据
					mess = o.toString();
				}
				/**
				 * 防止开关冲突，数据丢失
				 */
				synchronized (openFlagMap) {
					openFlagMap.put(ModelType.DEVICE_MANAGER+deviceId,false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return mess;
	}


	public String getTaskAllDeviceData(int tid) {
		//获取任务下的所有设备id，后循环拼接数据
		return getDevData(tid);
	}
	
	
	public String getDeviceData(int deviceId) {
		//获取任务下的所有设备id，后循环拼接数据
		return getDevData(deviceId);
	}

	public boolean setDevDataNew(final int deviceId, final String dataList) {
		/**
		 * 求取内差点，获取内差计算后的数据列表
		 * 1  拆分数据，把原始数据里面的x,y坐标取出来，放到缓存原始点的队列里面 Map<devId,Liekedlist<点>
		 * 2 根据原始点，计算所有的原始点
		 * 3 把返回的所有内差点，存储缓存，注意，缓存数据的最简化数据，保证带宽
		 * 4 开启多线程，插入数据的，注意表层点的定位id是一样的
		 */
		/***第一步实现**/
		/***第二部实现**/
		/***第三部实现**/
		//打开设备取数据开关，避免数据的重复取数据，节省带宽
		if(null == openFlagMap.get(ModelType.DEVICE_MANAGER+deviceId) || !openFlagMap.get(ModelType.DEVICE_MANAGER+deviceId)){
			openFlagMap.put(ModelType.DEVICE_MANAGER+deviceId,true);
		}
		boolean reMes = baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, deviceId ,dataList);
		/***第四部实现**/
		try {
			/**开启线程对数据库进行数据的相对应插入*/
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
			reMes = false;
		}
		return reMes;
	}


	/**
	 * 方法说明:获取多台设备数据
	 * @param deviceIds
	 * @return
	 * Date: 2016年7月22日上午10:36:16
	 * @author mengxy
	 * @version 1.0
	 * @since:
	 */
	public String getMultiDevData(int[] deviceIds) {
		Map<String, String> returnData = new HashMap<String, String>();
		for (int i = 0; i < deviceIds.length; i++) {
			returnData.put("DEVICE"+deviceIds[i], getDevData(deviceIds[i]));
		}
		if (returnData.size()>0) {
			JSONObject jsonObject = JSONObject.fromObject(returnData);
			return jsonObject.toString();
		}
		return "";
	}
	
	
}

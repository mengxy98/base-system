package com.net.base.service.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.record.PrecisionRecord;
import org.omg.CORBA.OBJ_ADAPTER;
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
import com.net.base.service.Point;
import com.net.base.util.TransData;

@Service
public class CacheDataService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Map<String, Boolean> openFlagMap = new HashMap<String, Boolean>();
	private static Map<String, LinkedList<String>> neicha = new HashMap<String, LinkedList<String>>();
	private static int gap = 2;//像素间隔20一个单元
	private static int precision = 10000; //页面1像素代表实际100米=10000cm
	private static int wheelWidth = 5;      //轮宽是10像素(也就是轮子压5个格子)，轮宽一半是5，，
	
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

	/**
	 * 不计算内差
	 */
	public boolean setDevDataNew(final int deviceId, final String dataList) {
		//打开设备取数据开关，避免数据的重复取数据，节省带宽
		if(null == openFlagMap.get(ModelType.DEVICE_MANAGER+deviceId) || !openFlagMap.get(ModelType.DEVICE_MANAGER+deviceId)){
			openFlagMap.put(ModelType.DEVICE_MANAGER+deviceId,true);
		}
		boolean reMes = baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, deviceId ,dataList);
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
			reMes = false;
		}
		return reMes;
	}
	
	/**计算内差
	 * @param deviceId
	 * @param dataList
	 * @return
	 */
	public boolean setDevDataNew2(final int deviceId, final String dataList) {
		/**
		 * 求取内差点，获取内差计算后的数据列表
		 * 1  拆分数据，把原始数据里面的x,y坐标取出来，放到缓存原始点的队列里面 Map<devId,Liekedlist<点>
		 * 2-4实现是开启定时任务，间隔1秒处理一次数据
		 */
		/***第一步实现**/
		String[] data = dataList.split(";");
		for (String mess : data) {
			LinkedList<String> ldata = neicha.get(ModelType.DEVICE_MANAGER+deviceId);
			if(null == ldata || ldata.size() < 25){  //5次数据不取走，自动清空队列
				ldata.add(mess);
			}else{
				ldata.clear();
			}
			neicha.put(ModelType.DEVICE_MANAGER+deviceId, ldata);
		}
		return true;
	}
	
	/**
	 * 调度计算内差
	 * 2 根据原始点，计算所有的原始点；但是没法保证计算的内差是当前上传数据的内差
	 * 3 把返回的所有内差点，存储缓存，注意，缓存数据的最简化数据，保证带宽
	 * 4 开启多线程，插入数据的，注意表层点的定位id是一样
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void calculateDiff(){
		/***第二部实现  pop一个点，getFirst一个点，实现起来就不用缓存pre，curr，next点带来的多余缓存**/
		Iterator iter = neicha.entrySet().iterator();
		while(iter.hasNext()){
			try {
				Map.Entry entry = (Map.Entry )iter.next();  //这里遍历的是所有缓存设备的原始数据
				String key = entry.getKey().toString();
				LinkedList<String> value = (LinkedList<String>)entry.getValue();
				if(value.size()>1){  //一个原始记录的时候不计算
					String preString = value.pop();
					String currString = value.getFirst();
					String preArray[] = preString.split(",");
					String currArray[] = currString.split(",");
					//计算2个点的所有内差点
					Object[] neichaPoint = cauculatePoint(preArray[5],preArray[6],currArray[5],currArray[6]);
					if (null != neichaPoint) { //如果==null 计算内差失败，主动抛弃点，直接跳过
						//拼接所有内差数据，注意这里的x，y坐标现在是像素点，也可以是原始内差的厘米点
						/***第三部实现**/
						//打开设备取数据开关，避免数据的重复取数据，节省带宽
						if(null == openFlagMap.get(ModelType.DEVICE_MANAGER+key) || !openFlagMap.get(ModelType.DEVICE_MANAGER+key)){
							openFlagMap.put(ModelType.DEVICE_MANAGER+key,true);
						}
						boolean reMes = baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, key ,"*******这里是所有内差的数据**********");
						/***第四部实现**/
						try {
							/**开启线程对数据库进行数据的相对应插入*/
							/*ThreadPoolManager.getInstance().execute(new Runnable() {
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
							});*/
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getMessage());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
	/**
	 *  "634580.7830421","3219202.3645062", //这是厘米cm级的数据  1m=100cm
	    "X"=63px           "Y"=322px     	
	 * @param strprex
	 * @param strprey
	 * @param strcurrx
	 * @param strcurry
	 * @return
	 */
	private Object[] cauculatePoint(String strprex, String strprey, String strcurrx, String strcurry) {
		try {//对应像素点
			int prex = Math.round(Float.parseFloat(strprex)/precision);
			int prey = Math.round(Float.parseFloat(strprey)/precision);
			int currx = Math.round(Float.parseFloat(strcurrx)/precision);
			int curry = Math.round(Float.parseFloat(strcurry)/precision);
			//三种形式，  平行  垂直  斜线
			//平行
			if(prey == curry){
				for (int i = Math.min(prex, currx); i < Math.max(prex, currx); i+=gap) {
					for (int j = Math.min(curry-wheelWidth, curry+wheelWidth); j < Math.max(curry-wheelWidth, curry+wheelWidth); j+=gap) {
						
					}
				}
			}else if (prex==currx) { //垂直
				for (int i = Math.min(prey, curry); i < Math.max(prey, curry); i+=gap) {
					for (int j = Math.min(currx-wheelWidth, currx+wheelWidth); j < Math.max(currx-wheelWidth, currx+wheelWidth); j+=gap) {
						
					}
				}
			}else { //斜线
				//取得弧度
				double a = (curry - prey)/(currx - prex);
                //垂线的斜率
				double va = -1/a;
                //根据点prevPos、currPos分别求出经过两点的直线方程
				double b = prey-va*prex;//第一个点
				double b1 = curry-va*curry;//第二个点

                //根据直线方程分别求出矩形的四个点
                double dx = Math.pow(Math.pow(wheelWidth,2)/(Math.pow(va,2) + 1), 0.5);

                //取得前一个点和当前点的矩形的四个点
                /**
	                 * x:Math.round(prevPos.x - dx),
	                   y:Math.round((prevPos.x - dx) * va + b),
	
	                x1:Math.round(prevPos.x + dx),
	                y1:Math.round((prevPos.x + dx) * va + b),
					
					x2:Math.round(currPos.x + dx),
	                y2:Math.round((currPos.x + dx) * va + b1),
					
	                x3:Math.round(currPos.x - dx),
	                y3:Math.round((currPos.x - dx) * va + b1),
                 */
                //循环遍历内差点
                
             

			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
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

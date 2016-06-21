package com.net.base.memcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;

@Service
public class BaseMemcache {
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	public boolean inputCacheData(Object id,String dataList){
		return inputData(null,id, dataList);
	};
	
	public boolean inputCacheData(String model,Object id,String dataList){
		return inputData(model,id,dataList);
	};
	
	private boolean inputData(String model,Object key,String dataList){
		if(model==null || model.length()==0)model=ModelType.COMMON_MANAGER;
		memCachedClient.setPrimitiveAsString(true);
		return memCachedClient.set(model+key, dataList);
	};
	
	
	public String getCacheData(String model,Object id){
		return getData(model,id);
	};
	public String getCacheData(Object id){
		return getData(null,id);
	};
	private String getData(String model,Object id){
		if(model==null || model.length()==0)model=ModelType.COMMON_MANAGER;
		Object o = memCachedClient.get(model+id);
		if (null == o) { //代表缓存中没有这个数据
			return "";
		}else {
			return o.toString();
		}
	};
	
	
}
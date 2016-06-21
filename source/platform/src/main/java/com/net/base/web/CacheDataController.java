package com.net.base.web;


import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.memcache.BaseMemcache;
import com.net.base.memcache.ModelType;


@RequestMapping(value="/cache")
@Controller
public class CacheDataController {
	
	@Autowired
	private BaseMemcache baseMemcache;
	
	@RequestMapping(value="/getDevData.do",method=RequestMethod.GET)
	@ResponseBody
	public String getDevData(Object id){
		String mess="";
		try {
			if (null == id)return "请传入id";
			/**
			 * 先判断缓存中是否有要取的数据，有的话就取缓存中的数据，没有就调用jdbc取数据库的数据
			 */
			String o = baseMemcache.getCacheData(ModelType.DEVICE_MANAGER, id);
			if (null == o) { //代表缓存中没有这个数据
				mess = "取到的是数据库的数据"+id;
				baseMemcache.inputCacheData(ModelType.DEVICE_MANAGER, id ,mess);
			}else {
				//代表缓存中存在缓存的对应数据，从缓存中获取对应数据
				mess = o.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transformData(mess);
	}
	
	public String transformData(String mess) {
		try {
			return new String(mess.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return mess;
	    }
	}
}

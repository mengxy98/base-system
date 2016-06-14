package com.net.base.web;


import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whalin.MemCached.MemCachedClient;


@RequestMapping(value="/memcache")
@Controller
public class Test {
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	static int i = 1;
	
	@RequestMapping(value="/judge.do",method=RequestMethod.GET)
	@ResponseBody
	public String returnJsp(Long id){
		String mess="";
		try {
			if (null == id || id < 0)return "请传入id";
			/**
			 * 先判断缓存中是否有要取的数据，有的话就取缓存中的数据，没有就调用jdbc取数据库的数据
			 */
			System.out.println("memcache缓存的测试"+i++);
			// 基本类型tostring方法
			// 通常不需要设置
			memCachedClient.setPrimitiveAsString(true);
			Object o = memCachedClient.get(id.toString());
			if (null == o) { //代表缓存中没有这个数据
				String name = id+"数据库数据"; //存在就返回对应数据，不存在默认返回的是mxy
				mess = "取到的是数据库的数据"+id;
				System.out.println("缓存添加数据"+id+memCachedClient.add(id.toString(), name)); //加入到缓存客户端中
			}else {
				//代表缓存中存在缓存的对应数据，从缓存中获取对应数据
				mess = "取到的是缓存的数据"+o.toString();
				System.out.println("......取到的缓存数据"+o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return new String(mess.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return mess;
	    }
	}
}

package com.net.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransData {

	public static Map<String,String> transData(String mess,String[] column){
		Map<String,String> returnMap = new HashMap<String, String>();
		String[] dataStrings = mess.split(",");
		for (int i = 0; i < column.length; i++) {
			try {
				returnMap.put(column[i], dataStrings[i]);
			} catch (Exception e) {
				returnMap.put(column[i],null);
			}
		}
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		returnMap.put("createTime", createDate);
		return returnMap;
		
	}
}

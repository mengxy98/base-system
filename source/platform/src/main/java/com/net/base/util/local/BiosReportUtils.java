package com.net.base.util.local;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.ui.ModelMap;

public class BiosReportUtils {
	public static String getParamsFromURL(String query) throws UnsupportedEncodingException{
		return URLDecoder.decode(query, "utf8").replaceAll("&", ";");
	}
	public static String getParamsFromMap(ModelMap map) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		for(String key :map.keySet()){
			sb.append(";").append(key).append("=").append(map.get(key));
		}
		return sb.toString();
	}
}

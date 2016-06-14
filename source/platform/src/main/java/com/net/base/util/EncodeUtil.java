package com.net.base.util;

public class EncodeUtil {
	@SuppressWarnings("unused")
	public static String encode(String target){
		
		byte[] bytes = (target+"we will be encoded").getBytes();
		
		String bytesStr = "";
		for(int i=0;i<bytes.length;i++){
			bytesStr += bytes[i];
		}
		
		return bytesStr;
	}
	
	public static void main(String[] args){
		encode("hahaha");
	}
}

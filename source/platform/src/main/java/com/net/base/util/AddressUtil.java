package com.net.base.util;

import java.io.StringWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


public class AddressUtil {
	public static String ipmatch = "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])";
	public static String domainmatch = "([\\w-]+\\.)+((com)|(net)|(org)|(gov\\.cn)|(info)|(cc)|(com\\.cn)|(net\\.cn)|(org\\.cn)|(name)|(biz)|(tv)|(cn)|(mobi)|(name)|(sh)|(ac)|(io)|(tw)|(com\\.tw)|(hk)|(com\\.hk)|(ws)|(travel)|(us)|(tm)|(la)|(me\\.uk)|(org\\.uk)|(ltd\\.uk)|(plc\\.uk)|(in)|(eu)|(it)|(jp))";
	public static Pattern ipPattern=  Pattern.compile(ipmatch,Pattern.CASE_INSENSITIVE);
	public static Pattern domainPattern=  Pattern.compile(domainmatch,Pattern.CASE_INSENSITIVE);
	public static Pattern mailPattern=  Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);
	
	public static boolean match(String url,Pattern pattern)
	{
		return pattern.matcher(url).matches();
	}
	
	//是否是IP格式
	public static boolean isIp(String url)
	{
		return match(url,ipPattern);
	}
	//是否域名格式
	public static boolean isDomain(String url)
	{
		return match(url,domainPattern);
	}
	//是否mail格式
	public static boolean isMail(String url)
	{
		return match(url,mailPattern);
	}
	
	private static byte pos []= new byte []{(byte)128,64,32,16,8,4,2,1};
	public static String getMask(String startIP,String endIP){
        byte start [] = getAddress(startIP);
        byte end   [] = getAddress(endIP);
        byte mask  [] = new byte [start.length];
        boolean flag=false;
        for(int i=0;i<start.length;i++){
            mask[i]=(byte)~(start[i]^end[i]);
            if(flag)mask[i]=0;
            if(mask[i]!=-1){
                mask[i]=getMask(mask[i]);
                flag=true;
            }
        }
        return toString(mask);
    }
	private static byte getMask(byte b) {
        if(b==0)return b;
        byte p = pos[0];
        for(int i=0;i<8;i++){
            if((b&pos[i])==0)break;
            p=(byte)(p>>1);
        }
        p=(byte)(p<<1);
        return (byte)(b&p);
    }
	public static String toString(byte[] address) {
        StringWriter sw = new StringWriter(16);
        sw.write(Integer.toString(address[0]&0xFF));
        for(int i=1;i<address.length;i++){
            sw.write(".");sw.write(Integer.toString(address[i]&0xFF));
        }
        return sw.toString();
    }
	private static byte[] getAddress(String address) {
        String subStr [] = address.split("\\.");
        if(subStr.length!=4)throw new IllegalArgumentException("所传入的IP地址不符合IPv4的规范");
        byte b [] = new byte [4];
        for(int i=0;i<b.length;i++)b [i]=(byte)Integer.parseInt(subStr[i]);
        return b;
    }
	
	 /**
	  * 在很多应用下都可能有需要将用户的真实IP记录下来，这时就要获得用户的真实IP地址，在JSP里，获取客户端的IP地
	  * 址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。但是在通过了Apache,Squid等
	  * 反向代理软件就不能获取到客户端的真实IP地址了。
	  * 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
	  * @param request
	  * @return
	  */

	public static String getClientIp(HttpServletRequest request) {
               String ip = request.getHeader("X-Forwarded-For");
	           
	           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getHeader("Proxy-Client-IP");
	           }
	           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getHeader("WL-Proxy-Client-IP");
	           }
	           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getHeader("HTTP_CLIENT_IP");
	           }
	           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	           }
	           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        	   ip = request.getHeader("X-Real-IP");
	           }
	           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	               ip = request.getRemoteAddr();
	           }
	          
	           if(StringUtils.isNotBlank(ip)) {
	               ip = ip.split(",")[0];
	           }
	           return ip;
	          

	 }
	
	public static void main(String[] args) {
		// System.out.println(AddressUtil.getMask("192.168.3.1", "192.168.3.15"));
	}
}
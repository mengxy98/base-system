/**
 * 
 */
package com.net.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:<br>
 * Origin Time: 2009-7-10 下午02:59:51<br>
 * 
 * @author Seraph<br>
 * @email:seraph115@gmail.com<br>
 */
public class ResponseUtils {
	
	public static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

	public static final String DEFAULT_CHARSET = "utf-8";

	/**
	 * Method: 设置Ajax返回类型的响应头<br>
	 * Origin Time: 2009-7-10 下午03:02:22<br>
	 * 
	 * @author: Seraph<br>
	 * @param response
	 */
	public static void setJsonHeader(HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Content-Type", "text/xml; charset="
				+ DEFAULT_CHARSET);
		// This method need servlet-api2.4.jar provided
		response.setCharacterEncoding(DEFAULT_CHARSET);
	}

	/**
	 * Method: 设置Ajax返回类型的响应头<br>
	 * Origin Time: 2009-7-10 下午03:02:16<br>
	 * 
	 * @author: Seraph<br>
	 * @param response
	 * @param charset
	 */
	public static void setJsonHeader(HttpServletResponse response,
			String charset) {
		if (charset == null || "".equals(charset.trim())) {
			charset = DEFAULT_CHARSET;
		}
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Content-Type", "text/xml; charset=" + charset);
		// This method need servlet-api2.4.jar provided
		response.setCharacterEncoding(charset);
	}
	
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String userAgent = request.getHeader("USER-AGENT");
        boolean isMSIE = ((userAgent != null && userAgent.indexOf("MSIE") != -1 ) || ( null != userAgent && -1 != userAgent.indexOf("like Gecko"))); 
        try {
            String finalFileName = null;
            if(isMSIE){//IE浏览器StringUtils.contains(userAgent, "MSIE")
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        } catch (UnsupportedEncodingException e) {
        	logger.info(e.toString());
        }
    }
	
	public static String toUtf8String(String s){ 
	     StringBuffer sb = new StringBuffer(); 
	       for (int i=0;i<s.length();i++){ 
	          char c = s.charAt(i); 
	          if (c >= 0 && c <= 255){sb.append(c);} 
	        else{ 
	        byte[] b; 
	         try { b = Character.toString(c).getBytes("utf-8");} 
	         catch (Exception ex) { 
	             System.out.println(ex); 
	                  b = new byte[0]; 
	         } 
	            for (int j = 0; j < b.length; j++) { 
	             int k = b[j]; 
	              if (k < 0) k += 256; 
	              sb.append("%" + Integer.toHexString(k).toUpperCase()); 
	              } 
	     } 
	  } 
	  return sb.toString(); 
	}


}

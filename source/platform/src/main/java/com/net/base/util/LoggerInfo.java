package com.net.base.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * @author jinzhao.gong
 * 
 */

public class LoggerInfo{
	
  private static final Log logger = LogFactory.getLog(LoggerInfo.class.getName());
	 	
  public static  void getInfoLogger(String info,int type,Exception ex){
	  if(type==0){
		  logger.error(info,ex);
	  } else if(type==1){
		  logger.debug(info,ex);
	  }else if(type==2){
		  logger.info(info,ex);
	  }
  }
  
  public static  void getInfoLogger(String info,int type){
	  if(type==0){
		  logger.error(info);
	  }else if(type==1){
		  logger.debug(info);
	  }else if(type==2){
		  logger.info(info);
	  }
  }
  
}

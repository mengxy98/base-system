package com.net.base.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.ObjectUtils.Null;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JsonUtils {
	/**
	 * 返回加了双引号的该字符串
	 * @param key
	 * @return
	 */
	public static String keyJson(String key){
		String replaceKey = key;
		replaceKey = replaceKey.replace("\"", "\\\"");
		replaceKey = replaceKey.replace(":", "\\:");
		return "\"" + replaceKey + "\"" ;
	}
	
	public static String keyJson(String key,Integer v){
		return "\"" + key + "\":" + v;
	}
	
	public static String keyJson(String key,Long v){
		return "\"" + key + "\":" + v;
	}
	
	public static String keyJson(String key,Float v){
		return "\"" + key + "\":" + v;
	}
	
	public static String keyJson(String key,Double v){
		return "\"" + key + "\":" + v;
	}
	
	public static String keyJson(String key,String v){
		return "\"" + key + "\":\"" + v + "\"";
	}
	
	public static String keyJson(String key,boolean b){
		return "\"" + key + "\":" + b;
	}
	
	/**
	 * 从json数组中解析出值对象
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getList4Json(String jsonString, Class pojoClass){
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List list = new ArrayList();
		for ( int i = 0 ; i<jsonArray.size(); i++){
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject,pojoClass);
			
			list.add(pojoValue);
		}
		return list;
	}
	
    /**
     * 将java对象转换成json字符串
     * @param javaObj
     * @return
     */
    public static String getJsonString4JavaPOJO(Object javaObj){
        
        JSONObject json;
        json = JSONObject.fromObject(javaObj);
        return json.toString();
        
    }
    /**
     * list对象转换为表格的JSON串
     * @param list
     * @return
     */
	public static String getJson4Grid(List<?> list){
		String json = "[";
		for(int i = 0; i < list.size(); i++){
			json += JsonUtils.getJsonString4JavaPOJO(list.get(i));
			if(i < list.size() - 1){
				json += ",";
			}
		}
		json += "]";
		return json;
	}
	/**
	 * 将对象转换为JSON串
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> String getJson4Object(Class<T> clazz){
		String json = "";
		json += JsonUtils.getJsonString4JavaPOJO(clazz);
		return json;
	}
	/**
	 * 将JSONObject转换为Map<String,String>
	 * @param jsonObject
	 * @return
	 */
	public static Map<String,String> getJSONObject2Map(JSONObject jsonObject){
		Map<String,String> properties = new HashMap<String,String>();
		for(Iterator keys = jsonObject.keys(); keys.hasNext();){
			String key = (String) keys.next();
			properties.put(key, jsonObject.getString(key));
		}
		return properties;
	}
	
	/**
	 * json串转Map List 。不保证顺序与插入时一致。
	 * @param json
	 * @return
	 */
	public static List<Map<String,String>> getJson4List(String json){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		JSONObject jsonObject;
		JSONArray jsonArray = JSONArray.fromObject(json);
		for(int i = 0; i < jsonArray.size(); i++){
			jsonObject = jsonArray.getJSONObject(i);
			Map<String,String> props = JsonUtils.getJSONObject2Map( jsonObject );
			list.add(props);
		}
		return list;
	}
	
	/**
	 * json串转ListOrderedMap 。保证顺序与插入时一致。
	 * @param json
	 * @return
	 */
	public static List<ListOrderedMap> getJson4ListOrderedMap(String json){
		List<ListOrderedMap> list = new ArrayList<ListOrderedMap>();
		JSONObject jsonObject;
		JSONArray jsonArray = JSONArray.fromObject(json);
		for(int i = 0; i < jsonArray.size(); i++){
			jsonObject = jsonArray.getJSONObject(i);
			ListOrderedMap properties = new ListOrderedMap();
			for(Iterator keys = jsonObject.keys(); keys.hasNext();){
				String key = (String) keys.next();
				properties.put(key, jsonObject.getString(key));
			}
			
			list.add(properties);
		}
		return list;
	}
	
	/**
	 * json串转ListOrderedMap 。保证顺序与插入时一致且键是小写的。
	 * @param json
	 * @return
	 */
	public static List<ListOrderedMap> getLowerKeyJson4ListOrderedMap(String json){
		List<ListOrderedMap> list = new ArrayList<ListOrderedMap>();
		JSONObject jsonObject;
		JSONArray jsonArray = JSONArray.fromObject(json);
		for(int i = 0; i < jsonArray.size(); i++){
			jsonObject = jsonArray.getJSONObject(i);
			ListOrderedMap properties = new ListOrderedMap();
			for(Iterator keys = jsonObject.keys(); keys.hasNext();){
				String key = (String) keys.next();
				properties.put(key.toLowerCase(), jsonObject.getString(key));
			}
			
			list.add(properties);
		}
		return list;
	}
	/**
	 * 将Map<String,String>对象转换为json串
	 * @param map
	 * @return
	 */
	public static String getJson4Map(Map<String,String> map){
		Set<String> keys = map.keySet();
	    String key = "";
	    String value = "";
	    StringBuffer jsonBuffer = new StringBuffer();
	    jsonBuffer.append("{");    
	    for(Iterator<String> it = keys.iterator();it.hasNext();){
	        key =  (String)it.next();
	        value = map.get(key);
	        jsonBuffer.append(key+":"+value);
	        if(it.hasNext()){
	             jsonBuffer.append(",");
	        }
	    }
	    jsonBuffer.append("}");
	    return jsonBuffer.toString();
	}
	
	
	/**
	 * 将Map<String,String>对象转换为json串,key, value<String> 加双引号
	 * @param map
	 * @return
	 */
	public static String getJson4MapDoubleString(Map<String,Object> map){
		Set<String> keys = map.keySet();
	    String key = "";
	    Object value = "";
	    StringBuffer jsonBuffer = new StringBuffer();
	    jsonBuffer.append("{");    
	    for(Iterator<String> it = keys.iterator();it.hasNext();){
	        key =  (String)it.next();
	        value = map.get(key);
	        if(value instanceof String){
	        	value = "\"" + value + "\"";
	        }
	        jsonBuffer.append("\"" + key.toLowerCase() + "\":" + value );
	        if(it.hasNext()){
	             jsonBuffer.append(",");
	        }
	    }
	    jsonBuffer.append("}");
	    return jsonBuffer.toString();
	}
	
    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     * @param jsonString
     * @return
     */
	public static Map<String,String> getMap4Json(String jsonString){
    	 Map<String,String> valueMap = new HashMap<String,String>();
    	 try
    	 {
        JSONObject jsonObject = JSONObject.fromObject( jsonString );  
        Iterator  keyIter = jsonObject.keys();
        String key;
        String value;
       

        while( keyIter.hasNext())
        {
            key = (String)keyIter.next();
            value = String.valueOf(jsonObject.get(key));
            valueMap.put(key, value);
        }
    	 }catch(Exception ex)
    	 {
    		 //ExLog.logPrint(JsonUtils.class, ex);
    	 }
        return valueMap;
    }
    /**
     * 从对象json串中取出key对应的值
     * 仅适用于对象json串
     * @param jsonString
     * @param key
     * @return
     */
    public static String getValue4Json(String jsonString, String key){
    	String value = "";
    	Map<String,String> msgMap = JsonUtils.getMap4Json(jsonString);
    	if(msgMap == null){
    		return null;
    	}else{
    		value = msgMap.get(key);
    		if(value == null){
    			return null;
    		}else{
    			return value;
    		}
    	}
    }
  
    
    
    
    /**
     * 将map中的key值转化为小写
     * 仅适用于对象key值为String类型
     * @param map
     * @return
     */
    public static Map<String,Object> toLowerMap(Map <String,Object> map){
    	Map <String,Object>  maptowerMap = new HashMap<String,Object> ();
    	Iterator i=map.entrySet().iterator();
    	while(i.hasNext()){//只遍历一次,速度快
    	Map.Entry e=(Map.Entry)i.next();
    		maptowerMap.put(e.getKey().toString().toLowerCase(),e.getValue());
    	}
    	return maptowerMap;
    }
    
    
    /**
     * 将list中Map的key值转化为小写
     * 仅适用于对象key值为String类型
     * @param map
     * @return
     */
    public static List<Map<String,Object>> toLowerListMap(List<Map<String,Object>> listmap){
    	List<Map <String,Object>> list = new ArrayList<Map <String,Object>>();
    	for(int i = 0 ; i < listmap.size() ; i++){
    		Map<String,Object> map = toLowerMap(listmap.get(i));
    		list.add(map);
    	}
    	return list;
    }
    
    public static String replaceSymbols(String json){
    	String re = json.replace("\n", "\\n");
    	re = re.replace("\r", "\\r");
    	re = re.replace("\t", "\\t");
    	return re;
    }
    
	public static String toJson(Object obj) {
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		
		if (obj instanceof List<?>) {
			JSONArray jsonArray = JSONArray.fromObject(obj, jsonConfig);
			//log.debug("JsonData - [Array]:" + jsonArray);
			return jsonArray.toString();
		} else {
			JSONObject jsonObject = JSONObject.fromObject(obj, jsonConfig);
			//log.debug("JsonData - [Object]: " + jsonObject);
			return jsonObject.toString();
		}
	}
	
	public static Object toBean(String str, Class<?> clazz) {
		
		JSONObject jsonObject = JSONObject.fromObject(str);
		
		String[] dateFormats = new String[] {"yyyy-MM-dd"}; 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats)); 
	
		return JSONObject.toBean(jsonObject, clazz);
	}
}
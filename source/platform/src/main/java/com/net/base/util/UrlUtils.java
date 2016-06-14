/**
 * 
 */
package com.net.base.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 类说明:<br>
 * 创建时间: 2008-9-9 下午10:31:22<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class UrlUtils {

	private static final Logger log = Logger.getLogger(UrlUtils.class);

	public static InputStream getFileInputStream(String path) {

		Resource resource = null;
		InputStream inputStream = null;
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			resource = resolver.getResource("classpath:"+path);
			URL url = resource.getURL();
			log.debug("LocalResources configuration url: [" + url + "]");
			inputStream = resource.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static URL getFileURL(String path) {

		Resource resource = null;
		URL url = null;
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			resource = resolver.getResource(path);
			url = resource.getURL();
			log.debug("LocalResources configuration url: [" + url + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

	public static String readFile(String filePath) {

		InputStream is = UrlUtils.getFileInputStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();

		String str;
		try {
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

}

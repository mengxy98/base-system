/**
 * 
 */
package com.net.security;

import java.util.Map;




/**
 * 类说明:<br>
 * 创建时间: 2008-9-4 下午04:34:27<br>
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class LoginMessager extends BaseObject {

	private boolean success;
	
	private boolean failure;
	
	private Map<String, String> contents;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public Map<String, String> getContents() {
		return contents;
	}

	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}

}


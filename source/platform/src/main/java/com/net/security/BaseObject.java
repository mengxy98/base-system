/**
 * 
 */
package com.net.security;

import java.lang.reflect.Field;

/**
 * 类说明: Via reflect to implement toString method.<br>
 * 创建时间: 2008-8-12 上午09:11:48<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public abstract class BaseObject {

	public String toString() {

		StringBuffer buffer = null;

		if (buffer == null) {
			buffer = new StringBuffer();
			Class<?> clazz = getClass();
			String fullName = clazz.getName();
			int position = fullName.lastIndexOf(".");
			String shortName = fullName.substring(position + 1);

			buffer.append(shortName);
			buffer.append(": [");

			Field[] fields = clazz.getDeclaredFields();
			Field.setAccessible(fields, true);
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				try {
					buffer.append(field.getName());
					buffer.append("=");
					buffer.append(field.get(this));
					buffer.append(", ");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			buffer.setLength(buffer.length() - 2);
			buffer.append("]");
		}
		return buffer.toString();
	}

}


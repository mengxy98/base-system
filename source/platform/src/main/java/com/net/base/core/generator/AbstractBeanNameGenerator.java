/**
 * 
 */
package com.net.base.core.generator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 类说明: 生成普通类的Spring注册名，规则是首字母小写<br>
 * 创建时间: 2011-1-26 下午12:45:28<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class AbstractBeanNameGenerator implements BeanNameGenerator {

	@Override
	public String generateBeanName(BeanDefinition paramBeanDefinition,
			BeanDefinitionRegistry paramBeanDefinitionRegistry) {
		String[] strs = paramBeanDefinition.getBeanClassName().split("\\.");
		String shortName = strs[strs.length - 1];
		return StringUtils.uncapitalize(shortName);
	}

}

/**
 * 
 */
package com.net.base.core.generator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 类说明: 生成service实现类的Spring注册名，规则是首字母小写，并去掉后缀名<br>
 * 创建时间:2013-10-04<br>
 * 
 * @author 李祥辉<br>
 */
public class IServiceBeanNameGenerator implements BeanNameGenerator {

	private static final Logger logger = Logger
			.getLogger(IServiceBeanNameGenerator.class);

	private static final String IMPLEMENTS_SUFFIX = "Impl";

	@Override
	public String generateBeanName(BeanDefinition paramBeanDefinition,
			BeanDefinitionRegistry paramBeanDefinitionRegistry) {
		String[] strs = paramBeanDefinition.getBeanClassName().split("\\.");
		String shortName = strs[strs.length - 1];
		shortName = StringUtils.uncapitalize(shortName);
		shortName = shortName.replace(IMPLEMENTS_SUFFIX, "");

		logger.debug("Generated a service bean's name: [" + shortName + "]");

		return shortName;
	}

}

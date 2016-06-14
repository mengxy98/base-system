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
 * 类说明: 生成iBatis的DAO的Spring注册名，规则是首字母小写，并去掉后缀名<br>
 * 创建时间: 2011-1-26 下午12:44:20<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class IBatisDaoBeanNameGenerator implements BeanNameGenerator {

	private static final Logger logger = Logger
			.getLogger(IBatisDaoBeanNameGenerator.class);

	private static final String DAO_IMPLEMENTS_SUFFIX = "Impl";

	@Override
	public String generateBeanName(BeanDefinition paramBeanDefinition,
			BeanDefinitionRegistry paramBeanDefinitionRegistry) {
		String[] strs = paramBeanDefinition.getBeanClassName().split("\\.");
		String shortName = strs[strs.length - 1];
		shortName = StringUtils.uncapitalize(shortName);
		shortName = shortName.replace(DAO_IMPLEMENTS_SUFFIX, "");

		logger.debug("Generated a ibatis DAO bean's name: [" + shortName + "]");

		return shortName;
	}

}

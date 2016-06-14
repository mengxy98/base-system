package com.net.base.frame.ext.spring;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ObjectUtils;

public abstract class CustomPropertyResourceConfigurer extends CustomPropertiesLoaderSupport
		implements BeanFactoryPostProcessor, PriorityOrdered {

	private int order = Ordered.LOWEST_PRECEDENCE; // default: same as
													// non-Ordered

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try {
			Properties mergedProps = mergeProperties();

			// Convert the merged properties, if necessary.
			convertProperties(mergedProps);

			// Let the subclass process the properties.
			processProperties(beanFactory, mergedProps);
		} catch (IOException ex) {
			throw new BeanInitializationException("Could not load properties", ex);
		}
	}

	/**
	 * Convert the given merged properties, converting property values if
	 * necessary. The result will then be processed.
	 * <p>
	 * The default implementation will invoke {@link #convertPropertyValue} for
	 * each property value, replacing the original with the converted value.
	 * 
	 * @param props
	 *            the Properties to convert
	 * @see #processProperties
	 */
	protected void convertProperties(Properties props) {
		Enumeration propertyNames = props.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			String propertyValue = props.getProperty(propertyName);
			String convertedValue = convertPropertyValue(propertyValue);
			if (!ObjectUtils.nullSafeEquals(propertyValue, convertedValue)) {
				props.setProperty(propertyName, convertedValue);
			}
		}
	}

	/**
	 * Convert the given property value from the properties source to the value
	 * that should be applied.
	 * <p>
	 * The default implementation simply returns the original value. Can be
	 * overridden in subclasses, for example to detect encrypted values and
	 * decrypt them accordingly.
	 * 
	 * @param originalValue
	 *            the original value from the properties source (properties file
	 *            or local "properties")
	 * @return the converted value, to be used for processing
	 * @see #setProperties
	 * @see #setLocations
	 * @see #setLocation
	 */
	protected String convertPropertyValue(String originalValue) {
		return originalValue;
	}

	/**
	 * Apply the given Properties to the given BeanFactory.
	 * 
	 * @param beanFactory
	 *            the BeanFactory used by the application context
	 * @param props
	 *            the Properties to apply
	 * @throws org.springframework.beans.BeansException
	 *             in case of errors
	 */
	protected abstract void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException;

}

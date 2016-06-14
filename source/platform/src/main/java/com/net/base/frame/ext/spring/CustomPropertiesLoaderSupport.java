package com.net.base.frame.ext.spring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.web.context.support.ServletContextResource;

public class CustomPropertiesLoaderSupport {

	public static final String XML_FILE_EXTENSION = ".xml";

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private Properties[] localProperties;

	private Resource[] locations;

	private boolean localOverride = false;

	private boolean ignoreResourceNotFound = false;

	private String fileEncoding;

	private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	
	/**
	 * @description:项目部署位置，如果服务器为tomcat,则值为webapps，如果服务器为嵌入式的jetty，且jetty.xml中属性war的值为src/main/webapp，则此值须为src/main/webapp，其他servlet容器根据需要进行调整
	 */
	private String deployPath;
	
	//本地文本路径
	private String localPath;
	
	
	
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}

	/**
	 * Set local properties, e.g. via the "props" tag in XML bean definitions.
	 * These can be considered defaults, to be overridden by properties loaded
	 * from files.
	 */
	public void setProperties(Properties properties) {
		this.localProperties = new Properties[] { properties };
	}

	/**
	 * Set local properties, e.g. via the "props" tag in XML bean definitions,
	 * allowing for merging multiple properties sets into one.
	 */
	public void setPropertiesArray(Properties[] propertiesArray) {
		this.localProperties = propertiesArray;
	}

	/**
	 * Set a location of a properties file to be loaded.
	 * <p>
	 * Can point to a classic properties file or to an XML file that follows JDK
	 * 1.5's properties XML format.
	 */
	public void setLocation(Resource location) {
		this.locations = new Resource[] { location };
	}

	/**
	 * Set locations of properties files to be loaded.
	 * <p>
	 * Can point to classic properties files or to XML files that follow JDK
	 * 1.5's properties XML format.
	 * <p>
	 * Note: Properties defined in later files will override properties defined
	 * earlier files, in case of overlapping keys. Hence, make sure that the
	 * most specific files are the last ones in the given list of locations.
	 */
	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	/**
	 * Set whether local properties override properties from files.
	 * <p>
	 * Default is "false": Properties from files override local defaults. Can be
	 * switched to "true" to let local properties override defaults from files.
	 */
	public void setLocalOverride(boolean localOverride) {
		this.localOverride = localOverride;
	}

	/**
	 * Set if failure to find the property resource should be ignored.
	 * <p>
	 * "true" is appropriate if the properties file is completely optional.
	 * Default is "false".
	 */
	public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
		this.ignoreResourceNotFound = ignoreResourceNotFound;
	}

	/**
	 * Set the encoding to use for parsing properties files.
	 * <p>
	 * Default is none, using the <code>java.util.Properties</code> default
	 * encoding.
	 * <p>
	 * Only applies to classic properties files, not to XML files.
	 * 
	 * @see org.springframework.util.PropertiesPersister#load
	 */
	public void setFileEncoding(String encoding) {
		this.fileEncoding = encoding;
	}

	/**
	 * Set the PropertiesPersister to use for parsing properties files. The
	 * default is DefaultPropertiesPersister.
	 * 
	 * @see org.springframework.util.DefaultPropertiesPersister
	 */
	public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
		this.propertiesPersister = (propertiesPersister != null ? propertiesPersister
				: new DefaultPropertiesPersister());
	}

	/**
	 * Return a merged Properties instance containing both the loaded properties
	 * and properties set on this FactoryBean.
	 */
	protected Properties mergeProperties() throws IOException {
		Properties result = new Properties();

		if (this.localOverride) {
			// Load properties from file upfront, to let local properties
			// override.
			loadProperties(result);
		}

		if (this.localProperties != null) {
			for (int i = 0; i < this.localProperties.length; i++) {
				CollectionUtils.mergePropertiesIntoMap(this.localProperties[i], result);
			}
		}

		if (!this.localOverride) {
			// Load properties from file afterwards, to let those properties
			// override.
			loadProperties(result);
		}

		return result;
	}

	/**
	 * Load properties into the given instance.
	 * 
	 * @param props
	 *            the Properties instance to load into
	 * @throws java.io.IOException
	 *             in case of I/O errors
	 * @see #setLocations
	 */
	protected void loadProperties(Properties props) throws IOException {
		if (this.locations != null) {
			for (int i = 0; i < this.locations.length; i++) {
				Resource location = this.locations[i];
				if (logger.isInfoEnabled()) {
					logger.info("Loading properties file from " + location);
				}
				InputStream is = null;
				try {
					// spring的DefaultResourceLoader将不符合内置资源定位前缀的资源统一转换为了ServletContextResource
					if (location instanceof ServletContextResource) {
						ServletContextResource newLocation = (ServletContextResource) location;
						
						    String newPath = "";
							// 获取项目部署地址
							String depPath = newLocation.getServletContext().getRealPath("/");
							// 获取真实的磁盘路径地址
							if(depPath.indexOf(deployPath) > -1){// 如果是自定义的资源
                                depPath = depPath.replace("\\", "/");
							    newPath = depPath.substring(0, depPath.indexOf(deployPath)) + newLocation.getPath();
								
							}else{
								String relativelyPath=System.getProperty("user.dir"); 
							    newPath = relativelyPath +localPath + newLocation.getPath();
								
							}
							this.logger.info("获取自定义server配置文件地址为:" + newPath);
							// 获取配置文件
							File file = new File(newPath);
							// 创建一个新的系统文件资源
							FileSystemResource newResource = new FileSystemResource(file);
							// 资源转换
							location = newResource;
							
					}
					
					is = location.getInputStream();
					if (location.getFilename().endsWith(XML_FILE_EXTENSION)) {
						this.propertiesPersister.loadFromXml(props, is);
					} else {
						if (this.fileEncoding != null) {
							this.propertiesPersister.load(props, new InputStreamReader(is, this.fileEncoding));
						} else {
							this.propertiesPersister.load(props, is);
						}
					}
				} catch (IOException ex) {
					if (this.ignoreResourceNotFound) {
						if (logger.isWarnEnabled()) {
							logger.warn("Could not load properties from " + location + ": " + ex.getMessage());
						}
					} else {
						throw ex;
					}
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}
		}
	}

}

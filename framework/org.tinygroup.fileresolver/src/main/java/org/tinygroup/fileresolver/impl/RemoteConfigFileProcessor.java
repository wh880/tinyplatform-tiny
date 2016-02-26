/**
 * 
 */
package org.tinygroup.fileresolver.impl;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * @author yanwj
 *
 */
public class RemoteConfigFileProcessor extends AbstractFileProcessor{
	RemoteConfigReadClient remoteConfigReadClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteConfigFileProcessor.class);

	public static final String REMOTE_CONFIG_PATH = "/application/application-properties/remoteconfig";
	
	public static final String REMOTE_CONFIG_PATH_ATTRIBUTE = "enable";
	
	String applicationXML;
	
	public RemoteConfigFileProcessor(String applicationXML) {
		this.applicationXML = applicationXML;
	}
	
	public String getApplicationNodePath() {
		return REMOTE_CONFIG_PATH;
	}

	public void process() {
		if (StringUtils.isNotBlank(applicationXML)) {
			XmlNode xmlNode = loadRemoteConfig(applicationXML);
			String enable = xmlNode.getAttribute(REMOTE_CONFIG_PATH_ATTRIBUTE);
			if (!StringUtils.equalsIgnoreCase(enable, "true")) {
				return;
			}
		}
		LOGGER.logMessage(LogLevel.INFO, "开始启动远程配置处理器");
		remoteConfigReadClient = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean(RemoteConfigReadClient.class);
		if (remoteConfigReadClient == null) {
			throw new RuntimeException("未找到远程配置实现bean");
		}
		remoteConfigReadClient.start();
		LOGGER.logMessage(LogLevel.INFO, "远程配置处理器启动完成");
		LOGGER.logMessage(LogLevel.INFO, "开始载入远程配置信息");
		ConfigurationUtil.getConfigurationManager().getConfiguration().putAll(remoteConfigReadClient.getALL());
		LOGGER.logMessage(LogLevel.INFO, "载入远程配置信息完成");
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
	}

	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	private XmlNode loadRemoteConfig(String applicationConfig) {
		XmlStringParser parser = new XmlStringParser();
		XmlNode root = parser.parse(applicationConfig).getRoot();
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(root);
		XmlNode appConfig = filter
				.findNode(RemoteConfigFileProcessor.REMOTE_CONFIG_PATH);
		return appConfig; 
	}
	
}

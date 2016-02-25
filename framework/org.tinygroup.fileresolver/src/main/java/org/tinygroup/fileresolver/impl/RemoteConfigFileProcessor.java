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
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * @author yanwj
 *
 */
public class RemoteConfigFileProcessor extends AbstractFileProcessor{
	RemoteConfigReadClient remoteConfigReadClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteConfigFileProcessor.class);

	public static final String REMOTE_CONFIG_PATH = "/application/application-properties/remoteconfig";
	
	public static final String REMOTE_CONFIG_PATH_ATTRIBUTE = "enable";
	
	XmlNode remoteConfigNode;
	
	public RemoteConfigFileProcessor(XmlNode remoteConfigNode) {
		this.remoteConfigNode = remoteConfigNode;
	}
	
	public String getApplicationNodePath() {
		return REMOTE_CONFIG_PATH;
	}

	public void process() {
		if (remoteConfigNode == null) {
			return;
		}else {
			String enable = remoteConfigNode.getAttribute(REMOTE_CONFIG_PATH_ATTRIBUTE);
			if (!StringUtils.equalsIgnoreCase(enable, "true")) {
				return;
			}
		}
		LOGGER.logMessage(LogLevel.INFO, "远程配置处理器启动");
		remoteConfigReadClient = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean(RemoteConfigReadClient.class);
		if (remoteConfigReadClient == null) {
			throw new RuntimeException("远程配置，未找到服务实现");
		}
		remoteConfigReadClient.start();
		LOGGER.logMessage(LogLevel.INFO, "远程配置载入Tiny...");
		ConfigurationUtil.getConfigurationManager().getConfiguration().putAll(remoteConfigReadClient.getALL());
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
	}

	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

}

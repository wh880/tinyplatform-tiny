package org.tinygroup.jedis.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.jedis.JedisSentinelManager;
import org.tinygroup.jedis.config.JedisSentinelConfigs;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * Jedis配置文件处理器，加载Jedis客户端配置信息
 * 
 * @author yancheng11334
 * 
 */
public class JedisSentinelConfigsFileProcessor extends AbstractFileProcessor {

	private static final String JEDIS_SENTRINEL_CONFIG_EXT_NAME = ".jedissentrinelconfig.xml";

	private static final String JEDIS_SENTRINEL_XSTREAM_NAME = "jedis";

	private JedisSentinelManager jedisSentinelManager;

	

	public JedisSentinelManager getJedisSentinelManager() {
		return jedisSentinelManager;
	}

	public void setJedisSentinelManager(JedisSentinelManager jedisSentinelManager) {
		this.jedisSentinelManager = jedisSentinelManager;
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(JEDIS_SENTRINEL_XSTREAM_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除Redis主从集群配置文件[{0}]",
					fileObject.getAbsolutePath());
			JedisSentinelConfigs jedisSentinelConfigs = (JedisSentinelConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (jedisSentinelConfigs != null) {
				jedisSentinelManager.removeJedisSentinelConfigs(jedisSentinelConfigs);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除Redis主从集群配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载Redis主从集群配置文件[{0}]",
					fileObject.getAbsolutePath());
			JedisSentinelConfigs oldJedisSentinelConfigs = (JedisSentinelConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (oldJedisSentinelConfigs != null) {
				jedisSentinelManager.removeJedisSentinelConfigs(oldJedisSentinelConfigs);
			}
			JedisSentinelConfigs jedisSentinelConfigs = (JedisSentinelConfigs) stream
					.fromXML(fileObject.getInputStream());
			jedisSentinelManager.addJedisSentinelConfigs(jedisSentinelConfigs);
			caches.put(fileObject.getAbsolutePath(), jedisSentinelConfigs);
			LOGGER.logMessage(LogLevel.INFO, "加载Redis主从集群配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(JEDIS_SENTRINEL_CONFIG_EXT_NAME);
	}

}

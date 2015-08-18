package org.tinygroup.cache.redis.fileresolver;

import org.tinygroup.cache.redis.JedisManager;
import org.tinygroup.cache.redis.config.JedisConfigs;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
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
public class JedisConfigsFileProcessor extends AbstractFileProcessor {

	private static final String JEDIS_CONFIG_EXT_NAME = ".jedisconfig.xml";

	private static final String JEDIS_CACHE_NAME = "jedisconfig";

	private JedisManager jedisManager;

	public JedisManager getJedisManager() {
		return jedisManager;
	}

	public void setJedisManager(JedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(JEDIS_CACHE_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除Redis缓存配置文件[{0}]",
					fileObject.getAbsolutePath());
			JedisConfigs jedisConfigs = (JedisConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (jedisConfigs != null) {
				jedisManager.removeJedisConfigs(jedisConfigs);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除Redis缓存配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载Redis缓存配置文件[{0}]",
					fileObject.getAbsolutePath());
			JedisConfigs oldJedisConfigs = (JedisConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (oldJedisConfigs != null) {
				jedisManager.removeJedisConfigs(oldJedisConfigs);
			}
			JedisConfigs jedisConfigs = (JedisConfigs) stream
					.fromXML(fileObject.getInputStream());
			jedisManager.addJedisConfigs(jedisConfigs);
			caches.put(fileObject.getAbsolutePath(), jedisConfigs);
			LOGGER.logMessage(LogLevel.INFO, "加载Redis缓存配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(JEDIS_CONFIG_EXT_NAME);
	}

}

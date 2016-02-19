/**
 * 
 */
package org.tinygroup.remoteconfig.model;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;

/**
 * @author yanwj
 *
 */
public class RemoteEnvironment {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteEnvironment.class);
	
	private static RemoteConfig config;
	
	public static RemoteConfig getConfig(){
		if (config == null) {
			new RemoteEnvironment();
		}
		return config;
	}
	
	private RemoteEnvironment(){
		LOGGER.logMessage(LogLevel.INFO, "读取本地配置信息...");
		URL url = getClass().getResource(IRemoteConfigConstant.REMOTE_CONFIG_NAME);
		if (url != null) {
			LOGGER.logMessage(LogLevel.INFO, String.format("开始解析[%s]" ,IRemoteConfigConstant.REMOTE_CONFIG_NAME));
			Properties pro = new Properties();
			try {
				pro.load(url.openStream());
				transObj(pro);
				LOGGER.logMessage(LogLevel.INFO, "============配置信息================");
				LOGGER.logMessage(LogLevel.INFO, config.toString());
				LOGGER.logMessage(LogLevel.INFO, "====================================");
				LOGGER.logMessage(LogLevel.INFO, String.format("解析完成[%s]" ,IRemoteConfigConstant.REMOTE_CONFIG_NAME));
			} catch (IOException e) {
				throw new RuntimeException("ZK配置初始化失败。。。", e);
			}
		}
	}
	
	private void transObj(Properties pro){
		String urlStr = pro.get(IRemoteConfigConstant.REMOTE_URLS) == null ? "":pro.get(IRemoteConfigConstant.REMOTE_URLS).toString();
		String app = pro.get(IRemoteConfigConstant.REMOTE_APP) == null ? "":pro.get(IRemoteConfigConstant.REMOTE_APP).toString();
		String env = pro.get(IRemoteConfigConstant.REMOTE_ENV) == null ? "":pro.get(IRemoteConfigConstant.REMOTE_ENV).toString();
		String version = pro.get(IRemoteConfigConstant.REMOTE_VERSION) == null ? "":pro.get(IRemoteConfigConstant.REMOTE_VERSION).toString();
		
		config = new RemoteConfig(urlStr, app, env, version);
	}
	
}

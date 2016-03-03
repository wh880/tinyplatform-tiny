/**
 * 
 */
package org.tinygroup.remoteconfig.zk.config;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.zk.client.IRemoteConfigZKConstant;

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
		URL url = getClass().getResource(IRemoteConfigZKConstant.REMOTE_CONFIG_NAME);
		if (url != null) {
			LOGGER.logMessage(LogLevel.INFO, String.format("开始解析[%s]" ,IRemoteConfigZKConstant.REMOTE_CONFIG_NAME));
			Properties pro = new Properties();
			try {
				pro.load(url.openStream());
				transObj(pro);
				LOGGER.logMessage(LogLevel.INFO, "============配置信息================");
				LOGGER.logMessage(LogLevel.INFO, config.toString());
				LOGGER.logMessage(LogLevel.INFO, "====================================");
				LOGGER.logMessage(LogLevel.INFO, String.format("解析完成[%s]" ,IRemoteConfigZKConstant.REMOTE_CONFIG_NAME));
				LOGGER.logMessage(LogLevel.INFO, "本地配置信息读取完毕");
			} catch (IOException e) {
				throw new RuntimeException("ZK配置初始化失败。。。", e);
			}
		}
	}
	
	private void transObj(Properties pro){
		String urlStr = pro.get(IRemoteConfigZKConstant.REMOTE_URLS) == null ? "":pro.get(IRemoteConfigZKConstant.REMOTE_URLS).toString();
		String app = pro.get(IRemoteConfigZKConstant.REMOTE_APP) == null ? "":pro.get(IRemoteConfigZKConstant.REMOTE_APP).toString();
		String env = pro.get(IRemoteConfigZKConstant.REMOTE_ENV) == null ? "":pro.get(IRemoteConfigZKConstant.REMOTE_ENV).toString();
		String version = pro.get(IRemoteConfigZKConstant.REMOTE_VERSION) == null ? "":pro.get(IRemoteConfigZKConstant.REMOTE_VERSION).toString();
		
		config = new RemoteConfig(urlStr, app, env, version );
	}
	
}

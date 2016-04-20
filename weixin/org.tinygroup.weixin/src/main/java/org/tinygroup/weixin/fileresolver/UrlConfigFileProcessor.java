package org.tinygroup.weixin.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.weixin.WeiXinManager;
import org.tinygroup.weixin.common.UrlConfigs;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 微信服务的配置文件处理器
 * 
 * @author yancheng11334
 * 
 */
public class UrlConfigFileProcessor extends AbstractFileProcessor {

	private static final String WEIXIN_URL_NAME = ".weixin";
	private static final String XSTEAM_PACKAGE_NAME = "weixin";
    
	private WeiXinManager weiXinManager;

	public WeiXinManager getWeiXinManager() {
		return weiXinManager;
	}

	public void setWeiXinManager(WeiXinManager weiXinManager) {
		this.weiXinManager = weiXinManager;
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(XSTEAM_PACKAGE_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除微信服务配置文件[{0}]",
					fileObject.getAbsolutePath());
			UrlConfigs urlConfigs = (UrlConfigs) caches
					.get(fileObject.getAbsolutePath());
			if (urlConfigs != null) {
				weiXinManager.removeUrlConfigs(urlConfigs);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除微信服务配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载微信服务配置文件[{0}]",
					fileObject.getAbsolutePath());
			UrlConfigs oldUrlConfigs = (UrlConfigs) caches
			.get(fileObject.getAbsolutePath());
			if (oldUrlConfigs != null) {
				weiXinManager.removeUrlConfigs(oldUrlConfigs);
			}
			UrlConfigs urlConfigs = (UrlConfigs) stream.fromXML(fileObject
					.getInputStream());
			if (urlConfigs != null) {
				weiXinManager.addUrlConfigs(urlConfigs);
			}
			caches.put(fileObject.getAbsolutePath(), urlConfigs);
			LOGGER.logMessage(LogLevel.INFO, "加载微信服务配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(WEIXIN_URL_NAME) || fileObject.getFileName().endsWith(".weixin.xml");
	}

}

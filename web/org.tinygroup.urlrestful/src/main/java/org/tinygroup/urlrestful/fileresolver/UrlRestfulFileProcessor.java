package org.tinygroup.urlrestful.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.urlrestful.config.Rules;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * restful配置文件扫描器
 * 
 * @author renhui
 * 
 */
public class UrlRestfulFileProcessor extends AbstractFileProcessor {

	private static final String RESTFUL_EXT_FILENAME = ".restful.xml";

	private UrlRestfulManager urlRestfulManager;

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().toLowerCase()
				.endsWith(RESTFUL_EXT_FILENAME);
	}

	public void setUrlRestfulManager(UrlRestfulManager urlRestfulManager) {
		this.urlRestfulManager = urlRestfulManager;
	}

	public void process() {
		XStream stream = XStreamFactory
				.getXStream(UrlRestfulManager.URL_RESTFUL_XSTREAM);
		for (FileObject fileObject : deleteList) {
			logger.logMessage(LogLevel.INFO, "正在移除restful文件[{0}]",
					fileObject.getAbsolutePath());
			Rules Rules = (Rules) caches.get(fileObject
					.getAbsolutePath());
			if (Rules != null) {
				urlRestfulManager.removeRules(Rules);
				caches.remove(fileObject.getAbsolutePath());
			}
			logger.logMessage(LogLevel.INFO, "移除restful文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			logger.logMessage(LogLevel.INFO, "正在加载restful文件[{0}]",
					fileObject.getAbsolutePath());
			Rules Rules = (Rules) stream.fromXML(fileObject
					.getInputStream());
			Rules oldRules = (Rules) caches.get(fileObject
					.getAbsolutePath());
			if (oldRules != null) {
				urlRestfulManager.removeRules(oldRules);
			}
			urlRestfulManager.addRules(Rules);
			caches.put(fileObject.getAbsolutePath(), Rules);
			logger.logMessage(LogLevel.INFO, "加载restful文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

}

package org.tinygroup.urlrestful.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.urlrestful.config.UrlRestfuls;
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
				.getXStream(UrlRestfulManager.URLREST_XSTREAM);
		for (FileObject fileObject : deleteList) {
			logger.logMessage(LogLevel.INFO, "正在移除restful文件[{0}]",
					fileObject.getAbsolutePath());
			UrlRestfuls urlRestfuls = (UrlRestfuls) caches.get(fileObject
					.getAbsolutePath());
			if (urlRestfuls != null) {
				urlRestfulManager.removeRestfuls(urlRestfuls);
				caches.remove(fileObject.getAbsolutePath());
			}
			logger.logMessage(LogLevel.INFO, "移除restful文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			logger.logMessage(LogLevel.INFO, "正在加载restful文件[{0}]",
					fileObject.getAbsolutePath());
			UrlRestfuls urlRestfuls = (UrlRestfuls) stream.fromXML(fileObject
					.getInputStream());
			UrlRestfuls oldRestfuls = (UrlRestfuls) caches.get(fileObject
					.getAbsolutePath());
			if (oldRestfuls != null) {
				urlRestfulManager.removeRestfuls(oldRestfuls);
			}
			urlRestfulManager.addUrlRestfuls(urlRestfuls);
			caches.put(fileObject.getAbsolutePath(), urlRestfuls);
			logger.logMessage(LogLevel.INFO, "加载restful文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

}

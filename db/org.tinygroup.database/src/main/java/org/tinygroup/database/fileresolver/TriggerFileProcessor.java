package org.tinygroup.database.fileresolver;

import org.tinygroup.database.config.trigger.Triggers;
import org.tinygroup.database.trigger.TriggerProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 触发器文件处理
 * 
 * @author renhui
 * 
 */
public class TriggerFileProcessor extends AbstractFileProcessor {

	private static final String TRIGGER_EXTFILENAME = ".trigger.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(TRIGGER_EXTFILENAME);
	}

	public void process() {

		TriggerProcessor processor = SpringUtil
				.getBean(DataBaseUtil.TRIGGER_BEAN);
		XStream stream = XStreamFactory
				.getXStream(DataBaseUtil.DATABASE_XSTREAM);
		for (FileObject fileObject : deleteList) {
			logger.logMessage(LogLevel.INFO, "正在移除trigger文件[{0}]",
					fileObject.getAbsolutePath());
			Triggers triggers = (Triggers) caches
					.get(fileObject.getAbsolutePath());
			if (triggers != null) {
				processor.removeTriggers(triggers);
				caches.remove(fileObject.getAbsolutePath());
			}
			logger.logMessage(LogLevel.INFO, "移除trigger文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			logger.logMessage(LogLevel.INFO, "正在加载trigger文件[{0}]",
					fileObject.getAbsolutePath());
			Triggers oldTriggers = (Triggers) caches
					.get(fileObject.getAbsolutePath());
			if (oldTriggers != null) {
				processor.removeTriggers(oldTriggers);
			}
			Triggers triggers = (Triggers) stream
					.fromXML(fileObject.getInputStream());
			processor.addTriggers(triggers);
			caches.put(fileObject.getAbsolutePath(), triggers);
			logger.logMessage(LogLevel.INFO, "加载trigger文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

}

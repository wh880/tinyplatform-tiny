package org.tinygroup.database.fileresolver;

import org.tinygroup.database.config.sequence.Sequences;
import org.tinygroup.database.sequence.SequenceProcessor;
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
public class SequenceFileProcessor extends AbstractFileProcessor {

	private static final String TRIGGER_EXTFILENAME = ".sequence.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(TRIGGER_EXTFILENAME);
	}

	public void process() {

		SequenceProcessor processor = SpringUtil
				.getBean(DataBaseUtil.SEQUENCE_BEAN);
		XStream stream = XStreamFactory
				.getXStream(DataBaseUtil.DATABASE_XSTREAM);
		for (FileObject fileObject : deleteList) {
			logger.logMessage(LogLevel.INFO, "正在移除sequence文件[{0}]",
					fileObject.getAbsolutePath());
			Sequences sequences = (Sequences) caches
					.get(fileObject.getAbsolutePath());
			if (sequences != null) {
				processor.removeSequences(sequences);
				caches.remove(fileObject.getAbsolutePath());
			}
			logger.logMessage(LogLevel.INFO, "移除sequence文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			logger.logMessage(LogLevel.INFO, "正在加载sequence文件[{0}]",
					fileObject.getAbsolutePath());
			Sequences oldSequences = (Sequences) caches
					.get(fileObject.getAbsolutePath());
			if (oldSequences != null) {
				processor.removeSequences(oldSequences);
			}
			Sequences sequences = (Sequences) stream
					.fromXML(fileObject.getInputStream());
			processor.addSequences(sequences);
			caches.put(fileObject.getAbsolutePath(), sequences);
			logger.logMessage(LogLevel.INFO, "加载sequence文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

}
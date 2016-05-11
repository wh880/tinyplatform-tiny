package org.tiny.seg.fileresolver;

import org.tiny.seg.ChineseParser;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;

public class ChineseParserWordProcessor extends AbstractFileProcessor {

	private static final String EXT_FILE_NAME = ".worddic";
	
	private ChineseParser chineseParser;

	public ChineseParser getChineseParser() {
		return chineseParser;
	}

	public void setChineseParser(ChineseParser chineseParser) {
		this.chineseParser = chineseParser;
	}

	public void process() {
		LOGGER.logMessage(LogLevel.INFO, "开始加载tiny中文词库...");
		for(FileObject fileObject:changeList){
			LOGGER.logMessage(LogLevel.INFO, "开始加载词库文件{0}...",fileObject.getAbsolutePath());
			chineseParser.loadDict(fileObject.getInputStream(), "UTF-8");
			LOGGER.logMessage(LogLevel.INFO, "加载词库文件{0}完成!",fileObject.getAbsolutePath());
		}
		LOGGER.logMessage(LogLevel.INFO, "加载tiny中文词库完成!");
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(EXT_FILE_NAME);
	}

}

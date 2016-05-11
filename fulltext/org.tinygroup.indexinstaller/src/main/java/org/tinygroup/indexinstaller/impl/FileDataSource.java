package org.tinygroup.indexinstaller.impl;

import java.io.File;
import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.indexinstaller.IndexDataSource;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 文件目录数据源
 * 
 * @author yancheng11334
 * 
 */
public class FileDataSource extends AbstractIndexDataSource implements
		IndexDataSource<XmlNode> {

	public String getType() {
		return "file";
	}

	public void install(XmlNode config) {
		LOGGER.logMessage(LogLevel.INFO, "开始安装文件目录类型的索引数据源...");
		List<XmlNode> paths = config.getSubNodes("path");
		if (CollectionUtil.isEmpty(paths)) {
			throw new FullTextException("本文件目录节点没有找到目录配置!");
		} else {
			for (XmlNode path : paths) {
				LOGGER.logMessage(LogLevel.INFO, "开始处理目录{0}",
						path.getContent());
				File file = new File(path.getContent());
				installFile(file,path.getAttribute("type"));
				LOGGER.logMessage(LogLevel.INFO, "处理目录{0}结束!",
						path.getContent());
			}
		}
		LOGGER.logMessage(LogLevel.INFO, "安装文件目录类型的索引数据源结束!");
	}

	private void installFile(File dir, String type) {
		if (!dir.exists()) {
			throw new FullTextException(String.format("安装文件目录[%s]失败:目录不存在!",
					dir.getPath()));
		}
		try{
			getFullText().createIndex(type, dir);
		}catch(FullTextException e){
			throw e;
		}catch(Exception e){
			throw new FullTextException(String.format("安装文件目录[%s]发生异常:",
					dir.getPath()),e);
		}
	}

}

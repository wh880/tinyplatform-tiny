package org.tinygroup.webservice.fileresolver;

import java.io.InputStream;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.webservice.config.ContextParams;
import org.tinygroup.webservice.manager.ContextParamManager;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class ContextParamFileProcessor extends AbstractFileProcessor {
	
	private static final String CONTEXT_PARAM_EXT_FILENAME = ".contextparam.xml";
	
	private ContextParamManager contextParamManager;
	
	
	public ContextParamManager getContextParamManager() {
		return contextParamManager;
	}

	public void setContextParamManager(ContextParamManager contextParamManager) {
		this.contextParamManager = contextParamManager;
	}

	public void process() {
		XStream stream = XStreamFactory
				.getXStream(ContextParamManager.XSTEAM_PACKAGE_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除webservice参数配置文件[{0}]",
					fileObject.getAbsolutePath());
			ContextParams contextParams = (ContextParams) caches.get(fileObject.getAbsolutePath());
			if(contextParams!=null){
				contextParamManager.removeContextParams(contextParams);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除webservice参数配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载webservice参数配置文件[{0}]",
					fileObject.getAbsolutePath());
			ContextParams oldMatchers = (ContextParams)caches.get(fileObject.getAbsolutePath());
			if(oldMatchers!=null){
				contextParamManager.removeContextParams(oldMatchers);
			}
			InputStream inputStream = fileObject.getInputStream();
			ContextParams matchers = (ContextParams) stream
					.fromXML(inputStream);
			try {
				inputStream.close();
			} catch (Exception e) {
				LOGGER.errorMessage("关闭文件流时出错,文件路径:{}",e, fileObject.getAbsolutePath());
			}
			contextParamManager.addContextParams(matchers);
			caches.put(fileObject.getAbsolutePath(), matchers);
			LOGGER.logMessage(LogLevel.INFO, "加载webservice参数配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(CONTEXT_PARAM_EXT_FILENAME);
	}

}

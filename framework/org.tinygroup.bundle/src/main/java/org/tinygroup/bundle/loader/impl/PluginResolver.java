package org.tinygroup.bundle.loader.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public class PluginResolver {
	/**
	 * 插件添加时用到的扫描器
	 */
	private List<FileProcessor> pluginAddProcessorList = new ArrayList<FileProcessor>();
	
	/**
	 * 插件删除时用到的扫描器
	 */
	private List<FileProcessor> pluginRemoveProcessorList = new ArrayList<FileProcessor>();
	private static Logger logger = LoggerFactory.getLogger(PluginResolver.class);
	BundleManager bundleManager;
	/**
	 * 
	 * @param urls
	 *            jar包的url地址列表
	 */
	public void deResolve(URL[] urls,ClassLoader loader){
		
		pluginRemoveProcessorList = bundleManager.getPublicRemoveProcessors();
		cleanProcessor(pluginRemoveProcessorList);
		for (URL url : urls) {
			resole(url,pluginRemoveProcessorList);
		}
		for (FileProcessor fileProcessor : pluginRemoveProcessorList) {
//			fileProcessor.process(loader);
		}
	}
	/**
	 * 
	 * @param urls
	 *            jar包的url地址列表
	 */
	public void resolve(URL[] urls,ClassLoader loader)  {
		pluginAddProcessorList = bundleManager.getPublicProcessors();
		cleanProcessor(pluginAddProcessorList);
		for (URL url : urls) {
			resole(url,pluginAddProcessorList);
		}
		for (FileProcessor fileProcessor : pluginAddProcessorList) {
//			fileProcessor.process(loader);
		}
	}

	private void resole(URL url,List<FileProcessor> processorList)  {
		logger.logMessage(LogLevel.INFO, "处理插件jar包中的资源{0}",url);
		FileObject fileObj = VFS.resolveFile(url.toString());
		resolveFileObject(fileObj,processorList);
		logger.logMessage(LogLevel.INFO, "处理插件jar包中的资源{0}完毕",url);
	}

	private void resolveFileObject(FileObject file,List<FileProcessor> processorList) {
		
		if (file.isFolder()) {
			for (FileObject f : file.getChildren()) {
				resolveFileObject(f,processorList);
			}
		} else{
			process(file,processorList);
			
			//如果是jar文件，则再加处理
			String filePath = file.getAbsolutePath();
			if(filePath.endsWith(".jar")){
				FileObject jarFile = VFS
						.resolveFile("jar:" + filePath);
				if (jarFile.isFolder()) {
					for (FileObject f : jarFile.getChildren()) {
						resolveFileObject(f,processorList);
					}
				} 
			}
			
		} 
	}

	private void process(FileObject fileObject,List<FileProcessor> processorList)  {
		if (!fileObject.isExist())
			return;
		for (FileProcessor fileProcessor : processorList) {
			if (fileProcessor.isMatch(fileObject)) {
				
					addFile(fileObject, fileProcessor);
				
			}
		}

	}
	private void cleanProcessor(List<FileProcessor> processorList) {
		for (FileProcessor fileProcessor : processorList) {
			fileProcessor.clean();
		}
	}
	
	private void addFile(FileObject fileObject, FileProcessor fileProcessor) {
		fileProcessor.add(fileObject);
	}
}

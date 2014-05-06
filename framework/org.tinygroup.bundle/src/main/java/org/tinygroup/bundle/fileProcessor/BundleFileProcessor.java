package org.tinygroup.bundle.fileProcessor;

import org.tinygroup.bundle.BundleException;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class BundleFileProcessor extends AbstractFileProcessor{
	private static final String Bundle_EXT_FILENAME = ".bundle.xml";
	private static final String Bundle_XSTREAM = "bundle";
	private BundleManager bundleManager ;
	
	public BundleManager getBundleManager() {
		return bundleManager;
	}

	public void setBundleManager(BundleManager bundleManager) {
		this.bundleManager = bundleManager;
	}

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(Bundle_EXT_FILENAME);
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(Bundle_XSTREAM);
		for (FileObject file : deleteList) {
			logger.logMessage(LogLevel.INFO, "移除Bundle配置文件:{0}",
					file.getFileName());
			BundleDefine bundle = (BundleDefine) caches.get(file.getAbsolutePath());
			if(bundle!=null){
				try {
					bundleManager.removeBundle(bundle);
				} catch (BundleException e) {
					logger.errorMessage("移除Bundle:{0}时出错", e,bundle.getName());
				}
				caches.remove(file.getAbsolutePath());
			}
			logger.logMessage(LogLevel.INFO, "移除Bundle配置文件:{0}完成",
					file.getFileName());
		}
		for (FileObject file : changeList) {
			logger.logMessage(LogLevel.INFO, "开始读取Bundle配置文件:{0}",
					file.getFileName());
			BundleDefine oldBundle = (BundleDefine) caches.get(file.getAbsolutePath());
			if(oldBundle!=null){
				try {
					bundleManager.removeBundle(oldBundle);
				} catch (BundleException e) {
					logger.errorMessage("移除Bundle:{0}时出错", e,oldBundle.getName());
				}
			}
			BundleDefine bundle = (BundleDefine)  stream.fromXML(file
					.getInputStream());
			bundleManager.addBundleDefine(bundle);
			caches.put(file.getAbsolutePath(), bundle);
			logger.logMessage(LogLevel.INFO, "读取Bundle配置文件:{0}完成",
					file.getFileName());
		}
	}

}

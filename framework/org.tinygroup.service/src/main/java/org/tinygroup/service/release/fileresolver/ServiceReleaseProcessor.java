/**
 * 
 */
package org.tinygroup.service.release.fileresolver;

import java.io.InputStream;

import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.service.Service;
import org.tinygroup.service.release.ServiceReleaseManager;
import org.tinygroup.service.release.config.ServiceRelease;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * @author yanwj
 *
 */
public class ServiceReleaseProcessor extends AbstractFileProcessor implements FileProcessor {

	private static final String SERVICE_FILTER_EXTENSION = ".servicefilter.xml";
	
	public void process() {
		ServiceReleaseManager.clear();
		XStream stream = XStreamFactory
				.getXStream(Service.SERVICE_XSTREAM_PACKAGENAME);
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在读取servicefilter文件[{0}]",
					fileObject.getAbsolutePath());
			try {
				InputStream inputStream = fileObject.getInputStream();
				ServiceRelease filter = (ServiceRelease) stream
						.fromXML(inputStream);
				try {
					inputStream.close();
				} catch (Exception e) {
					LOGGER.errorMessage("关闭文件流时出错,文件路径:{}", e,
							fileObject.getAbsolutePath());
				}
				try {
					LOGGER.logMessage(LogLevel.INFO, "正在加载servicefilter");
					ServiceReleaseManager.add(filter);
					LOGGER.logMessage(LogLevel.INFO, "加载servicefilter结束");
				} catch (Exception e) {
					LOGGER.errorMessage("加载servicefilter时出现异常", e);
				}
				
			} catch (Exception e) {
				LOGGER.errorMessage("加载SysService文件[{0}]出现异常", e,
						fileObject.getAbsolutePath());
			}

			LOGGER.logMessage(LogLevel.INFO, "读取servicefilter文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(SERVICE_FILTER_EXTENSION);
	}}

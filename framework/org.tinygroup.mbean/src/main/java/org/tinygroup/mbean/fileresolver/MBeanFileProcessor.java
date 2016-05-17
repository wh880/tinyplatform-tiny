/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.mbean.fileresolver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.mbean.config.MBeanFileConfig;
import org.tinygroup.mbean.config.TinyModeInfo;
import org.tinygroup.mbean.config.TinyModeInfos;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class MBeanFileProcessor extends MBeanFileConfig implements
		FileProcessor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MBeanFileProcessor.class);
	private static final String MBEAN_FILENAME = ".mbean.xml";

	private List<TinyModeInfo> list = new ArrayList<TinyModeInfo>();
	
	protected List<TinyModeInfo> getTinyModeInfos() {
		return list;
	}
	
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(MBEAN_FILENAME);
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(MBeanFileConfig.MBEAN_XSTREAM_PACKAGENAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除MBean文件[{0}]",
					fileObject.getAbsolutePath());
			TinyModeInfos info = (TinyModeInfos) caches.get(fileObject
					.getAbsolutePath());
			if (info != null) {
				list.remove(info);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除MBean文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在读取MBean文件[{0}]",
					fileObject.getAbsolutePath());
			TinyModeInfos oldModeList = (TinyModeInfos) caches.get(fileObject
					.getAbsolutePath());
			if (oldModeList != null) {
				caches.remove(fileObject.getAbsolutePath());
			}
			InputStream inputStream = fileObject.getInputStream();
			TinyModeInfos modeList = (TinyModeInfos) stream
					.fromXML(inputStream);
			try {
				inputStream.close();
			} catch (Exception e) {
				LOGGER.errorMessage("关闭文件流时出错,文件路径:{}", e,
						fileObject.getAbsolutePath());
			}
			if (modeList != null && !modeList.getModeList().isEmpty()) {
				for (TinyModeInfo info : modeList.getModeList()) {
					list.add(info);
				}
			}
			caches.put(fileObject.getAbsolutePath(), modeList);
			LOGGER.logMessage(LogLevel.INFO, "读取MBean文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		try {
			LOGGER.logMessage(LogLevel.INFO, "正在注册MBean");
			this.loadMBeanServer(getFileResolver().getClassLoader());
			LOGGER.logMessage(LogLevel.INFO, "正在注册MBean结束");
		} catch (Exception e) {
			LOGGER.errorMessage("注册MBean时出现异常", e);
		}
		list.clear();
	}

}

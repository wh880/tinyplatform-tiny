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
package org.tinygroup.annotation.fileresolver;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.annotation.AnnotationExecuteManager;
import org.tinygroup.annotation.config.AnnotationClassMatchers;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

/**
 * 注解文件搜索器
 * 
 * @author renhui
 * 
 */
public class AnnotationFileProcessor extends AbstractFileProcessor {

	private static final String ANNOTATION_EXT_FILENAME = ".annotation.xml";
	private AnnotationExecuteManager manager;
	protected boolean checkMatch(FileObject fileObject) {

		return fileObject.getFileName().endsWith(ANNOTATION_EXT_FILENAME);
	}
	public AnnotationExecuteManager getManager() {
		return manager;
	}

	public void setManager(AnnotationExecuteManager manager) {
		this.manager = manager;
	}
	public void process() {

		
		XStream stream = XStreamFactory
				.getXStream(AnnotationExecuteManager.XSTEAM_PACKAGE_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除注解配置文件[{0}]",
					fileObject.getAbsolutePath());
			AnnotationClassMatchers annotationClassMatchers = (AnnotationClassMatchers) caches.get(fileObject.getAbsolutePath());
			if(annotationClassMatchers!=null){
				manager.removeAnnotationClassMatchers(annotationClassMatchers);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除注解配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载注解配置文件[{0}]",
					fileObject.getAbsolutePath());
			AnnotationClassMatchers oldMatchers = (AnnotationClassMatchers)caches.get(fileObject.getAbsolutePath());
			if(oldMatchers!=null){
				manager.removeAnnotationClassMatchers(oldMatchers);
			}
			InputStream inputStream = fileObject.getInputStream();
			AnnotationClassMatchers matchers = (AnnotationClassMatchers) stream
					.fromXML(inputStream);
			try {
				inputStream.close();
			} catch (Exception e) {
				LOGGER.errorMessage("关闭文件流时出错,文件路径:{}",e, fileObject.getAbsolutePath());
			}
			manager.addAnnotationClassMatchers(matchers);
			caches.put(fileObject.getAbsolutePath(), matchers);
			LOGGER.logMessage(LogLevel.INFO, "加载注解配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

}

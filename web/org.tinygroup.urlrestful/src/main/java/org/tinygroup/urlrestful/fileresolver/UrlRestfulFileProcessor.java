/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.urlrestful.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.urlrestful.config.Rules;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * restful配置文件扫描器
 * 
 * @author renhui
 * 
 */
public class UrlRestfulFileProcessor extends AbstractFileProcessor {

	private static final String RESTFUL_EXT_FILENAME = ".restful.xml";

	private UrlRestfulManager urlRestfulManager;

	public boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().toLowerCase()
				.endsWith(RESTFUL_EXT_FILENAME);
	}

	public void setUrlRestfulManager(UrlRestfulManager urlRestfulManager) {
		this.urlRestfulManager = urlRestfulManager;
	}

	public void process() {
		XStream stream = XStreamFactory
				.getXStream(UrlRestfulManager.URL_RESTFUL_XSTREAM);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除restful文件[{0}]",
					fileObject.getAbsolutePath());
			Rules Rules = (Rules) caches.get(fileObject
					.getAbsolutePath());
			if (Rules != null) {
				urlRestfulManager.removeRules(Rules);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除restful文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载restful文件[{0}]",
					fileObject.getAbsolutePath());
			Rules Rules = (Rules) stream.fromXML(fileObject
					.getInputStream());
			Rules oldRules = (Rules) caches.get(fileObject
					.getAbsolutePath());
			if (oldRules != null) {
				urlRestfulManager.removeRules(oldRules);
			}
			urlRestfulManager.addRules(Rules);
			caches.put(fileObject.getAbsolutePath(), Rules);
			LOGGER.logMessage(LogLevel.INFO, "加载restful文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

}

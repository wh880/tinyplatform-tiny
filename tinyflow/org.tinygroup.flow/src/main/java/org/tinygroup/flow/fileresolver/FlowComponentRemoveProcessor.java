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
package org.tinygroup.flow.fileresolver;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.flow.config.ComponentDefines;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

/**
 * 扫描组件的文件处理器
 * 
 * @author renhui
 * 
 */
public class FlowComponentRemoveProcessor extends AbstractFileProcessor {

	/**
	 * 扫描的文件后缀
	 */
	private static final String FLOW_COMPONENT_EXT_FILENAME = ".fc.xml";
	private FlowExecutor flowExecutor;
	
	public FlowExecutor getFlowExecutor() {
		return flowExecutor;
	}

	public void setFlowExecutor(FlowExecutor flowExecutor) {
		this.flowExecutor = flowExecutor;
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(FLOW_COMPONENT_EXT_FILENAME);
	}

	public void process() {
		XStream stream = XStreamFactory
				.getXStream(FlowExecutor.FLOW_XSTREAM_PACKAGENAME);
		for (FileObject fileObject : fileObjects) {
			LOGGER.logMessage(LogLevel.INFO, "正在删除逻辑组件fc文件[{0}]",
					fileObject.getAbsolutePath());
			ComponentDefines components = (ComponentDefines) stream
					.fromXML(fileObject.getInputStream());
			flowExecutor.removeComponents(components);
			LOGGER.logMessage(LogLevel.INFO, "删除逻辑组件fc文件[{0}]结束",
					fileObject.getAbsolutePath());
		}

	}

	public void setFileResolver(FileResolver fileResolver) {

	}

}

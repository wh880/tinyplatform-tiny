/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.metadata.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.metadata.config.constants.Constants;
import org.tinygroup.metadata.constants.ConstantProcessor;
import org.tinygroup.metadata.util.MetadataUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class ConstantFileResolver extends AbstractFileProcessor {

	private static final String CONSTANT_EXTFILENAME = ".const.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(CONSTANT_EXTFILENAME);
	}

	public void process() {
		ConstantProcessor constantProcessor = SpringUtil
				.getBean(MetadataUtil.CONSTANTPROCESSOR_BEAN);
		XStream stream = XStreamFactory
				.getXStream(MetadataUtil.METADATA_XSTREAM);
		for (FileObject fileObject : fileObjects) {
			logger.logMessage(LogLevel.INFO, "正在加载const文件[{0}]",
					fileObject.getAbsolutePath());
			try {
				Constants constants = (Constants) stream.fromXML(fileObject
						.getInputStream());
				constantProcessor.addConstants(constants);
			} catch (Exception e) {
				logger.errorMessage("加载const文件[{0}]出错", e,
						fileObject.getAbsolutePath());
			}

			logger.logMessage(LogLevel.INFO, "加载const文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

}

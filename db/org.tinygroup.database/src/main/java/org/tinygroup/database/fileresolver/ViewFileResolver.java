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
package org.tinygroup.database.fileresolver;

import org.tinygroup.database.config.view.Views;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.database.view.ViewProcessor;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class ViewFileResolver extends AbstractFileProcessor {

	private static final String VIEW_EXTFILENAME = ".view.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(VIEW_EXTFILENAME);
	}

	public void process() {
		ViewProcessor viewProcessor = SpringUtil
				.getBean(DataBaseUtil.VIEW_BEAN);
		XStream stream = XStreamFactory
				.getXStream(DataBaseUtil.DATABASE_XSTREAM);
		for (FileObject fileObject : fileObjects) {
			logger.logMessage(LogLevel.INFO, "正在加载view文件[{0}]",
					fileObject.getAbsolutePath());
			Views views = (Views) stream.fromXML(fileObject.getInputStream());
			viewProcessor.addViews(views);
			logger.logMessage(LogLevel.INFO, "加载view文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

}

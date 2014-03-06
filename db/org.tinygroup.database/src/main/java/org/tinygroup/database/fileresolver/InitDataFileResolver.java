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

import org.tinygroup.database.config.initdata.InitDatas;
import org.tinygroup.database.initdata.InitDataProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class InitDataFileResolver extends AbstractFileProcessor {
	private static final String INITDATA_EXTFILENAME = ".init.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(INITDATA_EXTFILENAME);
	}

	public void process() {
		logger.logMessage(LogLevel.DEBUG, "开始读取表格初始化数据init文件");
		InitDataProcessor initDataProcessor = SpringUtil
				.getBean(DataBaseUtil.INITDATA_BEAN);
		XStream stream = XStreamFactory
				.getXStream(DataBaseUtil.INITDATA_XSTREAM);
		for (FileObject fileObject : fileObjects) {
			logger.logMessage(LogLevel.DEBUG, "开始读取表格初始化数据init文件{0}",
					fileObject.getAbsolutePath().toString());
			InitDatas initDatas = (InitDatas) stream.fromXML(fileObject
					.getInputStream());
			initDataProcessor.addInitDatas(initDatas);
			logger.logMessage(LogLevel.DEBUG, "读取表格初始化数据init文件{0}完毕",
					fileObject.getAbsolutePath().toString());
		}
		logger.logMessage(LogLevel.DEBUG, "表格初始化数据init文件读取完毕");

	}

}

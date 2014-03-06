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
package org.tinygroup.weblayer.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.weblayer.TinyFilterManager;
import org.tinygroup.weblayer.configmanager.TinyFiterConfigManager;

/**
 * 搜索tinyfilter的文件处理器
 * 
 * 
 */
public class TinyFilterFileProcessor extends AbstractFileProcessor {

	private static final String FILTERS_EXT_FILENAMES = ".tinyfilters.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(FILTERS_EXT_FILENAMES);
	}

	public void process() {
		TinyFilterManager tinyFilterManager = SpringUtil
				.getBean(TinyFilterManager.TINY_FILTER_MANAGER);
		TinyFiterConfigManager configManager = SpringUtil
				.getBean(TinyFiterConfigManager.TINY_FILTER_CONFIGMANAGER);
		for (FileObject fileObject : fileObjects) {
			logger.log(LogLevel.INFO, "找到tiny-filter描述文件：<{}>",
					fileObject.getAbsolutePath());
			configManager.addConfig(fileObject);
		}
		tinyFilterManager.setConfigManager(configManager);
	}

}

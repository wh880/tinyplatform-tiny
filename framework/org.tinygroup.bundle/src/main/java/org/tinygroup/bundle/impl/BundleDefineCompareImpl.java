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
package org.tinygroup.bundle.impl;

import org.tinygroup.bundle.BundleDefineCompare;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.i18n.I18nMessages;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.exception.NotComparableException;

/**
 * 比较两个服务的大小
 *
 * @author luoguo
 *
 */
public class BundleDefineCompareImpl implements BundleDefineCompare{
	private I18nMessages i18nMessages = I18nMessageFactory.getI18nMessages();
	private Logger logger = LoggerFactory.getLogger(NotComparableException.class);
	public int compare(BundleDefine source, BundleDefine dest) {
		boolean isSameServiceId = isSame(source.getId(), dest.getId());
		if (isSameServiceId) {
			return source.getVersion().compareTo(dest.getVersion());
		}
		logger.log(LogLevel.ERROR,"bundle.compareExeception",
				source.getId(),source.getVersion(),dest.getId(),dest.getVersion());
		throw new NotComparableException(
				i18nMessages.getMessage("bundle.compareExeception",
				source.getId(),source.getVersion(),dest.getId(),dest.getVersion())
		);
	}

	private boolean isSame(String source, String dest) {
		return source.equals(dest);
	}
}

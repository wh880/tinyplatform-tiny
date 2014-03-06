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
package org.tinygroup.validate.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.validate.ValidatorManager;
import org.tinygroup.validate.ValidatorMapStorage;
import org.tinygroup.validate.config.Validators;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 验证映射文件搜索器
 * 
 * @author renhui
 * 
 */
public class ValidateMapFileProcessor extends AbstractFileProcessor {

	private static final String VALIDATE_MAP_FILE_SUFFIX = ".validatemap.xml";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(VALIDATE_MAP_FILE_SUFFIX);
	}

	public void process() {
		ValidatorMapStorage validatorManager = SpringUtil
				.getBean(ValidatorMapStorage.VALIDATOR_MAP_BEAN_NAME);

		XStream stream = XStreamFactory
				.getXStream(ValidatorManager.XSTEAM_PACKAGE_NAME);

		for (FileObject fileObject : fileObjects) {

			logger.logMessage(LogLevel.DEBUG, "正在加载校验映射配置文件[{0}]",
					fileObject.getAbsolutePath());
			try {
				Validators validators = (Validators) stream.fromXML(fileObject
						.getInputStream());
				validatorManager.addValidators(validators);
			} catch (Exception e) {
				logger.errorMessage("加载校验映射配置文件[{0}]出错", e,fileObject.getAbsolutePath());
			}
			
			logger.logMessage(LogLevel.DEBUG, "加载校验映射配置文件[{0}]结束",
					fileObject.getAbsolutePath());

		}

	}

}

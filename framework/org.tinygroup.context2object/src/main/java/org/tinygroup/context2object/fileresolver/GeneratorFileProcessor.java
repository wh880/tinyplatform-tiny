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
package org.tinygroup.context2object.fileresolver;

import org.tinygroup.context2object.TypeConverter;
import org.tinygroup.context2object.TypeCreator;
import org.tinygroup.context2object.config.GeneratorConfig;
import org.tinygroup.context2object.config.GeneratorConfigItem;
import org.tinygroup.context2object.impl.ClassNameObjectGenerator;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class GeneratorFileProcessor extends AbstractFileProcessor {
	private static Logger logger = LoggerFactory
			.getLogger(GeneratorFileProcessor.class);
	private static final String GENERATOR_EXT_FILENAME = ".generatorconfig.xml";
	public static final String CLASSNAME_OBJECT_GENERATOR_BEAN = "classNameObjectGenerator";
	public static final String CONTEXT2OBJECT_XSTREAM = "context2object";

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(GENERATOR_EXT_FILENAME);
	}

	public void process() {
		ClassNameObjectGenerator generator = SpringUtil
				.getBean(CLASSNAME_OBJECT_GENERATOR_BEAN);
		XStream stream = XStreamFactory.getXStream(CONTEXT2OBJECT_XSTREAM);
		for (FileObject file : fileObjects) {
			logger.logMessage(LogLevel.INFO, "开始读取generator配置文件:{0}",
					file.getFileName());
			GeneratorConfig config = (GeneratorConfig) stream.fromXML(file
					.getInputStream());
			deal(config, generator);
			logger.logMessage(LogLevel.INFO, "读取generator配置文件:{0}完成",
					file.getFileName());

		}
	}

	private void deal(GeneratorConfig config, ClassNameObjectGenerator generator) {
		logger.logMessage(LogLevel.INFO, "开始读取generator配置TypeConverter");
		for (GeneratorConfigItem item : config.getTypeConverters()) {
			logger.logMessage(LogLevel.INFO,
					"处理TypeConverter,beanName:{0},className:{1}",
					item.getBeanName(), item.getClassName());
			TypeConverter o = (TypeConverter) deal(item);
			generator.addTypeConverter(o);
		}
		logger.logMessage(LogLevel.INFO, "读取generator配置TypeConverter完成");
		logger.logMessage(LogLevel.INFO, "开始读取generator配置TypeCreator");
		for (GeneratorConfigItem item : config.getTypeCreators()) {
			logger.logMessage(LogLevel.INFO,
					"处理TypeCreator,beanName:{0},className:{1}",
					item.getBeanName(), item.getClassName());
			TypeCreator o = (TypeCreator) deal(item);
			generator.addTypeCreator(o);
		}
		logger.logMessage(LogLevel.INFO, "读取generator配置TypeCreator完成");
	}

	private Object deal(GeneratorConfigItem configItem) {
		String beanName = configItem.getBeanName();
		if (beanName == null || "".equals(beanName)) {
			try {
				return Class.forName(configItem.getClassName()).newInstance();
				// 20130808注释LoaderManagerFactory
				// return LoaderManagerFactory.getManager()
				// .getClass(configItem.getClassName()).newInstance();
			} catch (Exception e) {
				logger.errorMessage("generator配置文件加载时，创建类:{0}出错", e,
						configItem.getClassName());
				throw new RuntimeException(e);
			}
		}
		{
			return SpringUtil.getBean(beanName);
		}
	}

}

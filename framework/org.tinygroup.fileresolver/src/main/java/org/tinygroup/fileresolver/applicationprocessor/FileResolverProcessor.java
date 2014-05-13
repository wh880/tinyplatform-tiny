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
package org.tinygroup.fileresolver.applicationprocessor;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 加载处理器，并执行加载
 * 
 * @author luoguo
 * 
 */
public class FileResolverProcessor implements ApplicationProcessor {
	private static final String FILE_RESOLVER_NODE_NAME = "/application/file-resolver-configuration";
	private static Logger logger = LoggerFactory
			.getLogger(FileResolverProcessor.class);
	private XmlNode fileResolverConfiguration;

	private FileResolver fileResolver;


	public String getApplicationNodePath() {
		return FILE_RESOLVER_NODE_NAME;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		fileResolverConfiguration = applicationConfig;
	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public XmlNode getApplicationConfig() {
		return fileResolverConfiguration;
	}

	public void start() {
		fileResolver.resolve(null);
	}

	public void stop() {
		// Nothing
		
	}

	public void setApplication(Application application) {
		// Nothing
		
	}

	
	public FileResolver getFileResolver() {
		return fileResolver;
	}

	public void setFileResolver(FileResolver fileResolver) {
		this.fileResolver = fileResolver;
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}
}

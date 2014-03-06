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
package org.tinygroup.weblayer.configmanager;

import static org.tinygroup.logger.LogLevel.INFO;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.config.Configuration;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;


/**
 * tiny processor处理器配置管理对象
 * @author renhui
 *
 */
public class TinyProcessorConfigManager implements Configuration {
	
    public static final String TINY_PROCESSOR_CONFIGMANAGER = "tinyProcessorConfigManager";
    
    private static final String TINY_PROCESSOR_NODE_PATH="/application/tiny-processors";


	private List<XmlNode> configs=new ArrayList<XmlNode>();
	
	private XmlNode applicationConfig;
	
	private XmlNode componentConfig;
	 
	private static Logger logger = LoggerFactory
		.getLogger(TinyProcessorConfigManager.class);
	
	 
	 public void  addConfig(FileObject fileObject){
		 String filePath=fileObject.getPath();
		 logger.logMessage(INFO, "正在加载tiny-processor处理器配置文件[{0}] ....",filePath
					);
			try {

				String content = FileUtil.readStreamContent(fileObject.getInputStream(),"UTF-8");
				logger.logMessage(INFO, "tiny-processor处理器配置文件<{0}>加载完成。.",
						filePath);
				XmlNode root = new XmlStringParser().parse(content)
						.getRoot();
				configs.add(root);
			} catch (Exception e) {
				logger.errorMessage("载入tiny-processor配置文件<{}>时发生异常", e,
						filePath);
				throw new RuntimeException(e);
			}
		 
	 }
	 

	public XmlNode getApplicationConfig(){
		return applicationConfig;
	}

	public List<XmlNode> getConfigs() {
		return configs;
	}

	public String getApplicationNodePath() {
		return TINY_PROCESSOR_NODE_PATH;
	}

	public String getComponentConfigPath() {
		return "/tinyprocessor.config.xml";
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		this.applicationConfig = applicationConfig;
		this.componentConfig = componentConfig;
	}

	public XmlNode getComponentConfig() {
		return componentConfig;
	}
   
	
	
	
}

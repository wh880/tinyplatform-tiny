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
package org.tinygroup.template.fileresolver;

import java.util.List;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * tiny宏文件扫描
 *
 * @author renhui
 */
public class TinyMacroFileProcessor extends AbstractFileProcessor {

    private static final String COMPONENT_FILE_NAME = ".component";

    private static final String TINY_TEMPLATE_CONFIG = "/application/template-config";
    private static final String TINY_TEMPLATE_CONFIG_PATH = "/templateconfig.config.xml";
    //配置的三个参数名
    private static final String TEMPLATE_EXT_FILE_NAME = "templateExtFileName";
    private static final String LAYOUT_EXT_FILE_NAME = "layoutExtFileName";
    private static final String COMPONENT_EXT_FILE_NAME = "componentExtFileName";
    //配置参数的默认值
    private static final String TEMPLATE_EXT_DEFALUT = "page";
    private static final String LAYOUT_EXT_DEFALUT = "layout";
    private static final String COMPONENT_EXT_DEFALUT = "component";

    private TemplateEngine engine;


    private static boolean hasResourceLoader = false;

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

   	public boolean checkMatch(FileObject fileObject) {
        if (!fileObject.isFolder() && fileObject.getFileName().endsWith(COMPONENT_FILE_NAME)) {
            return true;
        }
        return false;
    }

    public void process() {
        if (!hasResourceLoader) {
        	reloadTemplateConfig();
            hasResourceLoader = true;
        }
        for (FileObject fileObject : changeList) {
        	LOGGER.logMessage(LogLevel.INFO, "模板配置文件[{0}]开始加载",
                    fileObject.getAbsolutePath());
            try {
                engine.registerMacroLibrary(fileObject.getPath());
            } catch (TemplateException e) {
            	LOGGER.errorMessage("加载模板配置文件[{0}]出错", e,
                        fileObject.getAbsolutePath());
            }
            LOGGER.logMessage(LogLevel.INFO, "模板配置文件[{0}]加载完毕",
                    fileObject.getAbsolutePath());
        }

    }
    
    /**
     * 设置模板引擎TemplateEngine
     */
    private void reloadTemplateConfig(){
    	//合并节点
        XmlNode totalConfig = ConfigurationUtil.combineXmlNode(applicationConfig, componentConfig);

        String templateExtFileName = TEMPLATE_EXT_DEFALUT;
        String layoutExtFileName = LAYOUT_EXT_DEFALUT;
        String componentExtFileName = COMPONENT_EXT_DEFALUT;
        
        if (totalConfig != null) {
            templateExtFileName = StringUtil.defaultIfBlank(totalConfig.getAttribute(TEMPLATE_EXT_FILE_NAME), TEMPLATE_EXT_DEFALUT);
            layoutExtFileName = StringUtil.defaultIfBlank(totalConfig.getAttribute(LAYOUT_EXT_FILE_NAME), LAYOUT_EXT_DEFALUT);
            componentExtFileName = StringUtil.defaultIfBlank(totalConfig.getAttribute(COMPONENT_EXT_FILE_NAME), COMPONENT_EXT_DEFALUT);
        }
     
        //系统内置资源加载器

        List<String> scanningPaths = fileResolver.getScanningPaths();
        for (String path : scanningPaths) {
            FileObjectResourceLoader fileResourceLoader = new FileObjectResourceLoader(
                    templateExtFileName, layoutExtFileName,
                    componentExtFileName, path);
            engine.addResourceLoader(fileResourceLoader);
        }
    }

    public void config(XmlNode applicationConfig, XmlNode componentConfig) {
        super.config(applicationConfig, componentConfig);
    }

    public String getComponentConfigPath() {
		return TINY_TEMPLATE_CONFIG_PATH;
	}
    
    public String getApplicationNodePath() {
        return TINY_TEMPLATE_CONFIG;
    }

}

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
package org.tinygroup.templateweb;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;

import java.util.List;

public class WebMacroFileProcessor extends AbstractFileProcessor {

	private static final String COMPONENT_FILE_NAME = ".component";

	private static final String TINY_TEMPLATE_CONFIG = "template-config";

	private TemplateEngine engine;
	
	private static boolean hasResouceLoader=false; 
	
	public TemplateEngine getEngine() {
		return engine;
	}

	public void setEngine(TemplateEngine engine) {
		this.engine = engine;
	}

	public void process() {
		if(!hasResouceLoader){
			addResourceLoaders();
			hasResouceLoader=true;
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "宏模板配置文件[{0}]开始加载",
					fileObject.getAbsolutePath());
			try {
				engine.registerMacroLibrary(fileObject.getPath());
			} catch (TemplateException e) {
				LOGGER.errorMessage("加载宏模板配置文件[{0}]出错", e,
						fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "宏模板配置文件[{0}]加载完毕",
					fileObject.getAbsolutePath());
		}

	}

	private void addResourceLoaders() {
		String templateExtFileName = null;
		String layoutExtFileName = null;
		String componentExtFileName = null;
		if (applicationConfig != null) {
			templateExtFileName = applicationConfig
					.getAttribute("templateExtFileName");
			layoutExtFileName = applicationConfig
					.getAttribute("layoutExtFileName");
			componentExtFileName = applicationConfig
					.getAttribute("componentExtFileName");
		}
		if (StringUtil.isBlank(templateExtFileName)) {
			templateExtFileName = "vm";
		}
		if (StringUtil.isBlank(layoutExtFileName)) {
			layoutExtFileName = "layout";
		}
		if (StringUtil.isBlank(componentExtFileName)) {
			componentExtFileName = "component";
		}

		List<String> scanningPaths = fileResolver.getScanningPaths();
		for (String path : scanningPaths) {
			FileObjectResourceLoader fileResourceLoader = new FileObjectResourceLoader(
					templateExtFileName, layoutExtFileName,
					componentExtFileName, path);
			engine.addResourceLoader(fileResourceLoader);
		}
	}
	
	public String getApplicationNodePath() {
		return TINY_TEMPLATE_CONFIG;
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(COMPONENT_FILE_NAME);
	}

}

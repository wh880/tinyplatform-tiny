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

import org.springframework.web.util.NestedServletException;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 添加url处理器 Created by luoguo on 2014/7/14.
 */
public class TinyTemplateProcessor extends AbstractTinyProcessor {

	private TemplateEngine engine;
	private static final Logger logger = LoggerFactory
			.getLogger(TinyTemplateProcessor.class);
	private static final String PAGELET_EXT_FILE_NAME = ".pagelet";
	private static final String PAGE_EXT_FILE_NAME = ".page";
	private static final String TEMPLATE_EXT_FILE_NAME = "templateExtFileName";
	private static final String TEMPLATE_WITH_LAYOUT_EXT_FILE_NAME = "templateWithLayoutExtFileName";
	private String templateWithLayout = PAGE_EXT_FILE_NAME;
	private String template = PAGELET_EXT_FILE_NAME;

	public TemplateEngine getEngine() {
		return engine;
	}

	public void setEngine(TemplateEngine engine) {
		this.engine = engine;
	}

	public void reallyProcess(String servletPath, WebContext context)
			throws ServletException, IOException {
		HttpServletResponse response = context.getResponse();
		try {
			long startTime = System.currentTimeMillis();
			TemplateContext templateContext = new TinyWebTemplateContext(
					context);
			boolean isPagelet = false;
			if (servletPath.endsWith(template)) {
				isPagelet = true;
			}
			context.put(
					"uiengine",
					BeanContainerFactory.getBeanContainer(
							this.getClass().getClassLoader()).getBean(
							"uiComponentManager"));
			if (isPagelet) {
				engine.renderTemplateWithOutLayout(
						servletPath.substring(0, servletPath.length()
								- template.length())
								+ templateWithLayout, templateContext,
						response.getOutputStream());
			} else {
				engine.renderTemplate(servletPath, templateContext,
						response.getOutputStream());
			}
			long endTime = System.currentTimeMillis();
			logger.logMessage(LogLevel.DEBUG, "路径<{}>处理时间：{}ms", servletPath,
					endTime - startTime);
		} catch (Exception e) {
			throw new NestedServletException("tiny template render error", e);
		}
	}

	@Override
	protected void customInit() throws ServletException {
		// 初始化时候对xml文档的内容进行读取 //这个是重写超类方法
		templateWithLayout = StringUtil.defaultIfBlank(
				getInitParamMap().get(TEMPLATE_WITH_LAYOUT_EXT_FILE_NAME),
				PAGE_EXT_FILE_NAME);
		template = StringUtil.defaultIfBlank(
				getInitParamMap().get(TEMPLATE_EXT_FILE_NAME),
				PAGELET_EXT_FILE_NAME);
	}

}

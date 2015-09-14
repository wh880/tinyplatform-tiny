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
package org.tinygroup.templatespringext.springext;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * tiny模板类的view对象
 * 
 * @author wll
 * 
 */
public class TinyTemplateLayoutView extends AbstractTemplateView {

	private TemplateEngine templateEngine;

	private static final String VIEW_EXT_FILENAME = "page";// 视图扩展后缀
	private static final String PAGELET_EXT_FILE_NAME = "pagelet";

	private String viewExtFileName = VIEW_EXT_FILENAME;

	private String noLayoutExtFileName = PAGELET_EXT_FILE_NAME;

	private static final Logger logger = LoggerFactory
			.getLogger(TinyTemplateLayoutView.class);

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public void setViewExtFileName(String viewExtFileName) {
		this.viewExtFileName = viewExtFileName;
	}

	public void setNoLayoutExtFileName(String noLayoutExtFileName) {
		this.noLayoutExtFileName = noLayoutExtFileName;
	}

	public boolean checkResource(Locale locale) throws Exception {
		String path = getExtFileName(getUrl());
		try {
			templateEngine.findTemplate(path);
			return true;
		} catch (Exception e) {
			logger.logMessage(LogLevel.DEBUG,
					"Could not load tiny template for URL [{0}]", e, path);
		}
		return false;
	}

	private boolean isPagelet(String path) {
		return StringUtils.endsWith(path, noLayoutExtFileName);
	}

	private String getExtFileName(String path) {
		if (isPagelet(path)) {
			return StringUtils.substringBeforeLast(path, noLayoutExtFileName)
					+ viewExtFileName;
		}
		return path;
	}


	protected void applyContentType(HttpServletResponse response) {
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		long startTime = System.currentTimeMillis();

		TemplateContext templateContext = new TinyWebTemplateContext(model);
		String path = getUrl();
		String extFileName=getExtFileName(path);
		if (isPagelet(path)) {
			templateEngine.renderTemplateWithOutLayout(extFileName, templateContext,response.getOutputStream());
		} else {
			templateEngine.renderTemplate(extFileName, templateContext,response.getOutputStream());
		}
		logger.logMessage(LogLevel.DEBUG, "路径<{}>处理时间：{}ms", path,
				System.currentTimeMillis() - startTime);

	}
}

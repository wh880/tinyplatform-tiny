/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.springmvc.view;

import org.springframework.web.servlet.view.AbstractTemplateView;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.support.TinyWebTemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.weblayer.webcontext.util.WebContextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * tiny模板类的view对象
 * 
 * @author renhui
 * 
 */
public class TinyTemplateLayoutView extends AbstractTemplateView {

	private TemplateEngine templateEngine;

	private static final Logger logger = LoggerFactory
			.getLogger(TinyTemplateLayoutView.class);

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long startTime = System.currentTimeMillis();
		model.put("uiengine",
				getApplicationContext().getBean("uiComponentManager"));
		templateEngine.renderTemplate(getUrl(), new TinyWebTemplateContext(
				model, WebContextUtil.getWebContext(request)), response
				.getWriter());
		logger.logMessage(LogLevel.DEBUG, "路径<{}>处理时间：{}ms", getUrl(),
				System.currentTimeMillis() - startTime);

	}

	public boolean checkResource(Locale locale) throws Exception {
		String path = getUrl();
		try {
			templateEngine.findTemplate(path);
			return true;
		} catch (Exception e) {
			logger.logMessage(LogLevel.DEBUG,
					"Could not load tiny template for URL [{0}]",e, path);
		}
		return false;
	}

	// 不设置字符集编码，由过滤器进行设置
	@Override
	protected void applyContentType(HttpServletResponse response) {

	}

}

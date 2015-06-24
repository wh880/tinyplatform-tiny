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
					"Could not load tiny template for URL [{0}]", path);
		}
		return false;
	}

	// 不设置字符集编码，由过滤器进行设置
	@Override
	protected void applyContentType(HttpServletResponse response) {

	}

}

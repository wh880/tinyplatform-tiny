package org.tinygroup.aopcache.base;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.springutil.MethodNameAccessTool;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.StringResourceLoader;

/**
 * 模板渲染类
 * 
 * @author renhui
 *
 */
public class TemplateRender {

	private TemplateEngine templateEngine;

	private StringResourceLoader resourceLoader;

	private static TemplateRender templateRender;

	private TemplateRender() {
		templateEngine = new TemplateEngineDefault();
		resourceLoader = new StringResourceLoader();
		templateEngine.addResourceLoader(resourceLoader);
	}

	public static TemplateRender newInstance() {
		if (templateRender == null) {
			templateRender = new TemplateRender();
		}
		return templateRender;
	}

	public String renderTemplate(MethodInvocation invocation, String content)
			throws TemplateException, UnsupportedEncodingException {
		if (StringUtil.isBlank(content)) {
			return "";
		}
		TemplateContext templateContext=assemblyContext(invocation);
		return renderTemplate(templateContext, content);
	}

	public String renderTemplate(TemplateContext context, String content)
			throws TemplateException, UnsupportedEncodingException {
		if (StringUtil.isBlank(content)) {
			return "";
		}
		Template template = resourceLoader.loadTemplate(content);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		template.render(context, writer);
		return new String(writer.toByteArray(), "UTF-8");
	}

	public TemplateContext assemblyContext(MethodInvocation invocation) {
		TemplateContext context = new TemplateContextDefault();
		Method method = invocation.getMethod();
		String[] paramNames = MethodNameAccessTool
				.getMethodParameterName(method);
		if (paramNames != null) {
			for (int i = 0; i < paramNames.length; i++) {
				context.put(paramNames[i], invocation.getArguments()[i]);
			}
		}
		return context;
	}

	public Object getParamValue(TemplateContext context, String key) {
		return context.get(key);
	}

}

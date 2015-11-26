package org.tinygroup.template.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.TemplateRender;
import org.tinygroup.template.loader.StringResourceLoader;

/**
 * 解决文件加载器和字符串加载器行为差异过大的问题
 * 
 * @author yancheng11334
 * 
 */
public class TemplateRenderDefault implements TemplateRender {

	private TemplateEngine templateEngine;
	private BeanContainer<?> beanContainer;
	private StringResourceLoader contentLoader = new StringResourceLoader();
	private boolean initResourceLoader = false;

	private void initResourceLoader(){
		if (!initResourceLoader) {
			initResourceLoader = true;
			getTemplateEngine().addResourceLoader(contentLoader);
		}
	}
	public TemplateRenderDefault() {
		beanContainer = BeanContainerFactory.getBeanContainer(getClass()
				.getClassLoader());
	}

	public void setTemplateEngine(TemplateEngine engine) {
		this.templateEngine = engine;
	}

	public TemplateEngine getTemplateEngine() {
		if (templateEngine == null) {
			return beanContainer.getBean(TemplateEngine.DEFAULT_BEAN_NAME);
		}
		return templateEngine;
	}

	public void renderTemplate(String path, TemplateContext context,
			OutputStream outputStream) throws TemplateException {
		getTemplateEngine().renderTemplate(path, context, outputStream);
	}

	public String renderTemplate(String path, TemplateContext context)
            throws TemplateException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			renderTemplate(path, context, outputStream);
			return new String(outputStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new TemplateException(e);
		} finally {
			outputStream.close();
		}
	}

	public void renderTemplateWithOutLayout(String path,
			TemplateContext context, OutputStream outputStream)
			throws TemplateException {
		getTemplateEngine().renderTemplateWithOutLayout(path, context,
				outputStream);
	}

	public String renderTemplateWithOutLayout(String path,
			TemplateContext context) throws TemplateException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			renderTemplateWithOutLayout(path, context, outputStream);
			return new String(outputStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new TemplateException(e);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				throw new TemplateException(e);
			}
		}
	}

	public void renderTemplateContent(String content, TemplateContext context,
			OutputStream outputStream) throws TemplateException {
		initResourceLoader();
		Template template = contentLoader.loadTemplate(content);
		getTemplateEngine().renderTemplate(template, context, outputStream);
	}

	public String renderTemplateContent(String content, TemplateContext context)
            throws TemplateException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			renderTemplateContent(content, context, outputStream);
			return new String(outputStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new TemplateException(e);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				throw new TemplateException(e);
			}
		}
	}

}

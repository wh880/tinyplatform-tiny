package org.tinygroup.templateindex.impl;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.template.TemplateRender;
import org.tinygroup.templateindex.TemplateIndexRender;

/**
 * 抽象模板索引渲染器
 * 
 * @author yancheng11334
 * 
 */
public abstract class AbstractTemplateIndexRender implements
		TemplateIndexRender {

	private static final String DEFALUT_TEMPLATE_RENDER = "fullTextTemplateRender";
	private TemplateRender templateRender;

	public TemplateRender getTemplateRender() {
		if (templateRender == null) {
			return BeanContainerFactory.getBeanContainer(
					getClass().getClassLoader()).getBean(
					DEFALUT_TEMPLATE_RENDER);
		}
		return templateRender;
	}

	public void setTemplateRender(TemplateRender templateRender) {
		this.templateRender = templateRender;
	}

}

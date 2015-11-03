package org.tinygroup.templatei18n;

import java.util.Locale;

import org.tinygroup.template.TemplateContext;

/**
 * 从上下文获取Locale
 * @author yancheng11334
 *
 */
public class ContextLocaleI18nVisitor extends AbstractI18nVisitor{

	private String localeName;
	
	public Locale getLocale(TemplateContext context) {
		return getContextLocale(context);
	}
	
	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	protected Locale getContextLocale(TemplateContext context) {
		return (localeName==null || context==null)?null:(Locale)context.get(localeName);
	}

}

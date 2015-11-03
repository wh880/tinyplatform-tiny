package org.tinygroup.templatei18n;

import java.util.Locale;

import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.template.TemplateContext;

/**
 * LocaleUtil取得默认的locale
 * @author yancheng11334
 *
 */
public class DefaultLocaleI18nVisitor extends AbstractI18nVisitor{
	
	public Locale getLocale(TemplateContext context) {
		return LocaleUtil.getDefault().getLocale();
	}

}

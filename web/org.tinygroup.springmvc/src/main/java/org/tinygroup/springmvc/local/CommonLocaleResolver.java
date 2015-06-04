package org.tinygroup.springmvc.local;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.tinygroup.commons.i18n.LocaleUtil;

/**
 * 
 * @author renhui
 * 
 */
public class CommonLocaleResolver extends AbstractLocaleResolver {

	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = LocaleUtil.getContext().getLocale();
		if (locale == null) {
			locale = getDefaultLocale();
			if (locale == null) {
				locale = request.getLocale();
			}
		}
		return locale;
	}

	public void setLocale(HttpServletRequest request,
			HttpServletResponse response, Locale locale) {
		LocaleUtil.setContext(locale);
	}

}

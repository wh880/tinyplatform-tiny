package org.tinygroup.springmvc.theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ThemeResolver;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;

public class TinyThemeResolver implements ThemeResolver {
	protected static final Logger logger = LoggerFactory
			.getLogger(TinyThemeResolver.class);

	public String resolveThemeName(HttpServletRequest request) {
		ThemeResolver themeResolver = RequestInstanceHolder
				.getMappingInstance().getCommonThemeResolver();
		if (themeResolver != null) {
			logger.logMessage(LogLevel.DEBUG,
					" invoke themeResolver.resolveThemeName() method that will proxy ["
							+ themeResolver + "]");
			return themeResolver.resolveThemeName(request);
		}
		return null;
	}

	public void setThemeName(HttpServletRequest request,
			HttpServletResponse response, String themeName) {
		ThemeResolver themeResolver = RequestInstanceHolder
				.getMappingInstance().getCommonThemeResolver();
		if (themeResolver != null) {
			logger.logMessage(LogLevel.DEBUG,
					" invoke themeResolver.setThemeName() method that will proxy ["
							+ themeResolver + "]");
			themeResolver.setThemeName(request, response, themeName);
		}
	}

}

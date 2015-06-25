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
package org.tinygroup.springmvc.local;

import org.springframework.web.servlet.LocaleResolver;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class TinyLocaleResolver implements LocaleResolver {

	protected static final Logger logger = LoggerFactory
			.getLogger(TinyLocaleResolver.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.LocaleResolver#resolveLocale(javax.servlet
	 * .http.HttpServletRequest)
	 */
	public Locale resolveLocale(HttpServletRequest request) {
		Profiler.enter("[CarLocaleResolver.resolveLocale()]");
		try {
			LocaleResolver localeResolver = RequestInstanceHolder
					.getMappingInstance().getCommonLocaleResolver();

			if (localeResolver != null) {
				logger.logMessage(LogLevel.DEBUG,
						" invoke localeResolver.resolveLocale() method that will proxy ["
								+ localeResolver + "]");
				return localeResolver.resolveLocale(request);
			}

			return null;
		} finally {
			Profiler.release();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.LocaleResolver#setLocale(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.util.Locale)
	 */
	public void setLocale(HttpServletRequest request,
			HttpServletResponse response, Locale locale) {
		LocaleResolver localeResolver = RequestInstanceHolder
				.getMappingInstance().getCommonLocaleResolver();

		if (localeResolver != null) {
			logger.logMessage(LogLevel.DEBUG,
					" invoke localeResolver.setLocale() method that will proxy ["
							+ localeResolver + "]");
			localeResolver.setLocale(request, response, locale);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TinyLocaleResolver";
	}
}

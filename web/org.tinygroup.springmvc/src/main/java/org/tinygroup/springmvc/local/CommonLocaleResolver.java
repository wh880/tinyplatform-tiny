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

import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.tinygroup.commons.i18n.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

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

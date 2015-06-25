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
package org.tinygroup.springmvc.view;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class TinyViewResolver implements ViewResolver {

	protected static final Logger logger = LoggerFactory
			.getLogger(TinyViewResolver.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang
	 * .String, java.util.Locale)
	 */
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		Profiler.enter("[TinyViewResolver.resolveViewName()]");
		try {
			List<ViewResolver> viewResolvers = RequestInstanceHolder
					.getMappingInstance().getViewResolvers();
			for (Iterator<ViewResolver> it = viewResolvers.iterator(); it
					.hasNext();) {
				ViewResolver viewResolver = it.next();
				View view = viewResolver.resolveViewName(viewName, locale);
				if (view != null) {
					logger.logMessage(LogLevel.DEBUG,
							" invoke car viewResolver.resolveViewName() method that will proxy ["
									+ viewResolver + "]");
					return view;
				}
			}
			return null;
		} finally {
			Profiler.release();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TinyViewResolver";
	}
}

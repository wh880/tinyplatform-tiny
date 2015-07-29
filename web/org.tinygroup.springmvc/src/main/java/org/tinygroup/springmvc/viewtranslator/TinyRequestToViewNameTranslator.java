/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
package org.tinygroup.springmvc.viewtranslator;

import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

import javax.servlet.http.HttpServletRequest;

public class TinyRequestToViewNameTranslator implements
		RequestToViewNameTranslator {

	protected static final Logger logger = LoggerFactory
			.getLogger(TinyRequestToViewNameTranslator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.RequestToViewNameTranslator#getViewName
	 * (javax.servlet.http.HttpServletRequest)
	 */
	public String getViewName(HttpServletRequest request) throws Exception {
		Profiler.enter("[TinyRequestToViewNameTranslator.getViewName()]");
		try {
			RequestToViewNameTranslator viewNameTranslator = RequestInstanceHolder
					.getMappingInstance().getViewNameTranslator();

			if (viewNameTranslator != null) {
				logger.logMessage(LogLevel.DEBUG,
						"invoke requestToViewNameTranslator.getViewName() method that will proxy ["
								+ viewNameTranslator + "]");
				return viewNameTranslator.getViewName(request);
			}

			// 应该不会执行
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
		return "TinyRequestToViewNameTranslator";
	}

}

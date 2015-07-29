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
package org.tinygroup.springmvc.exceptionresolver;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 委托ExtensionMappingInstance中的HandlerExceptionResolver进行异常解析处理
 * @author renhui
 *
 */
public class TinyHandlerExceptionResolver implements HandlerExceptionResolver,
		ExceptionConstants {
	protected static final Logger logger = LoggerFactory
			.getLogger(TinyHandlerExceptionResolver.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerExceptionResolver#resolveException
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Profiler.enter("[CarHandlerExceptionResolver.resolveException()]");
		try {
			List<HandlerExceptionResolver> handlerExceptionResolvers = RequestInstanceHolder
					.getMappingInstance().getHandlerExceptionResolvers();

			// Check registerer HandlerExceptionResolvers...
			ModelAndView exMv = null;
			HandlerExceptionResolver resolver = null;
			for (Iterator<HandlerExceptionResolver> it = handlerExceptionResolvers
					.iterator(); exMv == null && it.hasNext();) {
				resolver = (HandlerExceptionResolver) it.next();
				exMv = resolver
						.resolveException(request, response, handler, ex);
			}
			if (exMv != null) {
				logger.logMessage(
						LogLevel.DEBUG,
						" invoke handlerExceptionResolver.resolveException() method that will proxy ["
								+ resolver + "]");
				return exMv;
			}

			return null;
		} finally {
			Profiler.release();
		}
	}
}

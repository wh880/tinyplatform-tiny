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
package org.tinygroup.springmvc.multipart;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author renhui
 * 
 */
public class TinyMultipartResolver implements MultipartResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TinyMultipartResolver.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.multipart.MultipartResolver#cleanupMultipart(
	 * org.springframework.web.multipart.MultipartHttpServletRequest)
	 */
	public void cleanupMultipart(MultipartHttpServletRequest request) {
		MultipartResolver multipartResolver = RequestInstanceHolder
				.getMappingInstance().getCommonsMultipartResolver();

		if (multipartResolver != null) {
			LOGGER.logMessage(
					LogLevel.DEBUG,
					" invoke multipartResolver.cleanupMultipart() method that will proxy [{0}]",
					multipartResolver);
			multipartResolver.cleanupMultipart(request);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.multipart.MultipartResolver#isMultipart(javax
	 * .servlet.http.HttpServletRequest)
	 */
	public boolean isMultipart(HttpServletRequest request) {
		MultipartResolver multipartResolver = RequestInstanceHolder
				.getMappingInstance().getCommonsMultipartResolver();
		if (multipartResolver != null) {
			LOGGER.logMessage(
					LogLevel.DEBUG,
					" invoke multipartResolver.isMultipart() method that will proxy [{0}]",
					multipartResolver);
			return multipartResolver.isMultipart(request);
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.multipart.MultipartResolver#resolveMultipart(
	 * javax.servlet.http.HttpServletRequest)
	 */
	public MultipartHttpServletRequest resolveMultipart(
			HttpServletRequest request) throws MultipartException {
		Profiler.enter("[CarMultipartResolver.resolveMultipart()]");
		try {
			MultipartResolver multipartResolver = RequestInstanceHolder
					.getMappingInstance().getCommonsMultipartResolver();
			if (multipartResolver != null) {
				LOGGER.logMessage(
						LogLevel.DEBUG,
						" invoke car multipartResolver.resolveMultipart() method that will proxy [{0}]",
						multipartResolver);
				return multipartResolver.resolveMultipart(request);
			} else {
				return null;
			}
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
		return "TinyMultipartResolver";
	}

}

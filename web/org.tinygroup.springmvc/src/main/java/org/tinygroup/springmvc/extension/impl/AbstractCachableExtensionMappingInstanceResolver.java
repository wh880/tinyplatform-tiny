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
package org.tinygroup.springmvc.extension.impl;

import org.springframework.util.Assert;
import org.springframework.web.util.UrlPathHelper;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.ExtensionMappingInstance;
import org.tinygroup.springmvc.extension.ExtensionMappingInstanceResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author renhui
 */
public abstract class AbstractCachableExtensionMappingInstanceResolver
		implements ExtensionMappingInstanceResolver {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractCachableExtensionMappingInstanceResolver.class);

	private final Map<String, ExtensionMappingInstance> extensionMappings = new HashMap<String, ExtensionMappingInstance>(
			6);

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private String defaultContentExtension = "shtm";

	public void setDefaultContentExtension(String defaultContentExtension) {
		this.defaultContentExtension = defaultContentExtension;
	}

	public UrlPathHelper getUrlPathHelper() {
		return urlPathHelper;
	}

	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		this.urlPathHelper = urlPathHelper;
	}

	public ExtensionMappingInstance get(HttpServletRequest request) {
		// retrieve it from the request cache
		ExtensionMappingInstance emi = (ExtensionMappingInstance) request
				.getAttribute(getCacheKey(request));

		if (emi != null) {
			return emi;
		}

		// negotiation the right file extension
		// extension是否合法，没有则默认
		String ext = getExtension(request);

		// get emi
		emi = get(ext);
		if (null == emi) {
			throw new RuntimeException("found no config of [*." + ext
					+ "]! add right plugin,or config with yourself! ");
		}
		request.setAttribute(getCacheKey(request), emi);
		return emi;
	}

	// public void cleanCache(HttpServletRequest request) {
	// request.removeAttribute(getCacheKey(request));
	// }

	private String getCacheKey(HttpServletRequest request) {
		String realPath = this.getUrlPathHelper().getLookupPathForRequest(
				request);// main request or component Path
		return new StringBuilder(realPath).append(
				ExtensionMappingInstance.class.getName()).toString();
	}

	public ExtensionMappingInstance get(String ext) {
		return this.extensionMappings.get(ext);
	}

	protected abstract String doGetExtension(HttpServletRequest request);

	private String getExtension(HttpServletRequest request) {
		String ext = null;
		try {
			ext = this.doGetExtension(request);
		} catch (Exception e) {
			logger.logMessage(LogLevel.WARN,
					"find fileExtension from the request fail!", e);
		}
		if (ext == null) {
			logger.logMessage(
					LogLevel.INFO,
					"can not find fileExtension from the request! the default extesion[{0}] will be used!",
					defaultContentExtension);
		}
		return ext == null ? getDefaultExtension() : ext;
	}

	protected String getDefaultExtension() {
		return defaultContentExtension;
	}

	public void addExtensionMapping(ExtensionMappingInstance mappingInstance) {
		Assert.notNull(
				mappingInstance,
				"[Assertion failed] - this ExtensionMappingInstance is required; it must not be null");
		Assert.notNull(
				mappingInstance.getExtension(),
				"[Assertion failed] - this ExtensionMappingInstance.extension is required; it must not be null");
		if (extensionMappings.containsKey(mappingInstance.getExtension())) {
			logger.logMessage(LogLevel.DEBUG,
					"extension mapping -{0} is already existed",
					mappingInstance.getExtension());
		} else {
			extensionMappings.put(mappingInstance.getExtension(),
					mappingInstance);
		}
	}

	/**
	 * 根据extension 取得 {@link ExtensionMappingInstance} 对象。
	 * 
	 * @param extension
	 * @return
	 */
	public ExtensionMappingInstance getExtesionMapping(String extension) {
		return extensionMappings.get(extension);
	}

	/**
	 * 判断是否支持某种Extension。
	 * 
	 * @param extension
	 * @return
	 */
	public boolean supportExtension(String extension) {
		return extensionMappings.containsKey(extension);
	}

}

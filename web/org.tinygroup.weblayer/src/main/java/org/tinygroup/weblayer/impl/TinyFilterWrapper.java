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
package org.tinygroup.weblayer.impl;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.*;
import org.tinygroup.weblayer.config.TinyFilterConfigInfo;
import org.tinygroup.weblayer.configmanager.TinyFilterConfigManager;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * tiny-filter的包装类
 * 
 * @author renhui
 * 
 */
public class TinyFilterWrapper implements FilterWrapper {

	private Map<String, Filter> filterMap = new TreeMap<String, Filter>();
	
	private Map<String, Filter> postFilterMap = new TreeMap<String, Filter>();

	private Map<String, String> bean2Config = new HashMap<String, String>();

	private TinyFilterConfigManager tinyFilterConfigManager;

	private TinyFilterManager tinyFilterManager;

	public TinyFilterWrapper(TinyFilterConfigManager tinyFilterConfigManager,
			TinyFilterManager tinyFilterManager) {
		super();
		this.tinyFilterConfigManager = tinyFilterConfigManager;
		this.tinyFilterManager = tinyFilterManager;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(TinyFilterWrapper.class);

	public void filterWrapper(WebContext context, TinyFilterHandler handler)
			throws IOException, ServletException {
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		String servletPath = handler.getServletPath();
		List<Filter> matchFilters = getMatchFilters(servletPath,filterMap);
		logger.logMessage(LogLevel.DEBUG,
				"the pre wrapper httpFilters for the request path:[{0}] is [{1}]",
				servletPath, matchFilters);
		List<Filter> postMatchFilters = getMatchFilters(servletPath,postFilterMap);
		logger.logMessage(LogLevel.DEBUG,
				"the post wrapper httpFilters for the request path:[{0}] is [{1}]",
				servletPath, postMatchFilters);
		TinyFilterChain filterChain = new TinyFilterChain(matchFilters, postMatchFilters,handler);
		filterChain.doFilter(request, response);
	}

	private List<Filter> getMatchFilters(String servletPath,Map<String, Filter> filterMap) {
		List<Filter> matchFilters = new ArrayList<Filter>();
		for (String filterBeanName : filterMap.keySet()) {
			TinyFilterConfig filterConfig = tinyFilterManager
					.getTinyFilterConfig(bean2Config.get(filterBeanName));
			if (filterConfig.isMatch(servletPath)) {
				matchFilters.add(filterMap.get(filterBeanName));
			}
		}
		return matchFilters;
	}
	
	

	public void addHttpFilter(String filterName, String filterBeanName,
			Filter filter) {
		filterMap.put(filterBeanName, filter);
		bean2Config.put(filterBeanName, filterName);
	}
	
	public void addPostHttpFilter(String filterName, String filterBeanName,
			Filter filter) {
		postFilterMap.put(filterBeanName, filter);
		bean2Config.put(filterBeanName, filterName);
	}

	public void init() throws ServletException {
		logger.logMessage(
				LogLevel.DEBUG,
				"TinyFilterWrapper start initialization wrapper httpfilter");
		initWrapperFilters(filterMap);
		initWrapperFilters(postFilterMap);
		logger.logMessage(LogLevel.DEBUG,
				"TinyFilterWrapper initialization end");
	}

	private void initWrapperFilters(Map<String, Filter> filterMap) throws ServletException {
		for (String filterBeanName : filterMap.keySet()) {
			Filter filter = filterMap.get(filterBeanName);
			TinyFilterConfigInfo filterConfigInfo = tinyFilterConfigManager
					.getFilterConfig(bean2Config.get(filterBeanName));
			filter.init(new TinyWrapperFilterConfig(filterConfigInfo));
		}
	}

	public void destroy() {
		filterMap = null;
	}

}

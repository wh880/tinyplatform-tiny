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
import org.tinygroup.weblayer.TinyFilterHandler;
import org.tinygroup.weblayer.WebContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器链接口的默认实现，不再往下传递调用,直接返回
 * 
 * @author renhui
 * 
 */
public class TinyFilterChain implements FilterChain {
	private List<Filter> preFilters = new ArrayList<Filter>();
	private List<Filter> postFilters = new ArrayList<Filter>();
	private int preCurrentPosition = 0;
	private int postCurrentPosition = 0;
	private static final Logger logger = LoggerFactory
			.getLogger(TinyFilterChain.class);

	private TinyFilterHandler tinyFilterHandler;

	public TinyFilterChain(List<Filter> preFilters,List<Filter> postFilters, TinyFilterHandler hander) {
		this.preFilters = preFilters;
		this.postFilters=postFilters;
		this.tinyFilterHandler = hander;
	}

	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		if (preCurrentPosition <preFilters.size()) {//前置包装filter处理
			Filter nextFilter = preFilters.get(preCurrentPosition);
			logger.logMessage(LogLevel.DEBUG, "firing pre Filter:'{}'", nextFilter
					.getClass().getSimpleName());
			preCurrentPosition++;
			nextFilter.doFilter(request, response, this);
		} else if(preCurrentPosition==preFilters.size()&&postCurrentPosition==0) {
			initWebContext(request, response);//重新初始化webcontext中保存的request和response对象
			tinyFilterHandler.tinyFilterProcessor((HttpServletRequest) request, (HttpServletResponse) response);
			doPostFilter(request, response);
		}else{
			doPostFilter(request, response);
		}

	}

	private void doPostFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		if(postCurrentPosition<postFilters.size()){//后置包装filter处理
			Filter nextFilter = preFilters.get(postCurrentPosition);
			logger.logMessage(LogLevel.DEBUG, "firing pre Filter:'{}'", nextFilter
					.getClass().getSimpleName());
			postCurrentPosition++;
			nextFilter.doFilter(request, response, this);
		}
	}

	private void initWebContext(ServletRequest request, ServletResponse response) {
		WebContext webContext= tinyFilterHandler.getContext();
		webContext.setRequest((HttpServletRequest)request);
		webContext.setResponse((HttpServletResponse)response);
	}

}

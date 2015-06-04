package org.tinygroup.springmvc.handleradapter;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

public class TinyHandlerAdapter implements HandlerAdapter {

	protected static final Logger logger = LoggerFactory
			.getLogger(TinyHandlerAdapter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerAdapter#getLastModified(javax.
	 * servlet.http.HttpServletRequest, java.lang.Object)
	 */
	public long getLastModified(HttpServletRequest request, Object handler) {
		Profiler.enter("[CarHandlerAdapter.getLastModified()]-Get Last Modified");
		try {
			HandlerAdapter handlerAdapter = getHandlerAdapter(request, handler);
			if (handlerAdapter != null) {
				logger.logMessage(LogLevel.DEBUG,
						" invoke  handerAdapter.getLastModified() method that will proxy ["
								+ handlerAdapter + "]");
				return handlerAdapter.getLastModified(request, handler);
			}
			return -1;
		} finally {
			Profiler.release();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerAdapter#handle(javax.servlet.http
	 * .HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	public ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Profiler.enter("[CarHandlerAdapter.handle()]-Handle it and get ModelAndView");
		try {
			HandlerAdapter handlerAdapter = getHandlerAdapter(request, handler);

			if (handlerAdapter != null) {
				logger.logMessage(LogLevel.DEBUG,
						" invoke car handerAdapter.handle() method that will proxy ["
								+ handlerAdapter + "]");
				return handlerAdapter.handle(request, response, handler);
			}
			// 应该不会发生
			return null;
		} finally {
			Profiler.release();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerAdapter#supports(java.lang.Object)
	 */
	public boolean supports(Object handler) {
		Profiler.enter("[CarHandlerAdapter.supports()]-Find out if the handlerAdapter supports the request");
		try {
			ServletWebRequest servletWebRequest = RequestInstanceHolder
					.getServletWebRequest();
			HandlerAdapter handlerAdapter = getHandlerAdapter(
					servletWebRequest.getRequest(), handler);

			if (handlerAdapter != null) {
				logger.logMessage(LogLevel.DEBUG,
						" invoke car handerAdapter.supports() method that will proxy ["
								+ handlerAdapter + "]");
				return handlerAdapter.supports(handler);
			}
			// 如果为null
			return false;
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
		return "TinyHandlerAdapter";
	}

	// ~~~ 内部方法

	protected HandlerAdapter getHandlerAdapter(HttpServletRequest request,
			Object handler) {
		List<HandlerAdapter> handlerAdapters = RequestInstanceHolder
				.getMappingInstance().getHandlerAdapters();

		// 已经改变了spring mvc 默认的映射机制，所以size==1的场景没有必要判断。
		if (handlerAdapters.size() == 1) {
			return handlerAdapters.iterator().next();
		}

		Iterator<HandlerAdapter> it = handlerAdapters.iterator();
		while (it.hasNext()) {
			HandlerAdapter ha = it.next();
			if (ha.supports(handler)) {
				return ha;
			}
		}
		return null;
	}

}

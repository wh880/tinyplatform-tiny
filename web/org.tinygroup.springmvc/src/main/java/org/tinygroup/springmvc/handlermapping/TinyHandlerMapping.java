package org.tinygroup.springmvc.handlermapping;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.springmvc.util.Profiler;

/**
 * 
 * @author renhui
 * 
 */
public class TinyHandlerMapping implements HandlerMapping {

	protected static final Log logger = LogFactory
			.getLog(TinyHandlerMapping.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerMapping#getHandler(javax.servlet
	 * .http.HttpServletRequest)
	 */
	public HandlerExecutionChain getHandler(HttpServletRequest request)
			throws Exception {
		Profiler.enter("[TinyHandlerMapping.getHandler()]-Get Handler Mapping");
		try {
			HandlerExecutionChain handler = null;
			List<HandlerMapping> handlerMappings = RequestInstanceHolder
					.getMappingInstance().getHandlerMappings();
			Iterator<HandlerMapping> it = handlerMappings.iterator();
			while (it.hasNext()) {
				HandlerMapping hm = (HandlerMapping) it.next();
				if (logger.isDebugEnabled()) {
					logger.debug("Testing handler map [" + hm
							+ "] in DispatcherServlet");
				}
				handler = hm.getHandler(request);
				// TODO 是否需要缓存handler对象
				if (handler != null) {
					return handler;
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
		return "TinyHandlerMapping";
	}

}

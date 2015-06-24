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

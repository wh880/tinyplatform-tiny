package org.tinygroup.bundle.exception;

import org.tinygroup.exception.TinySysRuntimeException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class BundleException extends TinySysRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9068351096762931390L;
	private Logger logger = LoggerFactory.getLogger(BundleException.class);

	public BundleException(String msg) {
		super(msg);
		logger.log(LogLevel.ERROR, msg);
	}

	public BundleException(String msg, Throwable throwable) {
		super(msg, throwable);
		logger.log(LogLevel.ERROR, msg);
	}

}

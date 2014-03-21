package org.tinygroup.bundle.exception;

import org.tinygroup.exception.TinySysRuntimeException;

public class NotComparableException extends TinySysRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5235907666929851514L;

	public NotComparableException(String msg) {
		super(msg);
		// logger.log(LogLevel.ERROR, msg);
	}

	public NotComparableException(String msg, Throwable throwable) {
		super(msg, throwable);
		// logger.log(LogLevel.ERROR, msg);
	}

}

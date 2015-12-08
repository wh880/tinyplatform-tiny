package org.tinygroup.aopcache.exception;

import java.util.Locale;

import org.tinygroup.context.Context;
import org.tinygroup.exception.BaseRuntimeException;

public class AopCacheException extends BaseRuntimeException {

	public AopCacheException() {
		super();
	}

	public AopCacheException(String errorCode, Context context, Locale locale) {
		super(errorCode, context, locale);
	}

	public AopCacheException(String errorCode, Context context) {
		super(errorCode, context);
	}

	public AopCacheException(String errorCode, Object... params) {
		super(errorCode, params);
	}

	public AopCacheException(String errorCode, String defaultErrorMsg,
			Context context, Locale locale) {
		super(errorCode, defaultErrorMsg, context, locale);
	}

	public AopCacheException(String errorCode, String defaultErrorMsg,
			Locale locale, Object... params) {
		super(errorCode, defaultErrorMsg, locale, params);
	}

	public AopCacheException(String errorCode, String defaultErrorMsg,
			Locale locale, Throwable throwable, Object... params) {
		super(errorCode, defaultErrorMsg, locale, throwable, params);
	}

	public AopCacheException(String errorCode, String defaultErrorMsg,
			Throwable throwable, Object... params) {
		super(errorCode, defaultErrorMsg, throwable, params);
	}

	public AopCacheException(String errorCode, Throwable throwable,
			Object... params) {
		super(errorCode, throwable, params);
	}

	public AopCacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public AopCacheException(String message) {
		super(message);
	}

	public AopCacheException(Throwable cause) {
		super(cause);
	}

}

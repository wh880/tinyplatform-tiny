package org.tinygroup.jdbctemplatedslsession.exception;

import java.util.Locale;

import org.tinygroup.context.Context;
import org.tinygroup.exception.BaseRuntimeException;

public class DslRuntimeException extends BaseRuntimeException {

	public DslRuntimeException() {
		super();
	}

	public DslRuntimeException(String errorCode, Context context, Locale locale) {
		super(errorCode, context, locale);
	}

	public DslRuntimeException(String errorCode, Context context) {
		super(errorCode, context);
	}

	public DslRuntimeException(String errorCode, Object... params) {
		super(errorCode, params);
	}

	public DslRuntimeException(String errorCode, String defaultErrorMsg,
			Context context, Locale locale) {
		super(errorCode, defaultErrorMsg, context, locale);
	}

	public DslRuntimeException(String errorCode, String defaultErrorMsg,
			Locale locale, Object... params) {
		super(errorCode, defaultErrorMsg, locale, params);
	}

	public DslRuntimeException(String errorCode, String defaultErrorMsg,
			Locale locale, Throwable throwable, Object... params) {
		super(errorCode, defaultErrorMsg, locale, throwable, params);
	}

	public DslRuntimeException(String errorCode, String defaultErrorMsg,
			Object... params) {
		super(errorCode, defaultErrorMsg, params);
	}

	public DslRuntimeException(String errorCode, String defaultErrorMsg,
			Throwable throwable, Object... params) {
		super(errorCode, defaultErrorMsg, throwable, params);
	}

	public DslRuntimeException(String errorCode, Throwable throwable,
			Object... params) {
		super(errorCode, throwable, params);
	}

	public DslRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DslRuntimeException(String message) {
		super(message);
	}

	public DslRuntimeException(Throwable cause) {
		super(cause);
	}

	
}

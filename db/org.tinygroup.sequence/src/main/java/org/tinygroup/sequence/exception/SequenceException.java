package org.tinygroup.sequence.exception;

import java.util.Locale;

import org.tinygroup.context.Context;
import org.tinygroup.exception.BaseRuntimeException;

public class SequenceException extends BaseRuntimeException {

	public SequenceException() {
		super();
	}

	public SequenceException(String errorCode, Context context, Locale locale) {
		super(errorCode, context, locale);
	}

	public SequenceException(String errorCode, Context context) {
		super(errorCode, context);
	}

	public SequenceException(String errorCode, Object... params) {
		super(errorCode, params);
	}

	public SequenceException(String errorCode, String defaultErrorMsg,
			Context context, Locale locale) {
		super(errorCode, defaultErrorMsg, context, locale);
	}

	public SequenceException(String errorCode, String defaultErrorMsg,
			Locale locale, Object... params) {
		super(errorCode, defaultErrorMsg, locale, params);
	}

	public SequenceException(String errorCode, String defaultErrorMsg,
			Locale locale, Throwable throwable, Object... params) {
		super(errorCode, defaultErrorMsg, locale, throwable, params);
	}

	public SequenceException(String errorCode, String defaultErrorMsg,
			Throwable throwable, Object... params) {
		super(errorCode, defaultErrorMsg, throwable, params);
	}

	public SequenceException(String errorCode, Throwable throwable,
			Object... params) {
		super(errorCode, throwable, params);
	}

	public SequenceException(Throwable cause) {
		super(cause);
	}

}

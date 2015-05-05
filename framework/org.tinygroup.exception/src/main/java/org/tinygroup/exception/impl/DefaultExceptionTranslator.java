package org.tinygroup.exception.impl;

import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.exception.ErrorCodeTranslator;
import org.tinygroup.exception.ErrorCodeTranslatorSelector;
import org.tinygroup.exception.ErrorContext;
import org.tinygroup.exception.ExceptionTranslator;

/**
 * 默认的异常翻译器
 * 
 * @author renhui
 * 
 */
public class DefaultExceptionTranslator implements ExceptionTranslator {

	private ErrorCodeTranslatorSelector selector = new SimpleErrorCodeTranslatorSelector();

	public String translateException(BaseRuntimeException exception) {
		ErrorContext errorContext = BaseRuntimeException
				.getErrorContext(exception);
		ErrorCodeTranslator translator = selector.select(errorContext);
		if (translator != null) {
			return translator.translate();
		}
		return null;
	}

	public ErrorCodeTranslatorSelector getSelector() {
		return selector;
	}

	public void setSelector(ErrorCodeTranslatorSelector selector) {
		this.selector = selector;
	}

}

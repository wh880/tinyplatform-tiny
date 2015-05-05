package org.tinygroup.exception.impl;

import org.tinygroup.exception.ErrorCodeTranslator;
import org.tinygroup.exception.ErrorCodeTranslatorSelector;
import org.tinygroup.exception.ErrorContext;

public class SimpleErrorCodeTranslatorSelector implements
		ErrorCodeTranslatorSelector {

	public ErrorCodeTranslator select(ErrorContext errorContext) {
		return new SimpleErrorCodeTranslator(errorContext.fetchCurrentError());
	}

}

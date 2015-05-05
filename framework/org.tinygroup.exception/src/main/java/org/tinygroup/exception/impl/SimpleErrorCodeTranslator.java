package org.tinygroup.exception.impl;

import org.tinygroup.exception.CommonError;
import org.tinygroup.exception.ErrorCodeTranslator;

public class SimpleErrorCodeTranslator implements ErrorCodeTranslator {
	
	private CommonError commonError;
	
	public SimpleErrorCodeTranslator(CommonError commonError) {
		super();
		this.commonError = commonError;
	}

	public String translate() {
		return commonError.getErrorMsg();
	}

	public String getErrorCode() {
		return commonError.getErrorCode().toString();
	}

}

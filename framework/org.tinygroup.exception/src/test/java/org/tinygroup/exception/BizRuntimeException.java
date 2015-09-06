package org.tinygroup.exception;

import org.tinygroup.commons.i18n.LocaleUtil;

public class BizRuntimeException extends BaseRuntimeException{
	public BizRuntimeException(String errorCode, Object... params) {
		super(errorCode, params);
	}
	
	public BizRuntimeException(String errorCode, String defaultErrorMsg,
			Object... params) {
		super(errorCode,defaultErrorMsg,params);
	}
}

package org.tinygroup.exception;


public class BizRuntimeException extends BaseRuntimeException{
	public BizRuntimeException(String errorCode, Object... params) {
		super(errorCode, params);
	}
	
}

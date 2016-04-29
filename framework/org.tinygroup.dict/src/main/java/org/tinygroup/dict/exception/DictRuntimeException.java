package org.tinygroup.dict.exception;

import org.tinygroup.exception.BaseRuntimeException;

public class DictRuntimeException extends BaseRuntimeException{

	private static final long serialVersionUID = 1L;

	public DictRuntimeException(String code, Object... args) {
		super(code,args);
	}
}

package org.tinygroup.cepcoreimpl.exception;

import org.tinygroup.cepcoreexceptioncode.CEPCoreImplExceptionCode;
import org.tinygroup.exception.BaseRuntimeException;

public class CEPConfigException extends BaseRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7863356712914424695L;

	public CEPConfigException(String message){
		super(CEPCoreImplExceptionCode.CEP_CONFIG_EXCEPTION_CODE, message);
	}
}

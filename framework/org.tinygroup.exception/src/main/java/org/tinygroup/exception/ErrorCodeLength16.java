package org.tinygroup.exception;

import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;

/**
 * 16位长度的错误码
 * 
 * @author renhui
 * 
 */
public class ErrorCodeLength16 extends AbstractErrorCode {

	private static final String VERSION = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2894313536708132240L;


	public ErrorCodeLength16(ErrorType errorType, ErrorLevel errorLevel,
							 String errorScene, int errorNumber, String errorPrefix) {
		super(VERSION, errorType, errorLevel, errorScene, errorNumber,
				errorPrefix);
	}


	@Override
	protected int[] getFieldLength() {
		return new int[0];
	}

	@Override
	protected String getErrorCodeFormatString() {
		return null;
	}

	@Override
	protected AbstractErrorCode getReserveErrorCode(ErrorType errorType) {
		return null;
	}
}

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

	// 这里是定义长度
	private static final int[] FIELD_LENGTH = { 2, 1, 1, 1, 8, 3 };

	private static final String VERSION = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2894313536708132240L;
	
	public ErrorCodeLength16() {
		super();
	}

	public ErrorCodeLength16(String errorCode) {
		super(errorCode);
	}

	public ErrorCodeLength16(ErrorType errorType, ErrorLevel errorLevel,
			int errorScene, int errorNumber, String errorPrefix) {
		super(VERSION, errorType, errorLevel, errorScene, errorNumber,
				errorPrefix);
	}

	@Override
	protected int[] getFieldLength() {
		return FIELD_LENGTH;
	}

	@Override
	protected String getErrorCodeFormatString() {
		return "%2s%1s%1d%1d%08d%03d";
	}

	@Override
	protected AbstractErrorCode getReserveErrorCode(ErrorType errorType) {
		if (errorType == null) {
			errorType = ErrorType.FRAMEWORK;
		}
		AbstractErrorCode errorCode = new ErrorCode(errorType,
				ErrorLevel.ERROR, 99999999, 999, errorPrefix);
		return errorCode;
	}
	
	protected   void internalParse(char[] chars) {
		this.errorScene = Integer.valueOf("" + chars[5] + chars[6] + chars[7]
				+ chars[8] + chars[9] + chars[10] + chars[11] + chars[12]);
		this.errorNumber =  Integer.valueOf("" + chars[13] + chars[14] + chars[15]);
	}
}

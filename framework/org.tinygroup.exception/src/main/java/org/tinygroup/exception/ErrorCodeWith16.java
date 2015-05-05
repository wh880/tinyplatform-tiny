package org.tinygroup.exception;

import org.tinygroup.exception.constant.ReservedErrorCodes;

/**
 * 16位长度的错误码
 * 
 * @author renhui
 * 
 */
public class ErrorCodeWith16 extends AbstractErrorCode {

	private static final String VERSION = "1";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2894313536708132240L;

	private ErrorCodeWith16(String errorCode) {
		super(errorCode);
	}

	public ErrorCodeWith16(String errorType, String errorLevel,
			String errorScene, String errorSpecific, String errorPrefix) {
		super(VERSION, errorType, errorLevel, errorScene, errorSpecific,
				errorPrefix);
	}

	@Override
	protected void checkScene(String errorScene) {
		checkLength(errorScene, 8);
	}

	@Override
	protected void checkSpecific(String errorSpecific) {
		checkLength(errorScene, 3);
	}

	@Override
	protected void buildErrorCode(String errorCode) {
		try {
			checkLength(errorCode, 16);
			splitErrorCode(errorCode);
		} catch (Throwable e) {
			splitErrorCode(ReservedErrorCodes.UNKNOWN_ERROR);
		}
	}

	private void splitErrorCode(String errorCode) {
		char[] chars = errorCode.toCharArray();
		this.errorPrefix = "" + chars[0] + chars[1];
		this.version = "" + chars[2];
		this.errorLevel = "" + chars[3];
		this.errorType = "" + chars[4];
		this.errorScene = "" + chars[5] + chars[6] + chars[7] + chars[8]
				+ chars[9] + chars[10] + chars[11] + chars[12];
		this.errorSpecific = "" + chars[13] + chars[14] + chars[15];
	}
}

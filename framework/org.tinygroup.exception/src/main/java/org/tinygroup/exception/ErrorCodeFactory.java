package org.tinygroup.exception;

import org.tinygroup.commons.tools.Assert;

/**
 * 创建errorcode的工厂类
 * 
 * @author renhui
 * 
 */
public class ErrorCodeFactory {

	private static Class type = ErrorCode.class;

	public static void setErrorCodeType(Class type) {
		ErrorCodeFactory.type = type;
	}

	public static AbstractErrorCode createErrorCode() {
		if (type.equals(ErrorCode.class)) {
			return new ErrorCode();
		} else if (type.equals(ErrorCodeLength16.class)) {
			return new ErrorCodeLength16();
		}
		return null;
	}

	public static AbstractErrorCode parseErrorCode(String errorCode) {
		Assert.assertNotNull(errorCode, "errorCode must not be null");
		if (type.equals(ErrorCode.class)) {
			return new ErrorCode(errorCode);
		} else if (type.equals(ErrorCodeLength16.class)) {
			return new ErrorCodeLength16(errorCode);
		}
		return null;
	}

}

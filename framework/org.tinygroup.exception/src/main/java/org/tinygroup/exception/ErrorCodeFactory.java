package org.tinygroup.exception;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.Assert;

/**
 * 创建errorcode的工厂类
 * 
 * @author renhui
 * 
 */
public class ErrorCodeFactory {

	private static List<AbstractErrorCode> errorCodes = new ArrayList<AbstractErrorCode>();

	static {
		errorCodes.add(new ErrorCode());
		errorCodes.add(new ErrorCodeLength16());
	}

	public static void addErrorCodes(AbstractErrorCode errorCode) {
		errorCodes.add(errorCode);
	}

	public static AbstractErrorCode parseErrorCode(String errorCode) {
		Assert.assertNotNull(errorCode, "errorCode must not be null");
		ErrorCodeParse creator = findCreator(errorCode);
		if (creator != null) {
			return creator.parse(errorCode);
		}
		throw new IllegalArgumentException(String.format("未找到错误码:%s,对应的错误码对象",errorCode));
	}

	private static ErrorCodeParse findCreator(String errorCodeStr) {
		for (AbstractErrorCode errorCode : errorCodes) {
			if (errorCode.isLengthMatch(errorCodeStr.length())) {
				return errorCode;
			}
		}
		return null;
	}
}

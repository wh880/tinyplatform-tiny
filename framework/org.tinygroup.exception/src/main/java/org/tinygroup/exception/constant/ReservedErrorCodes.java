package org.tinygroup.exception.constant;

import org.tinygroup.exception.ErrorCodeFactory;

/**
 * 保留错误码,提供全局的定义。
 * 
 */
public interface ReservedErrorCodes {
	
	/** 未知系统异常 */
	String UNKNOWN_ERROR = ErrorCodeFactory.createErrorCode()
			.getUnknownErrorCode(ErrorType.FRAMEWORK);

	/** 未知系统异常 */
	String UNKNOWN_SYSTEM_ERROR = ErrorCodeFactory.createErrorCode()
			.getUnknownErrorCode(ErrorType.FRAMEWORK);

	/** 未知扩展系统异常 */
	String UNKNOWN_EXT_ERROR =  ErrorCodeFactory.createErrorCode()
	.getUnknownErrorCode(ErrorType.EXT);

	/** 未知业务异常 */
	String UNKNOWN_BIZ_ERROR =  ErrorCodeFactory.createErrorCode()
	.getUnknownErrorCode(ErrorType.BIZ);

	/** 未知第三方异常 */
	String UNKNOWN_THIRD_PARTY_ERROR =  ErrorCodeFactory.createErrorCode()
	.getUnknownErrorCode(ErrorType.THIRD_PARTY);

	/** */
	String UNKNOW_ERROR_V1 = "AE13399999999999";
}

package org.tinygroup.springmvc.exceptionresolver;

public interface ExceptionConstants {

	String MESSAGE_CODE_NAME = "messageCode";
	String EXCEPTION_CODE_NAME = "exceptionCode";
	String UNCAUGHT_EXCEPTION_CODE = "common.uncaughtException";

	/**
	 * ==Json
	 */

	/** 返回状态的key */
	String KEY_RETURN_STAT = "stat";

	/** 返回状态的value - 失败 */
	String VALUE_RETURN_STAT_FAIL = "fail";

	/** 返回信息的key */
	String KEY_RETURN_MSG = "msg";

	/**
	 * ==tile
	 */
	String EXCEPTION_MARKING = "exception_marking";

}

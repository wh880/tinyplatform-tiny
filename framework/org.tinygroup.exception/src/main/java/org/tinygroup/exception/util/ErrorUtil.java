/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.exception.util;

import org.tinygroup.exception.Error;
import org.tinygroup.exception.ErrorCode;
import org.tinygroup.exception.ErrorContext;
import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;
import org.tinygroup.exception.errorcode.AbstractErrorCode;
import org.tinygroup.exception.errorcode.ErrorCodeDefault;

/**
 * 标准错误码工具类。
 * 
 * <p>
 * 使用标准错误码时，必须配置ErrorUtil的bean。否则获取不到错误位置信息。
 * 
 */
public class ErrorUtil {

	/** 系统名称 */
	private static String appName;

	// ~~~ 构造方法

	private ErrorUtil() {
	}

	/**
	 * V1版本错误码构造函数。
	 * 
	 * @param errorType
	 *            错误类型。（1：框架级系统错误 、2:框架业务扩展错误,3：业务错误、4：第三方错误） 
	 * @param
	 *            errorLevel 错误级别。（1：错误、2：严重错误）
	 * @param errorScene
	 *            错误场景。（业务事件码）
	 * @param errorSpecific
	 *            错误编码。（错误明细编码，错误场景内唯一）
	 * @param errorPrefix
	 *            错误码前缀。（按厂商分配）
	 * @return 错误码实例。
	 */
	public static ErrorCodeDefault makeErrorCode(String errorType, String errorLevel,
			int errorScene, int errorSpecific, String errorPrefix) {
		return new ErrorCodeDefault(ErrorType.find(errorType), ErrorLevel.find(errorLevel), errorScene, errorSpecific,
				errorPrefix);
	}

	/**
	 * 创建一个CommonError
	 * 
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static Error makeError(ErrorCode errorCode, String message) {

		Error error = new Error();
		error.setErrorCode(errorCode);
		error.setErrorMsg(message);
		error.setErrorSource(getAppName());
		return error;
	}

	/**
	 * 创建一个CommonError
	 * 
	 * @param errorCode
	 * @param message
	 * @param location
	 * @return
	 */
	public static Error makeError(ErrorCode errorCode, String message,
			String location) {

		Error error = new Error();
		error.setErrorCode(errorCode);
		error.setErrorMsg(message);
		error.setErrorSource(location);
		return error;
	}

	/**
	 * 增加一个error到errorContext中
	 * 
	 * @param error
	 * @return
	 */
	public static ErrorContext addError(Error error) {
		return addError(null, error);
	}

	/**
	 * 增加一个error到errorContext中
	 * 
	 * @param context
	 * @param error
	 * @return
	 */
	public static ErrorContext addError(ErrorContext context, Error error) {

		if (context == null) {
			context = new ErrorContext();
		}

		if (error == null) {
			return context;
		}

		context.addError(error);

		return context;
	}

	/**
	 * 创建并且增加一个Error到errorContext中
	 * 
	 * @param context
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static ErrorContext makeAndAddError(ErrorContext context,
											   ErrorCode errorCode, String message) {

		Error error = makeError(errorCode, message);
		return addError(context, error);
	}

	/**
	 * 创建并且增加一个Error到新的errorContext中
	 * 
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static ErrorContext makeAndAddError(AbstractErrorCode errorCode,
			String message) {

		Error error = makeError(errorCode, message);
		return  addError(error);
	}

	/**
	 * 获取系统名称
	 * 
	 * @return
	 */
	public static String getAppName() {

		if (null == appName || "".equals(appName)) {
			return "unknown";
		}

		return appName;
	}

	/**
	 * Setter method for property <tt>appName</tt>.
	 * 
	 * @param appName
	 *            value to be assigned to property appName
	 */
	public static void setAppName(String appName) {
		ErrorUtil.appName = appName;
	}
}

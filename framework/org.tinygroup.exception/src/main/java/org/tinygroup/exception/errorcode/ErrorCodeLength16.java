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
package org.tinygroup.exception.errorcode;

import org.tinygroup.exception.constant.ErrorLevel;
import org.tinygroup.exception.constant.ErrorType;

/**
 * 16位长度的错误码
 * 
 * @author renhui
 * 
 */
public class ErrorCodeLength16 extends AbstractErrorCode {
	/** 未知系统异常 */
	public static final String UNKNOWN_ERROR = "1TE1299999999999";

	/** 未知系统异常 */
	public static final String UNKNOWN_SYSTEM_ERROR = "1TE1299999999999";

	/** 未知扩展系统异常 */
	public static final String UNKNOWN_EXT_ERROR = "1TE2299999999999";
	/** 未知业务异常 */
	public static final String UNKNOWN_BIZ_ERROR = "1TE3299999999999";

	/** 未知第三方异常 */
	public static final String UNKNOWN_THIRD_PARTY_ERROR = "1TE4299999999999";

	// 这里是定义长度
	private static final int[] FIELD_LENGTH = { 1, 2, 1, 1, 8, 3 };

	private static final String VERSION = "1";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2894313536708132240L;

	public ErrorCodeLength16() {
		super();
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
		return "%1s%2s%1d%1d%08d%03d";
	}


	public boolean isMatch(String errorCodeStr) {
		return errorCodeStr.startsWith(VERSION);
	}

}

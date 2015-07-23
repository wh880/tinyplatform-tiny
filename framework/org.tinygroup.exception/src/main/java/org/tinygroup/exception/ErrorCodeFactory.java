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
package org.tinygroup.exception;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.exception.errorcode.ErrorCodeDefault;
import org.tinygroup.exception.errorcode.ErrorCodeLength16;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建errorcode的工厂类
 * 
 * @author renhui
 * 
 */
public class ErrorCodeFactory {

	private static List<ErrorCodeParser> codeParsers = new ArrayList<ErrorCodeParser>();

	static {
		codeParsers.add(new ErrorCodeDefault());
		codeParsers.add(new ErrorCodeLength16());
	}

	private ErrorCodeFactory() {
	}

	public static void addCodeParser(ErrorCodeParser errorCodeParser) {
		codeParsers.add(errorCodeParser);
	}

	public static ErrorCode parseErrorCode(String errorCode, Throwable cause) {
		Assert.assertNotNull(errorCode, "errorCode must not be null");
		ErrorCodeParser parser = findParser(errorCode, cause);
		return parser.parse(errorCode);
	}

	private static ErrorCodeParser findParser(String errorCodeStr,
			Throwable cause) {
		for (ErrorCodeParser errorCodeParser : codeParsers) {
			if (errorCodeParser.isMatch(errorCodeStr)) {
				return errorCodeParser;
			}
		}
		throw new IllegalArgumentException(String.format(
				"未找到错误码:%s,对应的错误码解析规范", errorCodeStr), cause);
	}
}

/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.imda.validate.impl;

import org.tinygroup.context.Context;
import org.tinygroup.imda.validate.ValidateExecutor;
import org.tinygroup.imda.validate.ValidateRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellPhoneNumberValidator implements ValidateExecutor {
	static Pattern emailPattern = Pattern
			.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

	public String getRuleName() {
		return "mobile";
	}

	public boolean isValidate(ValidateRule validateRule, String value, Context context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		Matcher matcher = emailPattern.matcher(value);
		return matcher.matches();
	}

	public String getDefaultMessage() {
		return "请输入正确格式的手机号码";
	}
}

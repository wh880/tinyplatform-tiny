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

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.imda.validate.ValidateExecutor;
import org.tinygroup.imda.validate.ValidateRule;

public class AcceptValidator implements ValidateExecutor {

	public String getRuleName() {
		return "accept";
	}

	public boolean isValidate(ValidateRule validateRule, String value,
			Context context) {
	    String ruleValue=validateRule.getRuleValue();
	    if(!StringUtil.isBlank(ruleValue)){
	    	  String[] accepts=ruleValue.split("|");
	    	  for (String accept : accepts) {
				if(value.endsWith(accept)){
					return true;
				}
			  }
	    }
		return false;
	}

	public String getDefaultMessage() {
		return "请输入拥有合法后缀名的字符串";
	}
}

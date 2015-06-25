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
package org.tinygroup.weblayer.webcontext.parser.impl;

import org.tinygroup.weblayer.webcontext.parser.upload.ParamValueFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * 
 * 功能说明: 

 * 开发人员: renhui <br>
 * 开发时间: 2013-10-14 <br>
 * <br>
 */
public abstract class AbstractParamValueFilter implements ParamValueFilter {
	
	private String pattern;
	
	private Pattern regular;
	

	public void setPattern(String patternStr) {
		pattern=patternStr;
		regular=Pattern.compile(pattern);
	}

	public boolean isFiltering(HttpServletRequest request) {
		return true;
	}


	public boolean isFilter(String keyName) {
		if(regular!=null){
			return regular.matcher(keyName).matches();
		}
		return false;
	}

}

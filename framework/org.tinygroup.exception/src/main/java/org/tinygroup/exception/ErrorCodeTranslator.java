/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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

/**
 * 错误号翻译接口
 * @author renhui
 *
 */

public interface ErrorCodeTranslator {
    /**
     * 异常号翻译方法
     * @return
     */
	String translate(Error error);
	
	/**
	 * 显示要进行翻译的异常号
	 * @return
	 */
	String getErrorCode(Error error);
	
}

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
package org.tinygroup.tinysqldsl;

import org.tinygroup.tinysqldsl.base.InsertContext;

/**
 * 主键生成接口
 * @author renhui
 *
 */
public interface KeyGenerator {

	/**
	 * 根据插入上下文生成主键值
	 * @param <T>
	 * @param insertContext
	 * @return
	 */
	<T> T generate(InsertContext insertContext);
}

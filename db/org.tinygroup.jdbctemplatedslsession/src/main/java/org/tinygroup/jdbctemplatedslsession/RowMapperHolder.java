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
package org.tinygroup.jdbctemplatedslsession;

import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper实例持有接口
 * @author renhui
 *
 */
public interface RowMapperHolder {
	/**
	 * 根据类型进行匹配
	 * @param requiredType
	 * @return
	 */
	public boolean isMatch(Class requiredType);
	
	/**
	 * 返回该选择器对应的RowMapper
	 * @param requiredType
	 * @return
	 */
	public RowMapper getRowMapper(Class requiredType);

}

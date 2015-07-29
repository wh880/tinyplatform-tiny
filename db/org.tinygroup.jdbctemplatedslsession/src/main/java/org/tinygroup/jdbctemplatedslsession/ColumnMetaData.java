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


/**
 * 表格元数据对象
 * @author renhui
 *
 */
public class ColumnMetaData {

	private final String parameterName;

	private final int sqlType;

	private final boolean nullable;
	
	private final Object defaultValue;

	public ColumnMetaData(String parameterName, int sqlType, boolean nullable,
			Object defaultValue) {
		super();
		this.parameterName = parameterName;
		this.sqlType = sqlType;
		this.nullable = nullable;
		this.defaultValue = defaultValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public int getSqlType() {
		return sqlType;
	}

	public boolean isNullable() {
		return nullable;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}
}

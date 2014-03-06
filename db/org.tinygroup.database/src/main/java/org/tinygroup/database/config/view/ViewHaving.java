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
package org.tinygroup.database.config.view;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("view-having")
public class ViewHaving {
	
	@XStreamAsAttribute
	private String operator;
	@XStreamAsAttribute
	private String value;
	
	@XStreamAsAttribute
	@XStreamAlias("key-having")
	private Having keyHaving;
	
	@XStreamAsAttribute
	@XStreamAlias("value-having")
	private Having valueHaving;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Having getKeyHaving() {
		return keyHaving;
	}

	public void setKeyHaving(Having keyHaving) {
		this.keyHaving = keyHaving;
	}

	public Having getValueHaving() {
		return valueHaving;
	}

	public void setValueHaving(Having valueHaving) {
		this.valueHaving = valueHaving;
	}
	
	
}

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
package org.tinygroup.event;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("parameter")
public class Parameter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 610138334611099410L;
	public static final String INPUT = "in";// 输入参数
	public static final String OUTPUT = "out";// 输出参数
	public static final String BOTH = "both";// 输入输出参数
	@XStreamAsAttribute
	private String name;// 参数名称
	@XStreamAsAttribute
	private String title;// 参数本地名称,i18n键值,如果i18n找不到，则原样显示
	@XStreamAsAttribute
	private String type;// 参数类型
	@XStreamAlias("collection-type")
	@XStreamAsAttribute
	private String collectionType;// 参数类型
	@XStreamAsAttribute
	private String scope;// 参数域，默认为both

	@XStreamAsAttribute
	private boolean array;// 是否是数组，默认是false
	@XStreamAsAttribute
	private boolean required;// 是否是必须，默认是true
	private String description;// 描述
	@XStreamAsAttribute
	@XStreamAlias("validate-scene")//场景
	private String validatorScene;

	public String getValidatorSence() {
		return validatorScene;
	}

	public void setValidatorSence(String validatorSence) {
		this.validatorScene = validatorSence;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

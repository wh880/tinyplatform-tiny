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
package org.tinygroup.mongodb.common;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 字段分组
 * 
 * @author luoguo
 * 
 */
@XStreamAlias("group")
public class Group extends BaseObject {
    @XStreamImplicit
	List<ObjectField> objectFields;// 子集
	@XStreamImplicit
	List<Field> fields;// 字段
	
	public List<ObjectField> getObjectFields() {
		return objectFields;
	}

	public void setObjectFields(List<ObjectField> objectFields) {
		this.objectFields = objectFields;
	}

	public List<Field> getFields() {
		if (fields == null)
			fields = new ArrayList<Field>();
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
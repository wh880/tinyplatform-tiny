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
package org.tinygroup.database.config.view;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.database.config.SqlBody;
import org.tinygroup.metadata.config.BaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjiao
 * 
 */
@XStreamAlias("view")
public class View extends BaseObject {
	@XStreamAsAttribute
	private String schema;
	@XStreamAlias("sqls")
	private List<SqlBody> viewSqlBodyList;

	public String getName() {
		if (getSchema() == null || "".equals(getSchema())) {
			return super.getName();
		}
		return String.format("%s.%s", getSchema(), super.getName());
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@XStreamAlias("group-by-fields")
	private List<GroupByField> groupByFieldList;

	public List<GroupByField> getGroupByFieldList() {
		if (groupByFieldList == null) {
			groupByFieldList = new ArrayList<GroupByField>();
		}
		return groupByFieldList;
	}

	public void setGroupByFieldList(List<GroupByField> groupByFieldList) {
		this.groupByFieldList = groupByFieldList;
	}

	public List<SqlBody> getViewSqlBodyList() {
		return viewSqlBodyList;
	}

	public void setViewSqlBodyList(List<SqlBody> viewSqlBodyList) {
		this.viewSqlBodyList = viewSqlBodyList;
	}
}

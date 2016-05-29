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
	private List<SqlBody> sqlBodyList;
	@XStreamAlias("ref-view-ids")
	private RefViewIds refViewIds;//关联视图

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


	public void setSqlBodyList(List<SqlBody> sqlBodyList) {
		this.sqlBodyList = sqlBodyList;
	}

	public List<SqlBody> getSqlBodyList() {
		return sqlBodyList;
	}

	public void setRefViewIds(RefViewIds refViewIds) {
		this.refViewIds = refViewIds;
	}

	public RefViewIds getRefViewIds() {
		return refViewIds;
	}
}

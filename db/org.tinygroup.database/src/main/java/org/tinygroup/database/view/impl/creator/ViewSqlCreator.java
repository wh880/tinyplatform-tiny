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
package org.tinygroup.database.view.impl.creator;

import org.tinygroup.database.config.SqlBody;
import org.tinygroup.database.config.view.*;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import java.util.List;

/**
 * 生成view 创建、删除语句
 * 
 * @author renhui
 * 
 */
public class ViewSqlCreator {

	protected View view;

	protected Logger logger = LoggerFactory.getLogger(ViewSqlCreator.class);

	public ViewSqlCreator(View view) {
		this.view = view;
	}

	public String getCreateSql() {
		StringBuffer buffer = new StringBuffer();
		List<SqlBody> sqlBodyList = view.getSqlBodyList();
		for(SqlBody sqlBody : sqlBodyList){
			if(sqlBody.getDialectTypeName().equalsIgnoreCase(getLanguage())) {
				buffer.append(sqlBody.getContent()).append(";");
			}
		}

		logger.logMessage(LogLevel.DEBUG, "新建视图sql:{1}", buffer.toString());

		return buffer.toString();

	}

	public String getDropSql() {
		return String.format("DROP VIEW %s", view.getName());
	}

	protected String getLanguage(){
		return null;
	}
}

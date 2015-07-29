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
package org.tinygroup.tinydb.sql.impl;

import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.Configuration;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.sql.SqlAndValues;

import java.util.ArrayList;
import java.util.List;

/**
 * 把bean对象转换成对应的sql语句
 * 
 * @author renhui
 * 
 */
public class DefaultStatementTransform extends StatementTransformAdapter {

	public DefaultStatementTransform() {
		super();
	}

	public DefaultStatementTransform(Configuration configuration) {
		super(configuration);
	}

	public SqlAndValues toSelect(Bean bean) throws TinyDbException {
		TableConfiguration table = configuration.getTableConfiguration(
				bean.getType(), schema);
		if (table != null) {
			StringBuffer sb = new StringBuffer(" select * from ");
			sb.append(getFullTableName(bean.getType()));
			List<String> conditionColumns = getColumnNames(bean);
			String condition = getConditionSql(conditionColumns, bean);
			if (condition != null && condition.length() > 0) {
				sb.append(" where ").append(condition);
			}
			List<Object> params=getConditionParams(bean);
			return new SqlAndValues(sb.toString(), params);
		}
		throw new TinyDbException("不存在beanType：" + bean.getType() + "的表格");
	}

	public 	String toInsert(Bean bean) throws TinyDbException {
          return getInsertSql(bean);		
	}

	public String toDelete(Bean bean) throws TinyDbException {
		List<String> conditionColumns = getColumnNames(bean);
		return getDeleteSql(bean, conditionColumns);
	}

	public String toUpdate(Bean bean) throws TinyDbException {
		TableConfiguration table = configuration.getTableConfiguration(
				bean.getType(), schema);
		List<String> conditionColumns = new ArrayList<String>();
		conditionColumns.add(table.getPrimaryKey().getColumnName());
		return getUpdateSql(bean, conditionColumns);
	}

}

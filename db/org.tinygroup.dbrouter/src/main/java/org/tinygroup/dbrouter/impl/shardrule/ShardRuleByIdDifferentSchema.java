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
package org.tinygroup.dbrouter.impl.shardrule;

import java.util.List;

import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;
import org.tinygroup.jsqlparser.expression.DoubleValue;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.JdbcParameter;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.StringValue;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsList;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.statement.insert.Insert;

/**
 * 通过ID进行分片 Created by luoguo on 13-12-15.
 */
public class ShardRuleByIdDifferentSchema extends ShardRuleByIdAbstract {

	public ShardRuleByIdDifferentSchema() {

	}

	public ShardRuleByIdDifferentSchema(String tableName,
			String primaryKeyFieldName, int remainder) {
		super(tableName, primaryKeyFieldName, remainder);
	}

	public boolean isMatch(Partition partition, String sql,
			Object... preparedParams) {
		Statement statement = RouterManagerBeanFactory.getManager()
				.getSqlStatement(sql);
		if (statement instanceof Insert) {
			Insert insert = (Insert) statement;
			if (tableName.equals(insert.getTable().getName())) {
				ItemsList itemsList= insert.getItemsList();
				if(itemsList instanceof ExpressionList){
					List<Expression> expressions = ((ExpressionList)itemsList).getExpressions();
					int paramIndex=0;
					for (int i = 0; i < insert.getColumns().size(); i++) {
						Column column = insert.getColumns().get(i);
						Expression expression=expressions.get(i);
						if (column.getColumnName().equals(primaryKeyFieldName)) {
							  if(expression instanceof LongValue){
								  LongValue longValue=(LongValue)expression;
								  if(longValue.getValue()% partition.getShards()
											.size() == remainder){
									  return true;
								  }
							  }else if(expression instanceof JdbcParameter){
								   Long value = (Long) preparedParams[paramIndex];
									if ((value % partition.getShards().size()) == remainder) {
										return true;
									}
								  paramIndex++;
							  }
						}
					}
				}
			}
		}
		return false;
	}

	public String getReplacedSql(String sql) {
		return sql;
	}
}

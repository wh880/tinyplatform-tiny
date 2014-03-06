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

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;
import org.tinygroup.dbrouter.util.DbRouterUtil;
import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.operators.conditional.AndExpression;
import org.tinygroup.jsqlparser.expression.operators.conditional.OrExpression;
import org.tinygroup.jsqlparser.expression.operators.relational.EqualsTo;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.select.FromItem;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectBody;
import org.tinygroup.jsqlparser.statement.update.Update;

/**
 * Created by luoguo on 13-12-15.
 */
public class ShardRuleByIdSameSchema extends ShardRuleByIdAbstract {

	public ShardRuleByIdSameSchema() {

	}

	public ShardRuleByIdSameSchema(String tableName,
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
				for (int i = 0; i < insert.getColumns().size(); i++) {
					Column column = insert.getColumns().get(i);
					if (column.getColumnName().equals(primaryKeyFieldName)) {
						Expression expression=((ExpressionList) insert
								.getItemsList()).getExpressions().get(i);
						if(expression instanceof LongValue){
							LongValue value = (LongValue)expression;
							if ((value.getValue() % partition.getShards().size()) == remainder) {
								return true;
							}
						}
					}
				}
			}
		}
		if (statement instanceof Delete) {
			Delete delete = (Delete) statement;
			if (tableName.equals(delete.getTable().getName())) {
				return getWhereExpression(delete.getWhere(), partition);
			}
		}
		if (statement instanceof Update) {
			Update update = (Update) statement;
			if (tableName.equals(update.getTable().getName())) {
				return getWhereExpression(update.getWhere(), partition);
			}

		}
		if (statement instanceof Select) {
			Select select = (Select) statement;
			SelectBody body = select.getSelectBody();
			if (body instanceof PlainSelect) {
				PlainSelect plainSelect = (PlainSelect) body;
				FromItem fromItem = plainSelect.getFromItem();
				if (fromItem instanceof Table) {
					Table table = (Table) fromItem;
					if (tableName.equals(table.getName())) {
						return getWhereExpression(plainSelect.getWhere(),
								partition);
					}
				}
			}

		}

		return false;
	}

	private boolean getWhereExpression(Expression where, Partition partition) {
		if (where == null) {
			return true;
		}
		boolean equalsTo = getEqualsToExpression(where, partition);
		if (where instanceof AndExpression) {
			AndExpression andExpression = (AndExpression) where;
			Expression leftExpression = andExpression.getLeftExpression();
			if (getWhereExpression(leftExpression, partition)) {
				return true;
			}
			Expression rightExpression = andExpression.getRightExpression();
			if (getWhereExpression(rightExpression, partition)) {
				return true;
			}
		}
		if (where instanceof OrExpression) {
			OrExpression orExpression = (OrExpression) where;
			Expression leftExpression = orExpression.getLeftExpression();
			if (getWhereExpression(leftExpression, partition)) {
				return true;
			}
			Expression rightExpression = orExpression.getRightExpression();
			if (getWhereExpression(rightExpression, partition)) {
				return true;
			}
		}
		return equalsTo;

	}

	private boolean getEqualsToExpression(Expression where, Partition partition) {
		if (where instanceof EqualsTo) {
			EqualsTo equalsTo = (EqualsTo) where;
			Expression leftExpression = equalsTo.getLeftExpression();
			Expression rightExpression = equalsTo.getRightExpression();
			if (leftExpression instanceof Column) {
				Column column = (Column) leftExpression;
				if (column.getColumnName().equals(primaryKeyFieldName)) {
					if (rightExpression instanceof LongValue) {
						LongValue value = (LongValue) rightExpression;
						if ((value.getValue() % partition.getShards().size()) == remainder) {
							return true;
						}
					}
				}
			}

		}
		return false;
	}

	public String getReplacedSql(String sql) {
		Map<String, String> tableMapping = new HashMap<String, String>();
		tableMapping.put(tableName, tableName + remainder);
		return DbRouterUtil.transformSqlWithTableName(sql, tableMapping);
	}
}

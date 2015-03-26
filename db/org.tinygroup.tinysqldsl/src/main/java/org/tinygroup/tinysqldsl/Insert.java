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
package org.tinygroup.tinysqldsl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.base.Condition;
import org.tinygroup.tinysqldsl.base.Statement;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.base.Value;
import org.tinygroup.tinysqldsl.expression.JdbcParameter;
import org.tinygroup.tinysqldsl.expression.relational.ExpressionList;
import org.tinygroup.tinysqldsl.insert.InsertBody;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Insert extends StatementSqlBuilder implements Statement {

	private InsertBody insertBody;

	private Insert() {
		insertBody = new InsertBody();
	}

	public static Insert insertInto(Table table) {
		Insert insert = new Insert();
		insert.getInsertBody().setTable(table);
		return insert;
	}

	public Insert values(Value... values) {
		List<Column> columns = new ArrayList<Column>();
		ExpressionList itemsList = new ExpressionList();
		for (Value value : values) {
			columns.add(value.getColumn());
			itemsList.addExpression(new Condition(new JdbcParameter(), value
					.getValue()));
		}
		insertBody.setColumns(columns);
		insertBody.setItemsList(itemsList);
		return this;
	}

	public InsertBody getInsertBody() {
		return insertBody;
	}

	@Override
	public String toString() {
		return sql();
	}

	@Override
	protected void parserStatementBody() {
		build(insertBody);
	}

}

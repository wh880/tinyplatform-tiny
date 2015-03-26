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
package org.tinygroup.tinydbdsl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.base.Value;
import org.tinygroup.tinydbdsl.expression.JdbcParameter;
import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;
import org.tinygroup.tinydbdsl.insert.InsertBody;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Insert extends StatementParser implements Statement {

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
		parser(insertBody);
	}

}

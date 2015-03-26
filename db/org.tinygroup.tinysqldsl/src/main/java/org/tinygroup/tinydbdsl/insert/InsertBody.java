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
package org.tinygroup.tinydbdsl.insert;

import java.util.List;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.SelectBody;
import org.tinygroup.tinydbdsl.base.StatementBody;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.expression.relational.ItemsList;
import org.tinygroup.tinydbdsl.selectitem.SelectExpressionItem;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;

/**
 * The insert statement. Every column name in <code>columnNames</code> matches
 * an item in <code>itemsList</code>
 */
public class InsertBody implements StatementBody {

	private Table table;
	private List<Column> columns;
	private ItemsList itemsList;
	private boolean useValues = true;
	private SelectBody selectBody;
	private boolean useSelectBrackets = false;

	private boolean returningAllColumns = false;

	private List<SelectExpressionItem> returningExpressionList = null;

	public Table getTable() {
		return table;
	}

	public void setTable(Table name) {
		table = name;
	}

	/**
	 * Get the columns (found in "INSERT INTO (col1,col2..) [...]" )
	 * 
	 * @return a list of {@link org.tinygroup.jsqlparser.schema.Column}
	 */
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> list) {
		columns = list;
	}

	/**
	 * Get the values (as VALUES (...) or SELECT)
	 * 
	 * @return the values of the insert
	 */
	public ItemsList getItemsList() {
		return itemsList;
	}

	public void setItemsList(ItemsList list) {
		itemsList = list;
	}

	public boolean isUseValues() {
		return useValues;
	}

	public void setUseValues(boolean useValues) {
		this.useValues = useValues;
	}

	public boolean isReturningAllColumns() {
		return returningAllColumns;
	}

	public void setReturningAllColumns(boolean returningAllColumns) {
		this.returningAllColumns = returningAllColumns;
	}

	public List<SelectExpressionItem> getReturningExpressionList() {
		return returningExpressionList;
	}

	public void setReturningExpressionList(
			List<SelectExpressionItem> returningExpressionList) {
		this.returningExpressionList = returningExpressionList;
	}

	public SelectBody getSelectBody() {
		return selectBody;
	}

	public void setSelectBody(SelectBody selectBody) {
		this.selectBody = selectBody;
	}

	public boolean isUseSelectBrackets() {
		return useSelectBrackets;
	}

	public void setUseSelectBrackets(boolean useSelectBrackets) {
		this.useSelectBrackets = useSelectBrackets;
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO ");
		sql.append(table).append(" ");
		if (columns != null) {
			sql.append(DslUtil.getStringList(columns, true, true)).append(" ");
		}

		if (useValues) {
			sql.append("VALUES ");
		}

		if (itemsList != null) {
			sql.append(itemsList);
		}

		if (useSelectBrackets) {
			sql.append("(");
		}
		if (selectBody != null) {
			sql.append(selectBody);
		}
		if (useSelectBrackets) {
			sql.append(")");
		}

		if (isReturningAllColumns()) {
			sql.append(" RETURNING *");
		} else if (getReturningExpressionList() != null) {
			sql.append(" RETURNING ").append(
					DslUtil.getStringList(getReturningExpressionList(), true,
							false));
		}

		return sql.toString();
	}

	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}

}

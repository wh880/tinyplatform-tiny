/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.jsqlparser.statement.create.index;

import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.statement.StatementVisitor;
import org.tinygroup.jsqlparser.statement.create.table.Index;

import java.util.Iterator;

/**
 * A "CREATE INDEX" statement
 *
 * @author Raymond Augé
 */
public class CreateIndex implements Statement {

	private Table table;
	private Index index;


	public void accept(StatementVisitor statementVisitor) {
		statementVisitor.visit(this);
	}

	/**
	 * The index to be created
	 */
	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	/**
	 * The table on which the index is to be created
	 */
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}


	public String toString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append("CREATE ");

		if (index.getType() != null) {
			buffer.append(index.getType());
			buffer.append(" ");
		}

		buffer.append("INDEX ");
		buffer.append(index.getName());
		buffer.append(" ON ");
		buffer.append(table.getFullyQualifiedName());

		if (index.getColumnsNames() != null) {
			buffer.append(" (");

			for (Iterator iter = index.getColumnsNames().iterator(); iter.hasNext() ;) {
				String columnName = (String)iter.next();

				buffer.append(columnName);

				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}

			buffer.append(")");
		}

		return buffer.toString();
	}

}

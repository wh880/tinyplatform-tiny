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

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.delete.DeleteBody;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Delete extends StatementParser implements Statement {

	private DeleteBody deleteBody;

	private Delete() {
		deleteBody = new DeleteBody();
	}

	public static Delete delete(Table table) {
		Delete delete = new Delete();
		delete.getDeleteBody().setTable(table);
		return delete;
	}

	public Delete where(Condition condition) {
		deleteBody.setWhere(condition);
		return this;
	}

	public DeleteBody getDeleteBody() {
		return deleteBody;
	}

	@Override
	protected void parserStatementBody() {
		parser(deleteBody);
	}

	@Override
	public String toString() {
		return sql();
	}

}

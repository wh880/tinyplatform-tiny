/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.tinysqldsl;

import java.util.Arrays;

import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.base.InsertContext;
import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.base.Value;

/**
 * Insert语句 Created by luoguo on 2015/3/11.
 */
public class Insert extends StatementSqlBuilder implements Statement {

	private InsertContext context;
	/**
	 * SQL语句的标识，只是用于方便的识别是哪个SQL，没有其它意义，可以不设置
	 */
	private String id;

	public String getId() {
		return id;
	}

	private Insert() {
		context = new InsertContext();
	}

	public InsertContext getContext() {
		return context;
	}

	public static Insert insertInto(Table table) {
		Insert insert = new Insert();
		insert.getContext().setTable(table);
		insert.getContext().setSchema(table.getSchemaName());
		insert.getContext().setTableName(table.getName());
		return insert;
	}

	public Insert values(Value... values) {
		context.addValues(values);
		return this;
	}

	public Insert columns(Column... columns) {
		context.setColumns(Arrays.asList(columns));
		context.setUseValues(false);
		return this;
	}

	public Insert selectBody(Select select) {
		context.setItemsList(null);
		context.setSelectBody(select.getPlainSelect());
		return this;
	}

	@Override
	public String toString() {
		return sql();
	}

	@Override
	protected void parserStatementBody() {
		build(context.createInsert());
	}

	public void id(String id) {
		this.id = id;
	}
}

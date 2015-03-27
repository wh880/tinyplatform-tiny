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

import org.tinygroup.tinysqldsl.base.Condition;
import org.tinygroup.tinysqldsl.base.Statement;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.expression.OracleHierarchicalExpression;
import org.tinygroup.tinysqldsl.formitem.FromItem;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.select.Fetch;
import org.tinygroup.tinysqldsl.select.Join;
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.select.Offset;
import org.tinygroup.tinysqldsl.select.OrderByElement;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.CustomSelectItem;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Select extends StatementSqlBuilder implements Statement {

	private PlainSelect plainSelect;
    private String id;

    public String getId() {
        return id;
    }

    private Select() {
		super();
		plainSelect = new PlainSelect();
	}

	public PlainSelect getPlainSelect() {
		return plainSelect;
	}

	public static Select select(SelectItem... selectItems) {
		Select select = new Select();
		select.getPlainSelect().addSelectItems(selectItems);
		return select;
	}

	public static CustomSelectItem customSelectItem(String format,
			SelectItem... selectItems) {
		return new CustomSelectItem(format, selectItems);
	}

	public static Select selectFrom(Table... tables) {
		Select select = new Select();
		select.getPlainSelect().addSelectItems(new AllColumns());
		select.getPlainSelect().setFromItem(new FromItemList(tables));
		return select;
	}

	public Select into(Table... tables) {
		plainSelect.addIntoTables(tables);
		return this;
	}

	public Select from(FromItem fromItems) {
		plainSelect.setFromItem(fromItems);
		return this;
	}

	public Select join(Join... joins) {
		plainSelect.addJoins(joins);
		return this;
	}

	public Select where(Condition condition) {
		plainSelect.setWhere(condition);
		return this;
	}

	public Select groupBy(Expression... expressions) {
		plainSelect.addGroupByExpressions(expressions);
		return this;
	}

	public Select orderBy(OrderByElement... orderByElements) {
		plainSelect.addOrderByElements(orderByElements);
		return this;
	}

	public Select having(Condition condition) {
		plainSelect.setHaving(condition.getExpression());
		return this;
	}

	public Select limit(int start, int limit) {
		plainSelect.setLimit(new Limit(start, limit, true, true));
		return this;
	}

	/**
	 * 生成的sql语句 start和limit用？代替
	 * 
	 * @param limit
	 * @return
	 */
	public Select limit(Limit limit) {
		plainSelect.setLimit(limit);
		return this;
	}

	public Select offset(Offset offset) {
		plainSelect.setOffset(offset);
		return this;
	}

	public Select fetch(Fetch fetch) {
		plainSelect.setFetch(fetch);
		return this;
	}

	public Select forUpdate() {
		plainSelect.setForUpdate(true);
		return this;
	}

	public Select startWith(Condition startWithCondition,
			Condition connectCondition, boolean noCycle) {
		OracleHierarchicalExpression expression = new OracleHierarchicalExpression();
		expression.setStartExpression(startWithCondition.getExpression());
		expression.setConnectExpression(connectCondition.getExpression());
		expression.setNoCycle(noCycle);
		plainSelect.setOracleHierarchical(expression);
		return this;
	}

	@Override
	public String toString() {
		return sql();
	}

	@Override
	protected void parserStatementBody() {
		build(plainSelect);
	}

    public void id(String id) {
        this.id=id;
    }
}

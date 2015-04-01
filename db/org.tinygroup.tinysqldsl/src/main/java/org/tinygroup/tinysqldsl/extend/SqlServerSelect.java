package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.base.Condition;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.formitem.FromItem;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.select.Fetch;
import org.tinygroup.tinysqldsl.select.Join;
import org.tinygroup.tinysqldsl.select.Offset;
import org.tinygroup.tinysqldsl.select.OrderByElement;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * 与sqlserver数据库相关的查询语句
 * 
 * @author renhui
 * 
 */
public class SqlServerSelect extends Select {

	private SqlServerSelect() {
		super();
	}

	public static SqlServerSelect select(SelectItem... selectItems) {
		SqlServerSelect select = new SqlServerSelect();
		select.getPlainSelect().addSelectItems(selectItems);
		return select;
	}

	public static SqlServerSelect selectFrom(Table... tables) {
		SqlServerSelect select = new SqlServerSelect();
		select.getPlainSelect().addSelectItems(new AllColumns());
		select.getPlainSelect().setFromItem(new FromItemList(tables));
		return select;
	}

	public SqlServerSelect into(Table... tables) {
		plainSelect.addIntoTables(tables);
		return this;
	}

	public SqlServerSelect from(FromItem fromItems) {
		plainSelect.setFromItem(fromItems);
		return this;
	}

	public SqlServerSelect join(Join... joins) {
		plainSelect.addJoins(joins);
		return this;
	}

	public SqlServerSelect where(Condition condition) {
		plainSelect.setWhere(condition);
		return this;
	}

	public SqlServerSelect groupBy(Expression... expressions) {
		plainSelect.addGroupByExpressions(expressions);
		return this;
	}

	public SqlServerSelect orderBy(OrderByElement... orderByElements) {
		plainSelect.addOrderByElements(orderByElements);
		return this;
	}

	public SqlServerSelect having(Condition condition) {
		plainSelect.setHaving(condition.getExpression());
		return this;
	}

	public SqlServerSelect forUpdate() {
		plainSelect.setForUpdate(true);
		return this;
	}

	public SqlServerSelect offset(Offset offset) {
		plainSelect.setOffset(offset);
		return this;
	}

	public SqlServerSelect fetch(Fetch fetch) {
		plainSelect.setFetch(fetch);
		return this;
	}
}

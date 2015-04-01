package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.base.Condition;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.expression.OracleHierarchicalExpression;
import org.tinygroup.tinysqldsl.formitem.FromItem;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.select.Join;
import org.tinygroup.tinysqldsl.select.OrderByElement;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * oracle数据库相关的查询
 * @author renhui
 *
 */
public class OracleSelect extends Select {

	private OracleSelect() {
		super();
	}

	public static OracleSelect select(SelectItem... selectItems) {
		OracleSelect select = new OracleSelect();
		select.getPlainSelect().addSelectItems(selectItems);
		return select;
	}

	public static OracleSelect selectFrom(Table... tables) {
		OracleSelect select = new OracleSelect();
		select.getPlainSelect().addSelectItems(new AllColumns());
		select.getPlainSelect().setFromItem(new FromItemList(tables));
		return select;
	}
	
	public OracleSelect from(FromItem fromItems) {
		plainSelect.setFromItem(fromItems);
		return this;
	}

	public OracleSelect join(Join... joins) {
		plainSelect.addJoins(joins);
		return this;
	}

	public OracleSelect where(Condition condition) {
		plainSelect.setWhere(condition);
		return this;
	}

	public OracleSelect groupBy(Expression... expressions) {
		plainSelect.addGroupByExpressions(expressions);
		return this;
	}

	public OracleSelect orderBy(OrderByElement... orderByElements) {
		plainSelect.addOrderByElements(orderByElements);
		return this;
	}

	public OracleSelect having(Condition condition) {
		plainSelect.setHaving(condition.getExpression());
		return this;
	}

	public OracleSelect forUpdate() {
		plainSelect.setForUpdate(true);
		return this;
	}
	
	public OracleSelect into(Table... tables) {
		plainSelect.addIntoTables(tables);
		return this;
	}

	public OracleSelect page(int start, int limit) {
		StringBuilder pagingSelect = new StringBuilder();
		if (start == 0) {
			start = 1;
		}
		pagingSelect
				.append("select * from ( select row_.*, rownum db_rownum from ( ");
		pagingSelect.append(sql());
		pagingSelect.append(" ) row_ where rownum <=" + (start + limit - 1)
				+ ") where db_rownum >=" + start);
		this.stringBuilder = pagingSelect;
		return this;
	}

	public OracleSelect startWith(Condition startWithCondition,
			Condition connectCondition, boolean noCycle) {
		OracleHierarchicalExpression expression = new OracleHierarchicalExpression();
		expression.setStartExpression(startWithCondition.getExpression());
		expression.setConnectExpression(connectCondition.getExpression());
		expression.setNoCycle(noCycle);
		plainSelect.setOracleHierarchical(expression);
		return this;
	}
}

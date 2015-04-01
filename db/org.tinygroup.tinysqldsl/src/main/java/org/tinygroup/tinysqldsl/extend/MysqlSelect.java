package org.tinygroup.tinysqldsl.extend;

import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.base.Condition;
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.formitem.FromItem;
import org.tinygroup.tinysqldsl.formitem.FromItemList;
import org.tinygroup.tinysqldsl.select.Join;
import org.tinygroup.tinysqldsl.select.Limit;
import org.tinygroup.tinysqldsl.select.OrderByElement;
import org.tinygroup.tinysqldsl.selectitem.AllColumns;
import org.tinygroup.tinysqldsl.selectitem.SelectItem;

/**
 * mysql数据库相关的查询
 * @author renhui
 *
 */
public class MysqlSelect extends Select {
	private MysqlSelect() {
		super();
	}

	public static MysqlSelect select(SelectItem... selectItems) {
		MysqlSelect select = new MysqlSelect();
		select.getPlainSelect().addSelectItems(selectItems);
		return select;
	}

	public static MysqlSelect selectFrom(Table... tables) {
		MysqlSelect select = new MysqlSelect();
		select.getPlainSelect().addSelectItems(new AllColumns());
		select.getPlainSelect().setFromItem(new FromItemList(tables));
		return select;
	}

	public MysqlSelect from(FromItem fromItems) {
		plainSelect.setFromItem(fromItems);
		return this;
	}

	public MysqlSelect join(Join... joins) {
		plainSelect.addJoins(joins);
		return this;
	}

	public MysqlSelect where(Condition condition) {
		plainSelect.setWhere(condition);
		return this;
	}

	public MysqlSelect groupBy(Expression... expressions) {
		plainSelect.addGroupByExpressions(expressions);
		return this;
	}

	public MysqlSelect orderBy(OrderByElement... orderByElements) {
		plainSelect.addOrderByElements(orderByElements);
		return this;
	}

	public MysqlSelect having(Condition condition) {
		plainSelect.setHaving(condition.getExpression());
		return this;
	}

	public MysqlSelect forUpdate() {
		plainSelect.setForUpdate(true);
		return this;
	}
	
	public MysqlSelect limit(int start, int limit) {
		plainSelect.setLimit(new Limit(start, limit, false, false));
		return this;
	}
	/**
	 * 生成的sql语句 start和limit用？代替
	 * 
	 * @param limit
	 * @return
	 */
	public MysqlSelect limit(Limit limit) {
		plainSelect.setLimit(limit);
		return this;
	}

}

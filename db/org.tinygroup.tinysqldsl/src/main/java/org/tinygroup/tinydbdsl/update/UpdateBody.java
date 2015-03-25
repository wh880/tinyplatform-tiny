package org.tinygroup.tinydbdsl.update;

import java.util.List;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.SelectBody;
import org.tinygroup.tinydbdsl.base.StatementBody;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.formitem.FromItem;
import org.tinygroup.tinydbdsl.select.Join;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;

/**
 * The update statement.
 */
public class UpdateBody implements StatementBody {

	private List<Table> tables;
	private Expression where;
	private List<Column> columns;
	private List<Expression> expressions;
	private FromItem fromItem;
	private List<Join> joins;
	private SelectBody selectBody;
	private boolean useColumnsBrackets = true;
	private boolean useSelect = false;

	public List<Table> getTables() {
		return tables;
	}

	public Expression getWhere() {
		return where;
	}

	public void setTables(List<Table> list) {
		tables = list;
	}

	public void setWhere(Expression expression) {
		where = expression;
	}

	/**
	 * The {@link org.tinygroup.jsqlparser.schema.Column}s in this update (as
	 * col1 and col2 in UPDATE col1='a', col2='b')
	 * 
	 * @return a list of {@link org.tinygroup.jsqlparser.schema.Column}s
	 */
	public List<Column> getColumns() {
		return columns;
	}

	/**
	 * The {@link Expression}s in this update (as 'a' and 'b' in UPDATE
	 * col1='a', col2='b')
	 * 
	 * @return a list of {@link Expression}s
	 */
	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setColumns(List<Column> list) {
		columns = list;
	}

	public void setExpressions(List<Expression> list) {
		expressions = list;
	}

	public FromItem getFromItem() {
		return fromItem;
	}

	public void setFromItem(FromItem fromItem) {
		this.fromItem = fromItem;
	}

	public List<Join> getJoins() {
		return joins;
	}

	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}

	public SelectBody getSelectBody() {
		return selectBody;
	}

	public void setSelectBody(SelectBody selectBody) {
		this.selectBody = selectBody;
	}

	public boolean isUseColumnsBrackets() {
		return useColumnsBrackets;
	}

	public void setUseColumnsBrackets(boolean useColumnsBrackets) {
		this.useColumnsBrackets = useColumnsBrackets;
	}

	public boolean isUseSelect() {
		return useSelect;
	}

	public void setUseSelect(boolean useSelect) {
		this.useSelect = useSelect;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("UPDATE ");
		b.append(DslUtil.getStringList(getTables(), true, false)).append(
				" SET ");

		if (!useSelect) {
			for (int i = 0; i < getColumns().size(); i++) {
				if (i != 0) {
					b.append(", ");
				}
				b.append(columns.get(i)).append(" = ");
				b.append(expressions.get(i));
			}
		} else {
			if (useColumnsBrackets) {
				b.append("(");
			}
			for (int i = 0; i < getColumns().size(); i++) {
				if (i != 0) {
					b.append(", ");
				}
				b.append(columns.get(i));
			}
			if (useColumnsBrackets) {
				b.append(")");
			}
			b.append(" = ");
			b.append("(").append(selectBody).append(")");
		}

		if (fromItem != null) {
			b.append(" FROM ").append(fromItem);
			if (joins != null) {
				for (Join join : joins) {
					if (join.isSimple()) {
						b.append(", ").append(join);
					} else {
						b.append(" ").append(join);
					}
				}
			}
		}

		if (where != null) {
			b.append(" WHERE ");
			b.append(where);
		}
		return b.toString();
	}

	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}

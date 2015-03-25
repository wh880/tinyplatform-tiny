package org.tinygroup.tinydbdsl.delete;

import org.tinygroup.tinydbdsl.base.StatementBody;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;

public class DeleteBody implements StatementBody {

	private Table table;
	private Expression where;

	public Table getTable() {
		return table;
	}

	public Expression getWhere() {
		return where;
	}

	public void setTable(Table name) {
		table = name;
	}

	public void setWhere(Expression expression) {
		where = expression;
	}

	public String toString() {
		return "DELETE FROM " + table
				+ ((where != null) ? " WHERE " + where : "");
	}

	public void accept(StatementVisitor visitor) {
		visitor.visit(this);
	}
}

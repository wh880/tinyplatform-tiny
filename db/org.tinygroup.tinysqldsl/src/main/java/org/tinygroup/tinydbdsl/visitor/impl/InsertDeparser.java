package org.tinygroup.tinydbdsl.visitor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;
import org.tinygroup.tinydbdsl.expression.relational.MultiExpressionList;
import org.tinygroup.tinydbdsl.formitem.SubSelect;
import org.tinygroup.tinydbdsl.insert.InsertBody;
import org.tinygroup.tinydbdsl.selectitem.SelectExpressionItem;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;
import org.tinygroup.tinydbdsl.visitor.ItemsListVisitor;
import org.tinygroup.tinydbdsl.visitor.SelectVisitor;

public class InsertDeparser implements ItemsListVisitor {
	private StringBuilder buffer;
	private ExpressionVisitor expressionVisitor;
	private SelectVisitor selectVisitor;
	private List<Object> values = new ArrayList<Object>();

	public InsertDeparser() {
	}

	public InsertDeparser(ExpressionVisitor expressionVisitor,
			SelectVisitor selectVisitor, StringBuilder buffer,
			List<Object> values) {
		this.buffer = buffer;
		this.expressionVisitor = expressionVisitor;
		this.selectVisitor = selectVisitor;
		this.values = values;
	}

	public StringBuilder getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuilder buffer) {
		this.buffer = buffer;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public void deParse(InsertBody insert) {
		buffer.append("INSERT INTO ");
		buffer.append(insert.getTable().getFullyQualifiedName());
		if (insert.getColumns() != null) {
			buffer.append(" (");
			for (Iterator<Column> iter = insert.getColumns().iterator(); iter
					.hasNext();) {
				Column column = iter.next();
				buffer.append(column.getColumnName());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
			buffer.append(")");
		}

		if (insert.getItemsList() != null) {
			insert.getItemsList().accept(this);
		}

		if (insert.getSelectBody() != null) {
			buffer.append(" ");
			if (insert.isUseSelectBrackets()) {
				buffer.append("(");
			}
			insert.getSelectBody().accept(selectVisitor);
			if (insert.isUseSelectBrackets()) {
				buffer.append(")");
			}
		}

		if (insert.isReturningAllColumns()) {
			buffer.append(" RETURNING *");
		} else if (insert.getReturningExpressionList() != null) {
			buffer.append(" RETURNING ");
			for (Iterator<SelectExpressionItem> iter = insert
					.getReturningExpressionList().iterator(); iter.hasNext();) {
				buffer.append(iter.next().toString());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
		}
	}

	public void visit(ExpressionList expressionList) {
		buffer.append(" VALUES (");
		for (Iterator<Expression> iter = expressionList.getExpressions()
				.iterator(); iter.hasNext();) {
			Expression expression = iter.next();
			expression.accept(expressionVisitor);
			if (iter.hasNext()) {
				buffer.append(", ");
			}
		}
		buffer.append(")");
	}

	public void visit(MultiExpressionList multiExprList) {
		buffer.append(" VALUES ");
		for (Iterator<ExpressionList> it = multiExprList.getExprList()
				.iterator(); it.hasNext();) {
			buffer.append("(");
			for (Iterator<Expression> iter = it.next().getExpressions()
					.iterator(); iter.hasNext();) {
				Expression expression = iter.next();
				expression.accept(expressionVisitor);
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
			buffer.append(")");
			if (it.hasNext()) {
				buffer.append(", ");
			}
		}
	}

	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(selectVisitor);
	}

	public ExpressionVisitor getExpressionVisitor() {
		return expressionVisitor;
	}

	public SelectVisitor getSelectVisitor() {
		return selectVisitor;
	}

	public void setExpressionVisitor(ExpressionVisitor visitor) {
		expressionVisitor = visitor;
	}

	public void setSelectVisitor(SelectVisitor visitor) {
		selectVisitor = visitor;
	}
}

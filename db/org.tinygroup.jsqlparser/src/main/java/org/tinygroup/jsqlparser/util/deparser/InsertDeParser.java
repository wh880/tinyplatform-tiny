/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2013 JSQLParser
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package org.tinygroup.jsqlparser.util.deparser;

import java.util.Iterator;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsListVisitor;
import org.tinygroup.jsqlparser.expression.operators.relational.MultiExpressionList;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.statement.select.SubSelect;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) an {@link org.tinygroup.jsqlparser.statement.insert.Insert}
 */
public class InsertDeParser implements ItemsListVisitor {

	private StringBuilder buffer;
    private ExpressionVisitor expressionVisitor;
    private SelectVisitor selectVisitor;

	public InsertDeParser() {
	}

	/**
	 * @param expressionVisitor a {@link ExpressionVisitor} to de-parse
	 * {@link org.tinygroup.jsqlparser.expression.Expression}s. It has to share the
	 * same<br>
	 * StringBuilder (buffer parameter) as this object in order to work
	 * @param selectVisitor a {@link SelectVisitor} to de-parse
	 * {@link org.tinygroup.jsqlparser.statement.select.Select}s. It has to share the
	 * same<br>
	 * StringBuilder (buffer parameter) as this object in order to work
	 * @param buffer the buffer that will be filled with the insert
	 */
	public InsertDeParser(ExpressionVisitor expressionVisitor, SelectVisitor selectVisitor, StringBuilder buffer) {
		this.buffer = buffer;
		this.expressionVisitor = expressionVisitor;
		this.selectVisitor = selectVisitor;
	}

	public StringBuilder getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuilder buffer) {
		this.buffer = buffer;
	}

	public void deParse(Insert insert) {
		buffer.append("INSERT INTO ");
		buffer.append(insert.getTable().getFullyQualifiedName());
		if (insert.getColumns() != null) {
			buffer.append(" (");
			for (Iterator<Column> iter = insert.getColumns().iterator(); iter.hasNext();) {
				Column column = iter.next();
				buffer.append(column.getColumnName());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
			buffer.append(")");
		}

		insert.getItemsList().accept(this);

	}


	public void visit(ExpressionList expressionList) {
		buffer.append(" VALUES (");
		for (Iterator<Expression> iter = expressionList.getExpressions().iterator(); iter.hasNext();) {
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
		for (Iterator<ExpressionList> it = multiExprList.getExprList().iterator(); it.hasNext();) {
			buffer.append("(");
			for (Iterator<Expression> iter = it.next().getExpressions().iterator(); iter.hasNext();) {
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

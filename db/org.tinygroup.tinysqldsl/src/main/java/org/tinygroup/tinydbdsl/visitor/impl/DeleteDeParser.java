package org.tinygroup.tinydbdsl.visitor.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydbdsl.delete.DeleteBody;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) a {@link org.tinygroup.jsqlparser.statement.delete.Delete}
 */
public class DeleteDeParser {

	private StringBuilder buffer;
	private ExpressionVisitor expressionVisitor;
	private List<Object> values = new ArrayList<Object>();

	/**
	 * @param expressionVisitor
	 *            a {@link ExpressionVisitor} to de-parse expressions. It has to
	 *            share the same<br>
	 *            StringBuilder (buffer parameter) as this object in order to
	 *            work
	 * @param buffer
	 *            the buffer that will be filled with the select
	 */
	public DeleteDeParser(ExpressionVisitor expressionVisitor,
			StringBuilder buffer, List<Object> values) {
		this.buffer = buffer;
		this.expressionVisitor = expressionVisitor;
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

	public void deParse(DeleteBody delete) {
		buffer.append("DELETE FROM ").append(
				delete.getTable().getFullyQualifiedName());
		if (delete.getWhere() != null) {
			buffer.append(" WHERE ");
			delete.getWhere().accept(expressionVisitor);
		}

	}

	public ExpressionVisitor getExpressionVisitor() {
		return expressionVisitor;
	}

	public void setExpressionVisitor(ExpressionVisitor visitor) {
		expressionVisitor = visitor;
	}
}

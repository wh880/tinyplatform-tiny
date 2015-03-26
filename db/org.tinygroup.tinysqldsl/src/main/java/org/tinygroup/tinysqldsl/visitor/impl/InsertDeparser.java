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
package org.tinygroup.tinysqldsl.visitor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.expression.relational.ExpressionList;
import org.tinygroup.tinysqldsl.expression.relational.MultiExpressionList;
import org.tinygroup.tinysqldsl.formitem.SubSelect;
import org.tinygroup.tinysqldsl.insert.InsertBody;
import org.tinygroup.tinysqldsl.selectitem.SelectExpressionItem;
import org.tinygroup.tinysqldsl.visitor.ExpressionVisitor;
import org.tinygroup.tinysqldsl.visitor.ItemsListVisitor;
import org.tinygroup.tinysqldsl.visitor.SelectVisitor;

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

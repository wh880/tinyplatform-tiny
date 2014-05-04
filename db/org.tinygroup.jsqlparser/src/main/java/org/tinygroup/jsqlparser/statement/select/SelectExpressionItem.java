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
package org.tinygroup.jsqlparser.statement.select;

import org.tinygroup.jsqlparser.expression.Alias;
import org.tinygroup.jsqlparser.expression.Expression;

/**
 * An expression as in "SELECT expr1 AS EXPR"
 */
public class SelectExpressionItem implements SelectItem {

	private Expression expression;
	private Alias alias;

	public SelectExpressionItem() {
	}

	public SelectExpressionItem(Expression expression) {
		this.expression = expression;
	}
	
	public Alias getAlias() {
		return alias;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}


	public void accept(SelectItemVisitor selectItemVisitor) {
		selectItemVisitor.visit(this);
	}


	public String toString() {
		return expression + ((alias != null) ? alias.toString() : "");
	}
}

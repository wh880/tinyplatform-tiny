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
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsList;
import org.tinygroup.jsqlparser.expression.operators.relational.ItemsListVisitor;

/**
 * A subselect followed by an optional alias.
 */
public class SubSelect implements FromItem, Expression, ItemsList {

	private SelectBody selectBody;
	private Alias alias;

    private Pivot pivot;


	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}

	public SelectBody getSelectBody() {
		return selectBody;
	}

	public void setSelectBody(SelectBody body) {
		selectBody = body;
	}


	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}


	public Alias getAlias() {
		return alias;
	}


	public void setAlias(Alias alias) {
		this.alias = alias;
	}


    public Pivot getPivot() {
        return pivot;
    }


    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }


	public void accept(ItemsListVisitor itemsListVisitor) {
		itemsListVisitor.visit(this);
	}


	public String toString() {
		return "(" + selectBody + ")" 
                + ((pivot != null) ? " " + pivot : "")
                + ((alias != null) ? alias.toString() : "");
	}
}

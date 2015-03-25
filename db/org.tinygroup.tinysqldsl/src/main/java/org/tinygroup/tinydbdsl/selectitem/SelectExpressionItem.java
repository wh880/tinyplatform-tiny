package org.tinygroup.tinydbdsl.selectitem;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;

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

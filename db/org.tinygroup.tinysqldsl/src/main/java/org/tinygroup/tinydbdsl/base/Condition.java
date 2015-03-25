package org.tinygroup.tinydbdsl.base;

import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Condition implements Expression {
	private Expression expression;

	private Object[] values;

	public Condition(Expression expression, Object... values) {
		super();
		this.expression = expression;
		this.values = values;
	}

	public Expression getExpression() {
		return expression;
	}

	public Object[] getValues() {
		return values;
	}

	@Override
	public String toString() {
		return expression.toString();
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

}

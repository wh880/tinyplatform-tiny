package org.tinygroup.tinydbdsl.expression;

import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;

/**
 * 表达式
 * 
 * @author renhui
 * 
 */
public interface Expression {
	void accept(ExpressionVisitor expressionVisitor);
}

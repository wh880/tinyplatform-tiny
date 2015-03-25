package org.tinygroup.tinydbdsl.operator;

import org.tinygroup.tinydbdsl.expression.BinaryExpression;
import org.tinygroup.tinydbdsl.expression.Expression;

/**
 * 实例化BinaryExpression接口
 * 
 * @author renhui
 * 
 */
public interface ExpressionInstanceCallBack {
	BinaryExpression instance(Expression leftExpression,
			Expression rightExpression);
}
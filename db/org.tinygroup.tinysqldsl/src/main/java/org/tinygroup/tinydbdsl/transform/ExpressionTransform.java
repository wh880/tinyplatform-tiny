package org.tinygroup.tinydbdsl.transform;

import org.tinygroup.tinydbdsl.expression.Expression;


/**
 * 表达式转化接口
 * @author renhui
 *
 */
public interface ExpressionTransform {
	/**
	 * 是否是参数表达式
	 * @return
	 */
	boolean isParameterExpression(Expression expression);

	/**
	 * 把参数转化成表达式
	 * @param value
	 * @return
	 */
	Expression transform(Object value);
}

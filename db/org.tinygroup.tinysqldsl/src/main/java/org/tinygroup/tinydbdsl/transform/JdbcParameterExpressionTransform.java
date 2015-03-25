package org.tinygroup.tinydbdsl.transform;

import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.JdbcNamedParameter;
import org.tinygroup.tinydbdsl.expression.JdbcParameter;

/**
 * 默认转成JdbcParameter类型的表达式
 * @author renhui
 *
 */
public class JdbcParameterExpressionTransform implements ExpressionTransform {

	public Expression transform(Object value) {
		if(value instanceof JdbcNamedParameter){
              return new JdbcParameter();			
		}
		if(value instanceof JdbcParameter){
			return (JdbcParameter) value;
		}
		if(value instanceof Expression){
			return (Expression) value;
		}
		return new JdbcParameter();
	}

	public boolean isParameterExpression(Expression expression) {
		if(expression instanceof JdbcParameter){
			return true;
		}
		return false;
	}

}

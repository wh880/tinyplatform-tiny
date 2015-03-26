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
package org.tinygroup.tinydbdsl.operator;

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.expression.BinaryExpression;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.JdbcParameter;
import org.tinygroup.tinydbdsl.expression.relational.Between;
import org.tinygroup.tinydbdsl.expression.relational.EqualsTo;
import org.tinygroup.tinydbdsl.expression.relational.GreaterThan;
import org.tinygroup.tinydbdsl.expression.relational.GreaterThanEquals;
import org.tinygroup.tinydbdsl.expression.relational.IsNullExpression;
import org.tinygroup.tinydbdsl.expression.relational.LikeExpression;
import org.tinygroup.tinydbdsl.expression.relational.MinorThan;
import org.tinygroup.tinydbdsl.expression.relational.MinorThanEquals;
import org.tinygroup.tinydbdsl.expression.relational.NotEqualsTo;
import org.tinygroup.tinydbdsl.transform.ExpressionTransform;
import org.tinygroup.tinydbdsl.transform.JdbcParameterExpressionTransform;

/**
 * 二元操作接口的简单实现
 * 
 * @author renhui
 * 
 */
public abstract class SimpleBinaryOperator implements BinaryOperator,
		Expression, ExpressionTransform {

	private ExpressionTransform transform = new JdbcParameterExpressionTransform();

	public ExpressionTransform getTransform() {
		return transform;
	}

	public void setTransform(ExpressionTransform transform) {
		this.transform = transform;
	}

	public Condition eq(Object value) {
		return equal(value);
	}

	public Condition equal(Object value) {
		return toCondition(value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new EqualsTo(leExpression, rightExpression);
			}
		});
	}

	public Condition toCondition(Object value,
			ExpressionInstanceCallBack callBack) {
		Expression rightExpression = transform(value);
		BinaryExpression expression = callBack.instance(this, rightExpression);
		Condition condition = null;
		if (isParameterExpression(rightExpression)) {
			condition = new Condition(expression, value);
		} else {
			condition = new Condition(expression);
		}
		return condition;
	}

	public Condition neq(Object value) {
		return notEqual(value);
	}

	public Condition notEqual(Object value) {
		return toCondition(value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new NotEqualsTo(leExpression, rightExpression);
			}
		});
	}

	public Condition gt(Object value) {
		return greaterThan(value);
	}

	public Condition greaterThan(Object value) {
		return toCondition(value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new GreaterThan(leExpression, rightExpression);
			}
		});
	}

	public Condition gte(Object value) {
		return greaterThanEqual(value);
	}

	public Condition greaterThanEqual(Object value) {
		return toCondition(value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new GreaterThanEquals(leExpression, rightExpression);
			}
		});
	}

	public Condition lt(Object value) {
		return lessThan(value);
	}

	public Condition lessThan(Object value) {
		return toCondition(value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new MinorThan(leExpression, rightExpression);
			}
		});
	}

	public Condition lte(Object value) {
		return lessThanEqual(value);
	}

	public Condition lessThanEqual(Object value) {
		return toCondition(value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new MinorThanEquals(leExpression, rightExpression);
			}
		});
	}

	public Condition isNull() {
		IsNullExpression isNull = new IsNullExpression(this);
		Condition condition = new Condition(isNull);
		return condition;
	}

	public Condition isNotNull() {
		IsNullExpression isNotNull = new IsNullExpression(this, true);
		Condition condition = new Condition(isNotNull);
		return condition;
	}

	public Condition like(String value) {
		return toCondition("%" + value + "%", new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new LikeExpression(leExpression, rightExpression);
			}
		});
	}

	public Condition notLike(String value) {
		return toCondition("%" + value + "%", new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new LikeExpression(leExpression, rightExpression, true);
			}
		});
	}

	public Condition leftLike(String value) {
		return toCondition(value + "%", new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new LikeExpression(leExpression, rightExpression);
			}
		});
	}

	public Condition rightLike(String value) {
		return toCondition("%" + value, new ExpressionInstanceCallBack() {
			public BinaryExpression instance(Expression leExpression,
					Expression rightExpression) {
				return new LikeExpression(leExpression, rightExpression);
			}
		});
	}

	public Condition between(Object begin, Object end) {
		Between between = new Between(this, new JdbcParameter(),
				new JdbcParameter());
		Condition condition = new Condition(between, begin, end);
		return condition;
	}

	public Expression transform(Object value) {
		return transform.transform(value);
	}

	public boolean isParameterExpression(Expression expression) {
		return transform.isParameterExpression(expression);
	}
}

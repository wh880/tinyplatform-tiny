package org.tinygroup.tinydbdsl.operator;

import org.tinygroup.tinydbdsl.base.Condition;

/**
 * 二元操作接口
 * 
 * @author renhui
 * 
 */
public interface BinaryOperater {
	Condition eq(Object value);
	
	Condition equal(Object value);

	Condition between(Object start, Object end);

	Condition neq(Object value);

	Condition notEqual(Object value);

	Condition gt(Object value);

	Condition greaterThan(Object value);

	Condition gte(Object value);

	Condition greaterThanEqual(Object value);

	Condition lt(Object value);

	Condition lessThan(Object value);

	Condition lessThanEqual(Object value);

	Condition lte(Object value);

	Condition isNull();

	Condition isNotNull();

	Condition like(String value);
	
	Condition notLike(String value);

	Condition leftLike(String value);

	Condition rightLike(String value);
}

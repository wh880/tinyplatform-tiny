package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.base.Condition;

/**
 * Created by luoguo on 2015/3/11.
 */
public interface SelectField {
    Condition eq(Object value);

    Condition between(Object start, Object end);

    Condition ne(Object value);
    Condition notEqual(Object value);

    Condition gt(Object value);
    Condition greaterThan(Object value);

    Condition gte(Object value);
    Condition greaterOrEqual(Object value);

    Condition lt(Object value);
    Condition lessThan(Object value);

    Condition lessOrEqual(Object value);
    Condition lte(Object value);

    Condition isNull();

    Condition isNotNull();

    Condition like(String value);

    Condition leftLike(String value);

    Condition rightLike(String value);
}

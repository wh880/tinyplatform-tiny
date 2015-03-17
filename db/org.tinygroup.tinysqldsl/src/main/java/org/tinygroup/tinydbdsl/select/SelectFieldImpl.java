package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Operator;

/**
 * Created by luoguo on 2015/3/11.
 */
public class SelectFieldImpl implements SelectField {
   
    public Condition eq(Object value) {
        return new Condition(this, Operator.eq, value);
    }

   
    public Condition between(Object start, Object end) {
        return new Condition(this, Operator.between, start, end);
    }

   
    public Condition ne(Object value) {
        return new Condition(this, Operator.neq, value);
    }

   
    public Condition notEqual(Object value) {
       return ne(value);
    }

   
    public Condition gt(Object value) {
        return new Condition(this, Operator.gt, value);
    }

   
    public Condition greaterThan(Object value) {
        return gt(value);
    }

   
    public Condition gte(Object value) {
        return new Condition(this, Operator.gte, value);
    }

   
    public Condition greaterOrEqual(Object value) {
        return gte(value);
    }

   
    public Condition lt(Object value) {
        return new Condition(this, Operator.lt, value);
    }

   
    public Condition lessThan(Object value) {
        return lt(value);
    }

   
    public Condition lessOrEqual(Object value) {
        return lte(value);
    }

   
    public Condition lte(Object value) {
        return new Condition(this, Operator.lte, value);
    }

   
    public Condition isNull() {
        return new Condition(this, Operator.isNull, null);
    }

   
    public Condition isNotNull() {
        return new Condition(this, Operator.isNotNull, null);
    }

   
    public Condition like(String value) {
        return new Condition(this, Operator.like, value);
    }

   
    public Condition leftLike(String value) {
        return new Condition(this, Operator.leftLike, value);
    }

   
    public Condition rightLike(String value) {
        return new Condition(this, Operator.rightLike, value);
    }
}

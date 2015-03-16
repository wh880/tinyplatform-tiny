package org.tinygroup.tinydbdsl.base;

import org.tinygroup.tinydbdsl.select.SelectField;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Condition {
    private SelectField field;
    private Operator operator = Operator.eq;
    private Object[] values;

    public Condition(SelectField field, Object... value) {
        this.field = field;
        this.values = values;
    }

    public Condition(SelectField field, Operator operator, Object... value) {
        this.field = field;
        this.operator = operator;
        this.values = values;
    }

    public Condition() {

    }

    public SelectField getField() {
        return field;
    }


    public Operator getOperator() {
        return operator;
    }


    public Object[] getValues() {
        return values;
    }


    public static Condition and(Condition... conditions) {
        return null;
    }

    public static Condition or(Condition... conditions) {
        return null;
    }
}

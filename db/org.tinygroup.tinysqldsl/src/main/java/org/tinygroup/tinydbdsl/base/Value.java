package org.tinygroup.tinydbdsl.base;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Value {
    private Field field;
    private Object value;

    public Value(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }


    public Object getValue() {
        return value;
    }

}

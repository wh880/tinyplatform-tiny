package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.base.Field;

/**
 * Created by luoguo on 2015/3/11.
 */
public class DistinctField extends SelectFieldImpl {
    private Field field;

    public Field getField() {
        return field;
    }

    public DistinctField(Field field) {
        this.field = field;
    }
}

package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.base.Field;

/**
 * Created by luoguo on 2015/3/11.
 */
public class FieldCustom extends SelectFieldImpl {
    private final Field[] fields;
    private final String format;

    public FieldCustom(String format, Field... fields) {
        this.fields = fields;
        this.format = format;
    }

    public Field[] getFields() {
        return fields;
    }

    public String getFormat() {
        return format;
    }
}

package org.tinygroup.tinydbdsl.base;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Value {
    private Column column;
    private Object value;

    public Value(Column column, Object value) {
        this.column = column;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

	public Column getColumn() {
		return column;
	}

}
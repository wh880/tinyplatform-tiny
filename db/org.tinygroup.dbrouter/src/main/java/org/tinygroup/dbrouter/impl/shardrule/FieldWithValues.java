package org.tinygroup.dbrouter.impl.shardrule;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by luoguo on 14-1-22.
 */
@XStreamAlias("field-with-value")
public class FieldWithValues {
    @XStreamAlias("table-name")
    @XStreamAsAttribute
    String tableName;
    @XStreamAsAttribute
    String name;
    @XStreamAsAttribute
    String values;//多个值以逗号隔开

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}

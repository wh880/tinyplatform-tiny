package org.tinygroup.tinydbdsl.base;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Table {
    private String schema;
    private String name;
    private String alias;

    public Table() {

    }

    public Table(String name) {
        this.name = name;
    }

    public Table as(String alias) {
        Table table = new Table(schema, name, alias);
        table.alias=alias;
        return table;
    }

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Table(String schema, String name, String alias) {
        this.schema = schema;
        this.name = name;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }


    public String getSchema() {
        return schema;
    }


    public String getName() {
        return name;
    }

}

package org.tinygroup.tinysqldsl;

import org.tinygroup.tinysqldsl.base.Column;
import org.tinygroup.tinysqldsl.base.Table;

/**
 * Created by luoguo on 2015/3/11.
 */
public class CustomTable extends Table {
    public static final CustomTable CUSTOM = new CustomTable();
    public final Column ID = new Column(this, "id");
    public final Column NAME = new Column(this, "name");
    public final Column AGE = new Column(this, "age");

    public CustomTable() {
        super("custom");
    }
    
    public CustomTable(String schemaName,String alias) {
        super(schemaName, "custom", alias);
    }
    
    public CustomTable(String schemaName,String alias,boolean withAs) {
        super(schemaName, "custom", alias, withAs);
    }
}

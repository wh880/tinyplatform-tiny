package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Table;

/**
 * Created by luoguo on 2015/3/11.
 */
public class CustomTable extends Table {
    public static final CustomTable CUSTOM = new CustomTable();
    public final Column ID =  new Column(this,"id");
    public final Column NAME =  new Column(this,"name");
    public final Column AGE =  new Column(this,"age");
    private CustomTable() {
        super("custom");
    }
}

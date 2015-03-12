package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.base.Value;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Insert implements Statement {

    public static Insert insertInto(Table table) {
        return new Insert();
    }

    public Insert values(Value... values) {
        return new Insert();
    }

    public String sql() {
        return null;
    }

    public int execute() {
        return 0;
    }
}

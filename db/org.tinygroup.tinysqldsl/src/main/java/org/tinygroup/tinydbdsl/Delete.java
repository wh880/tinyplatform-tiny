package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Delete implements Statement {

    public static Delete delete(Table table) {
        return new Delete();
    }

    public Delete where(Condition... conditions) {
        return this;
    }

    public static Condition and(Condition... conditions) {
        return null;
    }

    public static Condition or(Condition... conditions) {
        return null;
    }

    public static Condition not(Condition condition) {
        return null;
    }

    public String sql() {
        return null;
    }

    public int execute() {
        return 0;
    }
}

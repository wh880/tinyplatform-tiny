package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.base.Value;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Update  implements Statement {

    public static Update update(Table table) {
        return new Update();
    }

    public Update set(Value... values) {
        return this;
    }

    public Update where(Condition... conditions) {
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
}

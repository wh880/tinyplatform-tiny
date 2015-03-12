package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Field;
import org.tinygroup.tinydbdsl.base.Table;

/**
 * Created by luoguo on 2015/3/11.
 */
public class UserTable extends Table {
    public static UserTable USER = new UserTable();
    public final Field NAME =  new Field("name");
    public final Field AGE =  new Field("age");
    private UserTable() {
        super("user");
    }
}

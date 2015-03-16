package org.tinygroup.tinydbdsl;

import static org.tinygroup.tinydbdsl.UserTable.USER;
import static org.tinygroup.tinydbdsl.Insert.*;
/**
 * Created by luoguo on 2015/3/11.
 */
public class TestInsert {
    public static void main(String[] args) {
        insertInto(USER).values(
                USER.NAME.value("悠然"),
                USER.AGE.value(22)
        );
    }
}

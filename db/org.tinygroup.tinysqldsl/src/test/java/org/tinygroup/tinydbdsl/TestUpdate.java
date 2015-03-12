package org.tinygroup.tinydbdsl;

import static org.tinygroup.tinydbdsl.UserTable.USER;
import static org.tinygroup.tinydbdsl.Update.update;

/**
 * Created by luoguo on 2015/3/11.
 */
public class TestUpdate {
    public static void main(String[] args) {
        update(USER).set(
                USER.NAME.to("abc"),
                USER.AGE.to(3)
        ).where(
                USER.NAME.eq("悠然")
        );
    }

}

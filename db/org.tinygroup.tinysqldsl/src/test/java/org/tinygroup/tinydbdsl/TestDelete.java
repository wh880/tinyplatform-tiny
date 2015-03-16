package org.tinygroup.tinydbdsl;

import static org.tinygroup.tinydbdsl.UserTable.USER;
import static org.tinygroup.tinydbdsl.Delete.delete;

/**
 * Created by luoguo on 2015/3/11.
 */
public class TestDelete {
    public static void main(String[] args) {
        delete(USER).where(
                USER.NAME.eq("悠然")
        );
        delete(USER).where(
                USER.NAME.leftLike("A"),
                USER.AGE.between(20,30)
        );
    }
}

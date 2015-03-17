package org.tinygroup.tinydbdsl;

import static org.tinygroup.tinydbdsl.UserTable.USER;
import static org.tinygroup.tinydbdsl.Select.*;
/**
 * Created by luoguo on 2015/3/11.
 */
public class TestSelect {
    public static void main(String[] args) {
        selectFrom(USER);

        select(
                customField("%s-%s"),
                USER.NAME,USER.AGE
        ).from(USER);

        select(
                customField("upper(%s)-%s"),
                USER.NAME,USER.AGE
        ).from(USER);

        selectFrom(USER).orderBy(USER.NAME.desc());

        selectFrom(USER).where(
                USER.NAME.eq("abc")
        );

        selectFrom(USER).where(
                USER.NAME.like("abc")
        );

        selectFrom(USER).where(
                or(
                        USER.NAME.like("abc"),
                        USER.AGE.gt(20)
                )
        );

        selectFrom(USER).where(
                USER.AGE.gt(20).and(
                        USER.NAME.like("abc"),
                        USER.AGE.gt(20)
                )
        );

        selectFrom(USER).where(
                USER.NAME.leftLike("abc")
        );

        selectFrom(USER).where(
                USER.AGE.between(23, 25)
        );

        select(USER.AGE.max()).from(USER).groupBy(USER.NAME,USER.AGE);
        select(USER.AGE.min()).from(USER);
        select(USER.AGE.avg()).from(USER);
        select(USER.AGE.count()).from(USER);
        select(USER.AGE.sum()).from(USER);
        select(USER.NAME.distinct()).from(USER);

        select(USER.AGE.sum()).from(USER).having(
                USER.AGE.sum().gt(100)
        ).join(
                select(USER.AGE.sum()).from(USER).having(
                        USER.AGE.sum().gt(100)
                )
        );
    }
}

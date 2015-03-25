package org.tinygroup.tinydbdsl;

import static org.tinygroup.tinydbdsl.Select.customSelectItem;
import static org.tinygroup.tinydbdsl.Select.or;
import static org.tinygroup.tinydbdsl.Select.and;
import static org.tinygroup.tinydbdsl.Select.select;
import static org.tinygroup.tinydbdsl.Select.selectFrom;
import static org.tinygroup.tinydbdsl.CustomTable.CUSTOM;
import static org.tinygroup.tinydbdsl.ScoreTable.TSCORE;
import static org.tinygroup.tinydbdsl.select.OrderByElement.desc;

import static org.tinygroup.tinydbdsl.select.Join.*;

/**
 * Created by luoguo on 2015/3/11.
 */
public class TestSelect {
	public static void main(String[] args) {
		System.out.println(selectFrom(CUSTOM));

		System.out.println(select(customSelectItem("%s-%s", CUSTOM.NAME, CUSTOM.AGE)).from(CUSTOM));

		System.out.println(select(customSelectItem("upper(%s)-%s", CUSTOM.NAME, CUSTOM.AGE))
				.from(CUSTOM));

		System.out.println(selectFrom(CUSTOM).orderBy(desc(CUSTOM.NAME)));

		System.out.println(selectFrom(CUSTOM).where(CUSTOM.NAME.eq("abc")));

		System.out.println(selectFrom(CUSTOM).where(CUSTOM.NAME.like("abc")));

		System.out.println(selectFrom(CUSTOM).where(or(CUSTOM.NAME.like("abc"), CUSTOM.AGE.gt(20))));

		System.out.println(selectFrom(CUSTOM).where(and(CUSTOM.NAME.like("abc"), CUSTOM.AGE.gt(20))));

		System.out.println(selectFrom(CUSTOM).where(CUSTOM.NAME.leftLike("abc")));

		System.out.println(selectFrom(CUSTOM).where(CUSTOM.AGE.between(23, 25)));

		System.out.println(select(CUSTOM.AGE.max()).from(CUSTOM).groupBy(CUSTOM.NAME, CUSTOM.AGE));
		System.out.println(select(CUSTOM.AGE.min()).from(CUSTOM));
		System.out.println(select(CUSTOM.AGE.avg()).from(CUSTOM));
		System.out.println(select(CUSTOM.AGE.count()).from(CUSTOM));
		System.out.println(select(CUSTOM.AGE.sum()).from(CUSTOM));
		System.out.println(select(CUSTOM.NAME.distinct()).from(CUSTOM).forUpdate());

		System.out.println(select(CUSTOM.NAME, CUSTOM.AGE, TSCORE.SCORE).from(CUSTOM)
				.join(leftJoin(TSCORE, CUSTOM.NAME.eq(TSCORE.NAME))).sql());

	}
}

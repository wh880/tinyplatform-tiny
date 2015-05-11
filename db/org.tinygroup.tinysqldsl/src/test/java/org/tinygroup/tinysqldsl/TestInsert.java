package org.tinygroup.tinysqldsl;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.ScoreTable.TSCORE;
import static org.tinygroup.tinysqldsl.Insert.insertInto;
import static org.tinygroup.tinysqldsl.Select.select;

/**
 * Created by luoguo on 2015/3/11.
 */
public class TestInsert {
	public static void main(String[] args) {
		System.out.println(insertInto(CUSTOM).values(CUSTOM.NAME.value("悠然"),
				CUSTOM.AGE.value(22)));

		System.out.println(insertInto(CUSTOM).columns(CUSTOM.NAME, CUSTOM.AGE)
				.selectBody(select(TSCORE.NAME, TSCORE.SCORE).from(TSCORE)));
	}
}

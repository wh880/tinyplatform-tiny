package org.tinygroup.tinysqldsl;

import org.tinygroup.tinysqldsl.expression.LongValue;
import org.tinygroup.tinysqldsl.expression.arithmetic.Addition;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Update.update;

/**
 * Created by luoguo on 2015/3/11.
 */
public class TestUpdate {
	public static void main(String[] args) {
		System.out.println(update(CUSTOM).set(CUSTOM.NAME.value("abc"),
				CUSTOM.AGE.value(3)).where(CUSTOM.NAME.eq("悠然")));
		System.out.println(update(CUSTOM).set(
				CUSTOM.AGE.value(new Addition(CUSTOM.AGE, new LongValue(3))))
				.where(CUSTOM.NAME.eq("悠然")));
	}

}

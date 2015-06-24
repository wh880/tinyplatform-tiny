package org.tinygroup.tinysqldsl;

import junit.framework.TestCase;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Insert.insertInto;

public class JunitTestInsert extends TestCase{

	
	public void testInsert() {
		assertEquals(insertInto(CUSTOM).values(CUSTOM.NAME.value("悠然"),CUSTOM.AGE.value(22)
        ).sql(), "INSERT INTO custom (name, age) VALUES(?, ?)");
	}

}

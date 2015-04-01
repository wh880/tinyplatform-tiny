package org.tinygroup.tinysqldsl;

import junit.framework.TestCase;
import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Select.selectFrom;
import static org.tinygroup.tinysqldsl.extend.MysqlSelect.selectFrom;
import static org.tinygroup.tinysqldsl.select.OrderByElement.desc;

public class TestMySql extends TestCase {

	public void testSql() {
		String sql = selectFrom(CUSTOM).orderBy(desc(CUSTOM.NAME)).limit(1, 10)
				.sql();
		assertEquals(
				"SELECT * FROM custom ORDER BY custom.name DESC LIMIT 10 OFFSET 1",
				sql);
	}
}

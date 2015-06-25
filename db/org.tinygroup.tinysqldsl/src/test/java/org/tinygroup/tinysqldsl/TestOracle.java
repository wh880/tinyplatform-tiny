/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.tinysqldsl;

import junit.framework.TestCase;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.extend.OracleSelect.selectFrom;
import static org.tinygroup.tinysqldsl.select.OrderByElement.desc;

public class TestOracle extends TestCase {

	public void testSql() {
		String sql = selectFrom(CUSTOM).orderBy(desc(CUSTOM.NAME)).page(1, 10)
				.sql();
		assertEquals(
				"select * from ( select row_.*, rownum db_rownum from ( SELECT * FROM custom ORDER BY custom.name DESC ) row_ where rownum <=10) where db_rownum >=1",
				sql);
	}
}

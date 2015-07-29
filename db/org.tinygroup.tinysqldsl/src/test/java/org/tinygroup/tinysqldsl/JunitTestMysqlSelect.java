/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
import static org.tinygroup.tinysqldsl.ScoreTable.TSCORE;
import static org.tinygroup.tinysqldsl.extend.MysqlComplexSelect.union;
import static org.tinygroup.tinysqldsl.extend.MysqlComplexSelect.unionAll;
import static org.tinygroup.tinysqldsl.extend.MysqlSelect.selectFrom;
import static org.tinygroup.tinysqldsl.select.Limit.limitWithParam;
public class JunitTestMysqlSelect extends TestCase {

	
	public void testMysqlSelect() {
		assertEquals(selectFrom(CUSTOM).limit(1, 10).sql(), "SELECT * FROM custom LIMIT 10 OFFSET 1");
		
		assertEquals(selectFrom(CUSTOM).limit(limitWithParam()).sql(), "SELECT * FROM custom LIMIT ? OFFSET ?");
		
		assertEquals(union(selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) UNION (SELECT * FROM custom)");
		
		assertEquals(unionAll(selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) UNION ALL (SELECT * FROM custom)");

	}

}

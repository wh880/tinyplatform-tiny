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
import static org.tinygroup.tinysqldsl.extend.SqlServerSelect.selectFrom;
import static org.tinygroup.tinysqldsl.select.Fetch.*;
import static org.tinygroup.tinysqldsl.select.Offset.offsetRow;

public class JunitTestSqlServer extends TestCase{

	public void testSqlServer() {
		assertEquals(selectFrom(CUSTOM).offset(offsetRow(0)).sql(),"SELECT * FROM custom OFFSET 0 ROW");
		
		assertEquals(selectFrom(CUSTOM).fetch(fetchWithFirstRow(1)).sql(),"SELECT * FROM custom FETCH FIRST 1 ROW ONLY");
		
		assertEquals(selectFrom(CUSTOM).fetch(fetchWithFirstRowParam(1)).sql(),"SELECT * FROM custom FETCH FIRST ? ROW ONLY");
		
		assertEquals(selectFrom(CUSTOM).fetch(fetchWithFirstRows(5)).sql(),"SELECT * FROM custom FETCH FIRST 5 ROWS ONLY");
		
		assertEquals(selectFrom(CUSTOM).fetch(fetchWithFirstRowsParam(5)).sql(),"SELECT * FROM custom FETCH FIRST ? ROWS ONLY");
		
		assertEquals(selectFrom(CUSTOM).offset(offsetRow(5)).fetch(fetchWithNextRow(1)).sql(),"SELECT * FROM custom OFFSET 5 ROW FETCH NEXT 1 ROW ONLY");
		
		assertEquals(selectFrom(CUSTOM).offset(offsetRow(5)).fetch(fetchWithNextRowParam(1)).sql(),"SELECT * FROM custom OFFSET 5 ROW FETCH NEXT ? ROW ONLY");
		
		assertEquals(selectFrom(CUSTOM).offset(offsetRow(5)).fetch(fetchWithNextRows(5)).sql(),"SELECT * FROM custom OFFSET 5 ROW FETCH NEXT 5 ROWS ONLY");
		
		assertEquals(selectFrom(CUSTOM).offset(offsetRow(5)).fetch(fetchWithNextRowsParam(5)).sql(),"SELECT * FROM custom OFFSET 5 ROW FETCH NEXT ? ROWS ONLY");
	}

}

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
import static org.tinygroup.tinysqldsl.Insert.insertInto;

public class JunitTestInsert extends TestCase{

	
	public void testInsert() {
		Insert insert = insertInto(CUSTOM).values(CUSTOM.NAME.value("悠然"), CUSTOM.AGE.value(22));
		assertEquals(insert.sql(), "INSERT INTO custom (name, age) VALUES(?, ?)");
		assertEquals(insert.getValues().size(), 2);
		assertEquals(insert.getValues().get(1),22);

		insert = insertInto(CUSTOM).values(
				CUSTOM.AGE.value(10),
				CUSTOM.NAME.value(null)
		);
		assertEquals(insert.sql(),"INSERT INTO custom (age) VALUES(?)");
		assertEquals(insert.getValues().size(), 1);
		assertEquals(insert.getValues().get(0),10);
	}

}

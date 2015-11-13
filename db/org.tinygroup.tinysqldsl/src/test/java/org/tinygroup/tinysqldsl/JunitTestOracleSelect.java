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
import org.tinygroup.tinysqldsl.base.Table;
import org.tinygroup.tinysqldsl.operator.SetOperationInstanceCallBack;
import org.tinygroup.tinysqldsl.select.SetOperation;
import org.tinygroup.tinysqldsl.select.UnionOperation;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.ScoreTable.TSCORE;
import static org.tinygroup.tinysqldsl.base.FragmentSql.fragmentCondition;
import static org.tinygroup.tinysqldsl.extend.OracleComplexSelect.*;
import static org.tinygroup.tinysqldsl.extend.OracleSelect.selectFrom;

public class JunitTestOracleSelect extends TestCase{

	public void testOracleSelect() {
		assertEquals(selectFrom(CUSTOM).page(0, 10).sql(), "select * from ( select row_.*, rownum db_rownum from ( SELECT * FROM custom ) row_ where rownum <=10) where db_rownum >=1");

		assertEquals(selectFrom(CUSTOM).into(new Table("test")).sql(), "SELECT * INTO test FROM custom");
		
		assertEquals(selectFrom(TSCORE).startWith(fragmentCondition("score.name=?", "aa"), fragmentCondition("score.custom_id=?", "bb"), false).sql(), "SELECT * FROM score START WITH score.name=? CONNECT BY score.custom_id=?");
	
		assertEquals(union(selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) UNION (SELECT * FROM score)");
		
		assertEquals(unionAll(selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) UNION ALL (SELECT * FROM score)");
		
		assertEquals(minus(selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) MINUS (SELECT * FROM score)");
		
		assertEquals(intersect(selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) INTERSECT (SELECT * FROM score)");
		
		
		assertEquals(setOperation(new SetOperationInstanceCallBack() {
			
			public SetOperation instanceOperation() {
				// TODO Auto-generated method stub
				return new UnionOperation();
			}
		},selectFrom(CUSTOM),selectFrom(TSCORE)).sql(), "(SELECT * FROM custom) UNION (SELECT * FROM score)");
	}

}

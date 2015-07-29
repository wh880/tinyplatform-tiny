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

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Insert.insertInto;
import static org.tinygroup.tinysqldsl.ScoreTable.TSCORE;
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

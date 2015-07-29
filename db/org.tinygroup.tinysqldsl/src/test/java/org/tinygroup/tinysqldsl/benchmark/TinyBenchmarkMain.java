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
package org.tinygroup.tinysqldsl.benchmark;

import static org.tinygroup.tinysqldsl.CustomTable.CUSTOM;
import static org.tinygroup.tinysqldsl.Insert.insertInto;

public class TinyBenchmarkMain {

	public static void main(String[] args) {
		TinyBenchmarkExecutor executor = new TinyBenchmarkExecutor();
		executor.setExecuteCount(5);
		executor.setLoopCount(1000 * 1000 * 10);
		executor.getInsertList().add(
				insertInto(CUSTOM).values(CUSTOM.NAME.value("悠然"),
						CUSTOM.AGE.value(22)));
		executor.execute();
	}
}

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
package org.tinygroup.databasechange;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TableSqlUtil {
	private static final String lINE_SEPATATOR = System.getProperty("line.separator");
	
	public static void appendSqlText(StringBuilder builder,Map<Class, List<String>> processSqls) throws IOException{
		for (Class clazz : processSqls.keySet()) {
			builder.append("/*-----").append(clazz.getSimpleName())
					.append("--end-----*/").append(lINE_SEPATATOR);
			List<String> sqls = processSqls.get(clazz);
			for (String sql : sqls) {
				builder.append(sql).append(";").append(lINE_SEPATATOR);
			}
			builder.append("/*-----").append(clazz.getSimpleName())
					.append("--end-----*/").append(lINE_SEPATATOR);
			builder.append(lINE_SEPATATOR);
		}
	}
}

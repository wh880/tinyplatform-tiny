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
package org.tinygroup.tinydbdsl.impl;

import javax.sql.DataSource;

import org.tinygroup.tinydbdsl.Delete;
import org.tinygroup.tinydbdsl.DslSqlExecute;
import org.tinygroup.tinydbdsl.Insert;
import org.tinygroup.tinydbdsl.Update;

/**
 * 简单的sql执行器
 * 
 * @author renhui
 * 
 */
public class SimpleDslSqlExecute extends AbstractDslSqlOperater implements DslSqlExecute {

	public SimpleDslSqlExecute(DataSource dataSource, Insert insert) {
		super(dataSource, insert);
	}

	public SimpleDslSqlExecute(DataSource dataSource, Update update) {
		super(dataSource, update);
	}

	public SimpleDslSqlExecute(DataSource dataSource, Delete delete) {
		super(dataSource, delete);
	}

	public int execute() {
		return jdbcTemplate.update(sql, values.toArray());
	}

}
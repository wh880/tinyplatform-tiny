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

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.tinygroup.tinydbdsl.base.Statement;

public class AbstractDslSqlOperator {

	protected JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	protected String sql;
	
	protected List<Object> values;
	
	public AbstractDslSqlOperator(DataSource dataSource, Statement statement){
		jdbcTemplate.setDataSource(dataSource);
		this.sql=statement.sql();
		this.values=statement.getValues();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public String getSql() {
		return sql;
	}

	public List<Object> getValues() {
		return values;
	}
}

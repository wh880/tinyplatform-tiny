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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.tinygroup.tinydbdsl.ComplexSelect;
import org.tinygroup.tinydbdsl.DslSqlQuery;
import org.tinygroup.tinydbdsl.Select;

/**
 * 数据查询接口的实现
 * 
 * @author renhui
 * 
 * @param <T>
 */
public class SimpleDslSqlQuery extends AbstractDslSqlOperater implements
		DslSqlQuery {

	public SimpleDslSqlQuery(DataSource dataSource, Select select) {
		super(dataSource, select);
	}

	public SimpleDslSqlQuery(DataSource dataSource, ComplexSelect complexSelect) {
		super(dataSource, complexSelect);
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchOneResult(Class<T> requiredType) {
		return (T) jdbcTemplate.queryForObject(sql, values.toArray(),
				new BeanPropertyRowMapper(requiredType));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] fetchArray(Class<T> requiredType) {
		List<T> records = fetchList(requiredType);
		return (T[]) records.toArray();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fetchList(Class<T> requiredType) {
		return jdbcTemplate.query(sql, values.toArray(),
				new BeanPropertyRowMapper(requiredType));
	}

	public SqlRowSet fetchResult() {
		return jdbcTemplate.queryForRowSet(sql, values.toArray());
	}

}

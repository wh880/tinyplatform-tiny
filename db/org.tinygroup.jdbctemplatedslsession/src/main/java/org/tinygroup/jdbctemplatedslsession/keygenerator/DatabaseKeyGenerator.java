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
package org.tinygroup.jdbctemplatedslsession.keygenerator;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.tinygroup.jdbctemplatedslsession.TableMetaData;
import org.tinygroup.tinysqldsl.Insert;
import org.tinygroup.tinysqldsl.KeyGenerator;
import org.tinygroup.tinysqldsl.base.InsertContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 由数据库自己来生成主键值,mysql为主键自增长,oracle为序列方式
 * 
 * @author renhui
 * 
 */
public class DatabaseKeyGenerator implements KeyGenerator {

	private JdbcTemplate jdbcTemplate;

	private Insert insert;

	private TableMetaData tableMetaData;

	public DatabaseKeyGenerator(JdbcTemplate jdbcTemplate, Insert insert,
			TableMetaData tableMetaData) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.insert = insert;
		this.tableMetaData = tableMetaData;
	}

	public <T> T generate(InsertContext insertContext) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(insert.parsedSql(),
						tableMetaData.getKeyNames());
				setParameterValues(ps, insert.getValues(), null);
				return ps;
			}
		}, keyHolder);
		Map keyMap = keyHolder.getKeys();
		if (keyMap.size() > 1) {
			throw new InvalidDataAccessApiUsageException(
					"The method should only be used when a single key is returned");
		}
		Iterator keyIter = ((Map) keyMap).values().iterator();
		if (keyIter.hasNext()) {
			return (T) keyIter.next();
		} else {
			throw new DataRetrievalFailureException(
					"Unable to retrieve the generated key. "
							+ "Check that the table has an identity column enabled.");
		}
	}

	/**
	 * Internal implementation for setting parameter values
	 * 
	 * @param preparedStatement
	 *            the PreparedStatement
	 * @param values
	 *            the values to be set
	 */
	private void setParameterValues(PreparedStatement preparedStatement,
			List<Object> values, int[] columnTypes) throws SQLException {
		int colIndex = 0;
		for (Object value : values) {
			colIndex++;
			if (columnTypes == null || colIndex < columnTypes.length) {
				StatementCreatorUtils.setParameterValue(preparedStatement,
						colIndex, SqlTypeValue.TYPE_UNKNOWN, value);
			} else {
				StatementCreatorUtils.setParameterValue(preparedStatement,
						colIndex, columnTypes[colIndex - 1], value);
			}
		}
	}

}

package org.tinygroup.jdbctemplatedslsession;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.tinysqldsl.Delete;
import org.tinygroup.tinysqldsl.DslSqlSession;
import org.tinygroup.tinysqldsl.Insert;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.Update;

public class SimpleDslSqlSession implements DslSqlSession {

	protected JdbcTemplate jdbcTemplate = new JdbcTemplate();

	public SimpleDslSqlSession(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int execute(Insert insert) {
		return jdbcTemplate.update(insert.sql(), insert.getValues().toArray());
	}

	public int execute(Update update) {
		return jdbcTemplate.update(update.sql(), update.getValues().toArray());
	}

	public int execute(Delete delete) {
		return jdbcTemplate.update(delete.sql(), delete.getValues().toArray());
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchOneResult(Select select, Class<T> requiredType) {
		return (T) jdbcTemplate.queryForObject(select.sql(), select.getValues()
				.toArray(), new BeanPropertyRowMapper(requiredType));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] fetchArray(Select select, Class<T> requiredType) {
		List<T> records = fetchList(select, requiredType);
		if (!CollectionUtil.isEmpty(records)) {
			return (T[]) records.toArray();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fetchList(Select select, Class<T> requiredType) {
		return jdbcTemplate.query(select.toString(), select.getValues()
				.toArray(), new BeanPropertyRowMapper(requiredType));
	}

}

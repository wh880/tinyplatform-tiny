package org.tinygroup.jdbctemplatedslsession;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.jdbctemplatedslsession.provider.DefaultPrimaryKeyProvider;
import org.tinygroup.jdbctemplatedslsession.rowmapper.SimpleRowMapperSelector;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinysqldsl.ComplexSelect;
import org.tinygroup.tinysqldsl.Delete;
import org.tinygroup.tinysqldsl.DslSession;
import org.tinygroup.tinysqldsl.Insert;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.Update;

/**
 * DslSqlSession接口的jdbctemplate版实现
 * 
 * @author renhui
 * 
 */
public class SimpleDslSession implements DslSession {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private RowMapperSelector selector = new SimpleRowMapperSelector();
	private PrimaryKeyProvider provider;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleDslSession.class);

	public SimpleDslSession(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(dataSource);
		provider = new DefaultPrimaryKeyProvider();
	}

	public RowMapperSelector getSelector() {
		return selector;
	}

	public void setSelector(RowMapperSelector selector) {
		this.selector = selector;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int execute(Insert insert) {
		logMessage(insert.sql(), insert.getValues());
		return jdbcTemplate.update(insert.sql(), insert.getValues().toArray());
	}

	public Number executeAndReturnKey(Insert insert) {
		jdbcInsert.setSchemaName(insert.getContext().getSchema());
		jdbcInsert.setTableName(insert.getContext().getTableName());
		jdbcInsert.setColumnNames(insert.getContext().getColumnNames());
		jdbcInsert.setGeneratedKeyNames(provider.generatedKeyNamesWithMetaData(
				jdbcTemplate.getDataSource(), null, insert.getContext()
						.getSchema(), insert.getContext().getTableName()));
		logMessage(insert.sql(), insert.getValues());
		return jdbcInsert.executeAndReturnKey(insert.getContext().getParams());
	}

	private void logMessage(String sql, List<Object> values) {
		LOGGER.logMessage(LogLevel.DEBUG, "Executing SQL:[{0}],values:{1}",
				sql, values);
	}

	public int execute(Update update) {
		logMessage(update.sql(), update.getValues());
		return jdbcTemplate.update(update.sql(), update.getValues().toArray());
	}

	public int execute(Delete delete) {
		logMessage(delete.sql(), delete.getValues());
		return jdbcTemplate.update(delete.sql(), delete.getValues().toArray());
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchOneResult(Select select, Class<T> requiredType) {
		logMessage(select.sql(), select.getValues());
		return (T) jdbcTemplate.queryForObject(select.sql(), select.getValues()
				.toArray(), selector.rowMapperSelector(requiredType));
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
		logMessage(select.sql(), select.getValues());
		return jdbcTemplate.query(select.toString(), select.getValues()
				.toArray(), selector.rowMapperSelector(requiredType));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] fetchArray(ComplexSelect complexSelect, Class<T> requiredType) {
		List<T> records = fetchList(complexSelect, requiredType);
		if (!CollectionUtil.isEmpty(records)) {
			return (T[]) records.toArray();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fetchList(ComplexSelect complexSelect,
			Class<T> requiredType) {
		logMessage(complexSelect.sql(), complexSelect.getValues());
		return jdbcTemplate.query(complexSelect.toString(), complexSelect
				.getValues().toArray(), selector
				.rowMapperSelector(requiredType));
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchOneResult(ComplexSelect complexSelect,
			Class<T> requiredType) {
		logMessage(complexSelect.sql(), complexSelect.getValues());
		return (T) jdbcTemplate.queryForObject(complexSelect.sql(),
				complexSelect.getValues().toArray(),
				selector.rowMapperSelector(requiredType));
	}

}

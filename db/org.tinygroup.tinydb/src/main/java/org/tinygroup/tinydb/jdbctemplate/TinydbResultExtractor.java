package org.tinygroup.tinydb.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.Field;
import org.tinygroup.tinydb.impl.DefaultNameConverter;
import org.tinygroup.tinydb.util.TinyBeanUtil;

/**
 * 结果集处理
 * 
 * @author renhui
 * 
 */
public class TinydbResultExtractor implements ResultSetExtractor {

	private BeanDbNameConverter converter = new DefaultNameConverter();
	private String beanType;
	private String schema;
	private int start;
	private int limit;

	// private

	public TinydbResultExtractor(String beanType, String schema,
			BeanDbNameConverter converter) {
		super();
		this.beanType = beanType;
		this.schema = schema;
		this.converter = converter;
	}

	public TinydbResultExtractor(String beanType, String schema, int start,
			int limit, BeanDbNameConverter converter) {
		this(beanType, schema, converter);
		this.start = start;
		this.limit = limit;
	}

	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		if (start <= 0) {
			start = 1;
		}
		List<Field> fields = TinyBeanUtil.getFieldsWithResultSet(rs, converter);
		RowMapper mapper = new BeanRowMapper(beanType, schema, fields);
		List results = new ArrayList();
		if (limit != 0) {// 需要进行游标分页
			if (rs.getType() == ResultSet.TYPE_FORWARD_ONLY) {
				results = extractDataWithForward(rs, mapper);
			} else {
				results = extractDataWithScroll(rs, mapper);
			}
		} else {
			results = extractData(rs, mapper);
		}
		return results;
	}

	private List extractData(ResultSet rs, RowMapper mapper)
			throws SQLException {
		List results = new ArrayList();
		int rowNum = 0;
		while (rs.next()) {
			results.add(mapper.mapRow(rs, rowNum++));
		}
		return results;
	}

	private List extractDataWithScroll(ResultSet rs, RowMapper mapper)
			throws SQLException {
		List results = new ArrayList();
		int rowNum = 0;
		if (rs.absolute(start)) {
			do {
				results.add(mapper.mapRow(rs, rowNum++));
			} while ((rs.next()) && (rowNum < limit));
		}
		return results;
	}

	private List extractDataWithForward(ResultSet rs, RowMapper mapper)
			throws SQLException {
		List results = new ArrayList();
		int rowNum = 0;
		int count = 0;
		// 1.游标位置定位
		while (rs.next()) {
			count++;
			if (count == start) {
				break;
			}
		}
		if (count == start) { // 绝对定位成功 ，记录数足够则提取分页需要的记录数
			do {
				results.add(mapper.mapRow(rs, rowNum++));
			} while ((rs.next()) && (rowNum < limit));
		}
		return results;
	}

}

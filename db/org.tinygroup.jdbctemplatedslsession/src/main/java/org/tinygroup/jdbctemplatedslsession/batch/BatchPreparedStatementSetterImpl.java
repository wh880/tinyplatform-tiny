package org.tinygroup.jdbctemplatedslsession.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;

public class BatchPreparedStatementSetterImpl implements
		BatchPreparedStatementSetter {
	private List<List<Object>> params;
	
	private int[] columnTypes;

	public BatchPreparedStatementSetterImpl(List<List<Object>> params,
			int[] columnTypes) {
		this.params = params;
		this.columnTypes=columnTypes;
	}

	public List<List<Object>> getParams() {
		return params;
	}

	public void setParams(List<List<Object>> params) {
		this.params = params;
	}

	public void setValues(PreparedStatement ps, int i) throws SQLException {
			List<Object> sqlParams = params.get(i);
			doSetStatementParameters(sqlParams, ps, columnTypes);
	}

	public int getBatchSize() {
		return params.size();
	}

	
	private void doSetStatementParameters(List<Object> values, PreparedStatement ps, int[] columnTypes) throws SQLException {
		int colIndex = 0;
		for (Object value : values) {
			colIndex++;
			if (value instanceof SqlParameterValue) {
				SqlParameterValue paramValue = (SqlParameterValue) value;
				StatementCreatorUtils.setParameterValue(ps, colIndex, paramValue, paramValue.getValue());
			}
			else {
				int colType;
				if (columnTypes == null || columnTypes.length < colIndex) {
					colType = SqlTypeValue.TYPE_UNKNOWN;
				}
				else {
					colType = columnTypes[colIndex - 1];
				}
				StatementCreatorUtils.setParameterValue(ps, colIndex, colType, value);
			}
		}
	}
}
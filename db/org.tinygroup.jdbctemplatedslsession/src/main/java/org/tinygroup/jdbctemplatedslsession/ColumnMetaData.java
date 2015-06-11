package org.tinygroup.jdbctemplatedslsession;


/**
 * 表格元数据对象
 * @author renhui
 *
 */
public class ColumnMetaData {

	private final String parameterName;

	private final int sqlType;

	private final boolean nullable;
	
	private final Object defaultValue;

	public ColumnMetaData(String parameterName, int sqlType, boolean nullable,
			Object defaultValue) {
		super();
		this.parameterName = parameterName;
		this.sqlType = sqlType;
		this.nullable = nullable;
		this.defaultValue = defaultValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public int getSqlType() {
		return sqlType;
	}

	public boolean isNullable() {
		return nullable;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}
}

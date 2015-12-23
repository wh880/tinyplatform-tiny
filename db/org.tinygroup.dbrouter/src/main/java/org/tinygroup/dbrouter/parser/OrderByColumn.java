package org.tinygroup.dbrouter.parser;

import org.tinygroup.dbrouter.parser.base.ColumnInfo;
import org.tinygroup.jsqlparser.schema.Column;

/**
 * 排序信息
 * @author renhui
 *
 */
public class OrderByColumn {
	private ColumnInfo columnInfo;
	private boolean asc = true;

	public OrderByColumn(Column column, boolean asc) {
		super();
		this.columnInfo = new ColumnInfo(column);
		this.asc = asc;
	}

	public String getColumnName() {
		return columnInfo.getName();
	}

	public boolean isAsc() {
		return asc;
	}

	public ColumnInfo getColumnInfo() {
		return columnInfo;
	}
	
}
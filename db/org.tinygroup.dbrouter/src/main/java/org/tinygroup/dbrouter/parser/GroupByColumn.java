package org.tinygroup.dbrouter.parser;

import org.tinygroup.dbrouter.parser.base.ColumnInfo;
import org.tinygroup.jsqlparser.schema.Column;

public class GroupByColumn {
	
	private ColumnInfo columnInfo;

	public GroupByColumn(Column column) {
		super();
		this.columnInfo=new ColumnInfo(column);
	}

	public ColumnInfo getColumnInfo() {
		return columnInfo;
	}
}

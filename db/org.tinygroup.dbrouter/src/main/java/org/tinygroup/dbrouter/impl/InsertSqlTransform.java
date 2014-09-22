package org.tinygroup.dbrouter.impl;

import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.tinygroup.dbrouter.config.Shard;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.insert.Insert;

public class InsertSqlTransform {
	
	private Insert originalInsert;
	
	private Shard firstShard;
	
	private DatabaseMetaData metaData;
	
	public InsertSqlTransform(Insert originalInsert,
			Shard firstShard, DatabaseMetaData metaData) {
		super();
		this.originalInsert = originalInsert;
		this.firstShard = firstShard;
		this.metaData = metaData;
	}
	
	public ColumnInfo getPrimaryColumn() throws SQLException{
		Table table = originalInsert.getTable();
		String tableName = table.getName();
		String realTableName = getRealTableName(firstShard.getTableMappingMap(), tableName);
		String queryTableName = realTableName;
		ResultSet rs = null;
		ColumnInfo columnInfo = null;
		try {
			rs = metaData.getPrimaryKeys(null, null, queryTableName);
			if (rs.next()) {
				columnInfo=getPrimaryKeys(rs,table,queryTableName);
			} else {
				rs.close();// 先关闭上次查询的resultset
				queryTableName = realTableName.toUpperCase();
				rs = metaData.getPrimaryKeys(null, null, queryTableName);
				if (rs.next()) {
					columnInfo=getPrimaryKeys(rs,table,queryTableName);
				}
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return columnInfo;
	}
	
	private  ColumnInfo getPrimaryKeys(ResultSet rs,Table table,String realTableName) throws SQLException {
		String primaryKey = rs.getString("COLUMN_NAME");
		ResultSet typeRs = metaData.getColumns(null, null, realTableName,
				primaryKey);
		ColumnInfo columnInfo=new ColumnInfo();
		columnInfo.setColumn(new Column(table, primaryKey));
		try {
			if (typeRs.next()) {
				 int dataType = typeRs.getInt("DATA_TYPE");
				 columnInfo.setDataType(dataType);
			}
		} finally {
			if (typeRs != null) {
				typeRs.close();
			}
		}
	    return columnInfo;
	}


	private  String getRealTableName(Map<String, String> tableMapping,
			String tableName) {
		String realTableName = tableName;// 真正在数据库存在的表名
		if (tableMapping != null && tableMapping.containsKey(tableName)) {
			realTableName = tableMapping.get(tableName);
		}
		return realTableName;
	}
	
	public class ColumnInfo implements Serializable{
		private Column column;
		private int dataType;
		public Column getColumn() {
			return column;
		}
		public void setColumn(Column column) {
			this.column = column;
		}
		public int getDataType() {
			return dataType;
		}
		public void setDataType(int dataType) {
			this.dataType = dataType;
		}
		public String getColumnName() {
			if(column!=null){
				return column.getColumnName();
			}
			return null;
		}
	}

}

package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.statement.alter.Alter;

public class AlterSqlVisitor {
	private SqlParserContext sqlParserContext;
    private StringBuilder buffer;
	public AlterSqlVisitor(SqlParserContext sqlParserContext) {
		super();
		this.sqlParserContext = sqlParserContext;
		this.buffer=sqlParserContext.getBuffer();
	}
	
	public void deParser(Alter alter){
		buffer.append("ALTER TABLE ");
		if(sqlParserContext.canReplaceTableName(alter.getTable().getName())){
			 buffer.append(sqlParserContext.getTargetTableName());
		}else{
			buffer.append(alter.getTable().getName());
		}
		buffer.append(" ADD COLUMN ").append(alter.getColumnName()).append(" ").append(alter.getDataType());
	}

}

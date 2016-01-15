package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.statement.drop.Drop;

public class DropSqlVisitor {
	
	private SqlParserContext sqlParserContext;
	private StringBuilder buffer;
	
	public DropSqlVisitor(SqlParserContext sqlParserContext) {
		super();
		this.sqlParserContext = sqlParserContext;
		this.buffer=sqlParserContext.getBuffer();
	}




	public void deParser(Drop drop){
		buffer.append("DROP ");
		if(drop.getType().equalsIgnoreCase("table")){
			 if(sqlParserContext.canReplaceTableName(drop.getName())){
				 buffer.append(drop.getType()).append(" ").append(sqlParserContext.getTargetTableName());
			 }else{
				 buffer.append(drop.getType()).append(" ").append(drop.getName());
			 }
			 sqlParserContext.getTableNames().add(drop.getName());
		}else{
		    buffer.append(drop.getType()).append(" ").append(drop.getName());	
		}
	}

}

package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.statement.truncate.Truncate;

public class TruncateSqlVisitor {
	
	private SqlParserContext sqlParserContext;
	private StringBuilder buffer;

	public TruncateSqlVisitor(SqlParserContext sqlParserContext) {
		super();
		this.sqlParserContext = sqlParserContext;
		this.buffer=sqlParserContext.getBuffer();
	}
	

	public void deParser(Truncate truncate){
		buffer.append("TRUNCATE TABLE ");
		if(sqlParserContext.canReplaceTableName(truncate.getTable().getName())){
			buffer.append(sqlParserContext.getTargetTableName());
		}else{
			buffer.append(truncate.getTable().getName());
		}
	}
	

}

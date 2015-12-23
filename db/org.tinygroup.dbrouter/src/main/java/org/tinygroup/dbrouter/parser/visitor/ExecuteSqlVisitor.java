package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.statement.execute.Execute;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;

public class ExecuteSqlVisitor {

	private SqlParserContext sqlParserContext;
	private StringBuilder buffer;
	
	public ExecuteSqlVisitor(SqlParserContext sqlParserContext) {
		this.sqlParserContext=sqlParserContext;
		this.buffer=sqlParserContext.getBuffer();
	}

	public void deParse(Execute execute) {
		buffer.append("EXECUTE ").append(execute.getName());
		buffer.append(" ").append(PlainSelect.getStringList(execute.getExprList().getExpressions(), true, false));
	}

	
}

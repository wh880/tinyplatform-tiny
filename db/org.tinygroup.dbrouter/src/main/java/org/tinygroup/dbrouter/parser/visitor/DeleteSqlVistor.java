package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.util.deparser.DeleteDeParser;

/**
 * 
 * @author renhui
 *
 */
public class DeleteSqlVistor extends DeleteDeParser {

	private SqlParserContext sqlParserContext;
	
	public DeleteSqlVistor(SqlParserContext sqlParserContext) {
		this.sqlParserContext=sqlParserContext;
		setBuffer(sqlParserContext.getBuffer());
	}
	
	public DeleteSqlVistor(ExpressionVisitor expressionVisitor,
			SqlParserContext sqlParserContext) {
		super(expressionVisitor, sqlParserContext.getBuffer());
		this.sqlParserContext=sqlParserContext;
	}

	
	public void deParse(Delete delete) {
		buffer.append("DELETE FROM ");
		Table tableName=delete.getTable();
		if (sqlParserContext.canReplaceTableName(tableName.getName())) {
			buffer.append(sqlParserContext.getTargetTableName());
		} else {
			buffer.append(tableName.getName());
		}
		sqlParserContext.getTableNames().add(tableName.getName());
		if (delete.getWhere() != null) {
			buffer.append(" WHERE ");
			delete.getWhere().accept(expressionVisitor);
		}
	}

}

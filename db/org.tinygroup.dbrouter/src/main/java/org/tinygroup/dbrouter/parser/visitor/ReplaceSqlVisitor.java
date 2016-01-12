package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.replace.Replace;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.util.deparser.ReplaceDeParser;

public class ReplaceSqlVisitor extends ReplaceDeParser {
    private SqlParserContext sqlParserContext;

	public ReplaceSqlVisitor(ExpressionVisitor expressionVisitor,
			SelectVisitor selectVisitor, SqlParserContext sqlParserContext) {
		super(expressionVisitor, selectVisitor, sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}

	@Override
	public void deParse(Replace replace) {
		buffer.append("REPLACE ");
		Table table=replace.getTable();
		if(sqlParserContext.canReplaceTableName(table.getName())){
			buffer.append(sqlParserContext.getTargetTableName());
		}else{
			buffer.append(table.getName());
		}
		sqlParserContext.getTableNames().add(table.getName());
		if (replace.getItemsList() != null) {
			if (replace.getColumns() != null) {
				buffer.append(" (");
				for (int i = 0; i < replace.getColumns().size(); i++) {
					Column column = replace.getColumns().get(i);
					buffer.append(column.getFullyQualifiedName());
					if (i < replace.getColumns().size() - 1) {
						buffer.append(", ");
					}
				}
				buffer.append(") ");
			} else {
				buffer.append(" ");
			}

		} else {
			buffer.append(" SET ");
			for (int i = 0; i < replace.getColumns().size(); i++) {
				Column column = replace.getColumns().get(i);
				buffer.append(column.getFullyQualifiedName()).append("=");

				Expression expression = replace.getExpressions().get(i);
				expression.accept(expressionVisitor);
				if (i < replace.getColumns().size() - 1) {
					buffer.append(", ");
				}

			}
		}

        if (replace.getItemsList() != null) {
			// REPLACE mytab SELECT * FROM mytab2
			// or VALUES ('as', ?, 565)

			if (replace.isUseValues()) {
				buffer.append(" VALUES");
			}

			buffer.append(replace.getItemsList());
		}
	}

}

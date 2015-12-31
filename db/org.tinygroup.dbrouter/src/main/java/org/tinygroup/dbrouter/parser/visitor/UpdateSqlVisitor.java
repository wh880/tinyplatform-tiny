package org.tinygroup.dbrouter.parser.visitor;

import java.util.List;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.select.Join;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.statement.update.Update;
import org.tinygroup.jsqlparser.util.deparser.UpdateDeParser;

/**
 * update语句的访问者
 * @author renhui
 *
 */
public class UpdateSqlVisitor extends UpdateDeParser {

	private SqlParserContext sqlParserContext;

	public UpdateSqlVisitor(ExpressionVisitor expressionVisitor,
			SelectVisitor selectVisitor, SqlParserContext sqlParserContext) {
		super(expressionVisitor, selectVisitor, sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}
	
	public void deParse(Update update) {
        buffer.append("UPDATE ");
        
        List<Table> tables=update.getTables();
        for (int i = 0; i < tables.size(); i++) {
        	Table table=tables.get(i);
        	if(sqlParserContext.canReplaceTableName(table.getName())){
        		buffer.append(sqlParserContext.getTargetTableName());
        	}else{
        		buffer.append(table.getName());
        	}
            if(i<tables.size()-1){
            	buffer.append(",");
            }
		}
        buffer.append(" SET ");
        if (!update.isUseSelect()) {
        	int columnIndex = Integer.MIN_VALUE;
        	sqlParserContext.setColumnIndex(columnIndex);
            for (int i = 0; i < update.getColumns().size(); i++) {
                Column column = update.getColumns().get(i);
                buffer.append(column.getFullyQualifiedName()).append(" = ");
                sqlParserContext.addColumn(column);
                sqlParserContext.addCondition(columnIndex, column);
                Expression expression = update.getExpressions().get(i);
                expression.accept(expressionVisitor);
                if (i < update.getColumns().size() - 1) {
                    buffer.append(", ");
                }
            	columnIndex++;
    			sqlParserContext.setColumnIndex(columnIndex);
            }
        } 

        if (update.getFromItem() != null) {
            buffer.append(" FROM ").append(update.getFromItem());
            if (update.getJoins() != null) {
                for (Join join : update.getJoins()) {
                    if (join.isSimple()) {
                        buffer.append(", ").append(join);
                    } else {
                        buffer.append(" ").append(join);
                    }
                }
            }
        }

        if (update.getWhere() != null) {
            buffer.append(" WHERE ");
            update.getWhere().accept(expressionVisitor);
        }

    }

}

package org.tinygroup.dbrouter.parser.visitor;

import java.util.Iterator;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.ExpressionVisitor;
import org.tinygroup.jsqlparser.expression.operators.relational.ExpressionList;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.select.SelectExpressionItem;
import org.tinygroup.jsqlparser.statement.select.SelectVisitor;
import org.tinygroup.jsqlparser.statement.select.WithItem;
import org.tinygroup.jsqlparser.util.deparser.InsertDeParser;

/**
 * 插入sql的访问者
 * 
 * @author renhui
 *
 */
public class InsertSqlVisitor extends InsertDeParser {

	private SqlParserContext sqlParserContext;

	public InsertSqlVisitor(ExpressionVisitor expressionVisitor,
			SelectVisitor selectVisitor, SqlParserContext sqlParserContext) {
		super(expressionVisitor, selectVisitor, sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}

	public void deParse(Insert insert) {
		buffer.append("INSERT INTO ");
		Table tableName = insert.getTable();
		if (sqlParserContext.canReplaceTableName(tableName.getName())) {
			buffer.append(sqlParserContext.getTargetTableName());
		} else {
			buffer.append(tableName.getName());
		}
		sqlParserContext.getTableNames().add(tableName.getName());
		if (insert.getColumns() != null) {
			buffer.append(" (");
			int columnIndex = Integer.MIN_VALUE;
			sqlParserContext.setColumnIndex(columnIndex);
			for (Iterator<Column> iter = insert.getColumns().iterator(); iter
					.hasNext();) {
				Column column = iter.next();
				sqlParserContext.addColumn(column);
				sqlParserContext.addCondition(columnIndex, column);
				buffer.append(column.getColumnName());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
				columnIndex++;
			}
			buffer.append(")");
		}

		if (insert.getItemsList() != null) {
			insert.getItemsList().accept(this);
		}

		if (insert.getSelect() != null) {
			buffer.append(" ");
			if (insert.isUseSelectBrackets()) {
				buffer.append("(");
			}
			if (insert.getSelect().getWithItemsList() != null) {
				buffer.append("WITH ");
				for (WithItem with : insert.getSelect().getWithItemsList()) {
					with.accept(selectVisitor);
				}
				buffer.append(" ");
			}
			insert.getSelect().getSelectBody().accept(selectVisitor);
			if (insert.isUseSelectBrackets()) {
				buffer.append(")");
			}
		}

		if (insert.isReturningAllColumns()) {
			buffer.append(" RETURNING *");
		} else if (insert.getReturningExpressionList() != null) {
			buffer.append(" RETURNING ");
			for (Iterator<SelectExpressionItem> iter = insert
					.getReturningExpressionList().iterator(); iter.hasNext();) {
				buffer.append(iter.next().toString());
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
		}
	}


	public void visit(ExpressionList expressionList) {
		buffer.append(" VALUES (");
		int columnIndex = sqlParserContext.getColumnIndex();
		for (Iterator<Expression> iter = expressionList.getExpressions()
				.iterator(); iter.hasNext();) {
			Expression expression = iter.next();
			expression.accept(expressionVisitor);
			if (iter.hasNext()) {
				buffer.append(", ");
			}
			columnIndex++;
			sqlParserContext.setColumnIndex(columnIndex);// 重新设置字段序号，是expressionvisitor方便获取下一个字段对应的条件信息
		}
		buffer.append(")");
	}

}

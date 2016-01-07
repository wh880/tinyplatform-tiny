package org.tinygroup.dbrouter.parser.visitor;

import java.util.Iterator;

import org.tinygroup.jsqlparser.statement.alter.Alter;
import org.tinygroup.jsqlparser.statement.create.index.CreateIndex;
import org.tinygroup.jsqlparser.statement.create.table.CreateTable;
import org.tinygroup.jsqlparser.statement.create.view.CreateView;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import org.tinygroup.jsqlparser.statement.drop.Drop;
import org.tinygroup.jsqlparser.statement.execute.Execute;
import org.tinygroup.jsqlparser.statement.insert.Insert;
import org.tinygroup.jsqlparser.statement.replace.Replace;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.WithItem;
import org.tinygroup.jsqlparser.statement.truncate.Truncate;
import org.tinygroup.jsqlparser.statement.update.Update;
import org.tinygroup.jsqlparser.util.deparser.DeleteDeParser;
import org.tinygroup.jsqlparser.util.deparser.InsertDeParser;
import org.tinygroup.jsqlparser.util.deparser.SelectDeParser;
import org.tinygroup.jsqlparser.util.deparser.StatementDeParser;

/**
 * DML\DDL语句的访问者
 * 
 * @author renhui
 *
 */
public class StatementSqlVisitor extends StatementDeParser {
	private SqlParserContext sqlParserContext;

	public StatementSqlVisitor(SqlParserContext sqlParserContext) {
		super(sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}

	@Override
	public void visit(CreateIndex createIndex) {
		CreateIndexSqlVisitor createIndexSqlVisitor = new CreateIndexSqlVisitor(
				sqlParserContext);
		sqlParserContext.setDDL(true);
		createIndexSqlVisitor.deParse(createIndex);
	}

	@Override
	public void visit(CreateTable createTable) {
		CreateTableSqlVisitor createTableSqlVisitor = new CreateTableSqlVisitor(
				sqlParserContext);
		sqlParserContext.setDDL(true);
		createTableSqlVisitor.deParse(createTable);
	}

	@Override
	public void visit(CreateView createView) {
		SelectDeParser selectDeParser = new SelectSqlVisitor(sqlParserContext);
		CreateViewSqlVisitor createViewSqlVisitor = new CreateViewSqlVisitor(
				sqlParserContext, selectDeParser);
		sqlParserContext.setDDL(true);
		createViewSqlVisitor.deParse(createView);
	}

	@Override
	public void visit(Delete delete) {
		SelectDeParser selectDeParser = new SelectSqlVisitor(sqlParserContext);
		selectDeParser.setBuffer(buffer);
		ExpressionSqlVisitor expressionDeParser = new ExpressionSqlVisitor(
				selectDeParser, sqlParserContext);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		DeleteDeParser deleteDeParser = new DeleteSqlVistor(expressionDeParser,
				sqlParserContext);
		sqlParserContext.setDML(true);
		deleteDeParser.deParse(delete);
	}

	@Override
	public void visit(Drop drop) {
		DropSqlVisitor dropSqlVisitor = new DropSqlVisitor(sqlParserContext);
		sqlParserContext.setDDL(true);
		dropSqlVisitor.deParser(drop);
	}

	@Override
	public void visit(Insert insert) {
		SelectDeParser selectDeParser = new SelectSqlVisitor(sqlParserContext);
		selectDeParser.setBuffer(buffer);
		ExpressionSqlVisitor expressionDeParser = new ExpressionSqlVisitor(
				selectDeParser, sqlParserContext);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		InsertDeParser insertDeParser = new InsertSqlVisitor(
				expressionDeParser, selectDeParser, sqlParserContext);
		sqlParserContext.setDML(true);
		insertDeParser.deParse(insert);
	}

	@Override
	public void visit(Replace replace) {
		SelectSqlVisitor selectVisitor = new SelectSqlVisitor(sqlParserContext);
		ExpressionSqlVisitor expressionVisitor = new ExpressionSqlVisitor(
				selectVisitor, sqlParserContext);
		selectVisitor.setExpressionVisitor(expressionVisitor);
		ReplaceSqlVisitor replaceSqlVisitor = new ReplaceSqlVisitor(
				expressionVisitor, selectVisitor, sqlParserContext);
		sqlParserContext.setDDL(true);
		replaceSqlVisitor.deParse(replace);
	}

	@Override
	public void visit(Select select) {
		SelectSqlVisitor selectDeParser = new SelectSqlVisitor(sqlParserContext);
		ExpressionSqlVisitor expressionDeParser = new ExpressionSqlVisitor(
				selectDeParser, sqlParserContext);
		sqlParserContext.setDML(true);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		if (select.getWithItemsList() != null
				&& !select.getWithItemsList().isEmpty()) {
			buffer.append("WITH ");
			for (Iterator<WithItem> iter = select.getWithItemsList().iterator(); iter
					.hasNext();) {
				WithItem withItem = iter.next();
				withItem.accept(selectDeParser);
				if (iter.hasNext()) {
					buffer.append(",");
				}
				buffer.append(" ");
			}
		}
		select.getSelectBody().accept(selectDeParser);
	}

	@Override
	public void visit(Truncate truncate) {
		TruncateSqlVisitor truncateSqlVisitor = new TruncateSqlVisitor(
				sqlParserContext);
		sqlParserContext.setDDL(true);
		truncateSqlVisitor.deParser(truncate);
	}

	@Override
	public void visit(Update update) {
		SelectDeParser selectDeParser = new SelectSqlVisitor(sqlParserContext);
		selectDeParser.setBuffer(buffer);
		ExpressionSqlVisitor expressionDeParser = new ExpressionSqlVisitor(
				selectDeParser, sqlParserContext);
		selectDeParser.setExpressionVisitor(expressionDeParser);
		UpdateSqlVisitor updateDeParser = new UpdateSqlVisitor(
				expressionDeParser, selectDeParser, sqlParserContext);
		sqlParserContext.setDML(true);
		updateDeParser.deParse(update);
	}

	@Override
	public void visit(Alter alter) {
		AlterSqlVisitor alterSqlVisitor = new AlterSqlVisitor(sqlParserContext);
		sqlParserContext.setDDL(true);
		alterSqlVisitor.deParser(alter);
	}

	@Override
	public void visit(Execute execute) {
		ExecuteSqlVisitor executeSqlVisitor=new ExecuteSqlVisitor(sqlParserContext);
		sqlParserContext.setDDL(true);
		executeSqlVisitor.deParse(execute);
	}

}

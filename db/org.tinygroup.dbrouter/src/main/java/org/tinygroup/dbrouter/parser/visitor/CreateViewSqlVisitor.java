package org.tinygroup.dbrouter.parser.visitor;

import org.tinygroup.jsqlparser.statement.create.view.CreateView;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.util.deparser.CreateViewDeParser;
import org.tinygroup.jsqlparser.util.deparser.SelectDeParser;

public class CreateViewSqlVisitor extends CreateViewDeParser {

	private SqlParserContext sqlParserContext;
	
	private SelectDeParser selectDeParser;
	
	public CreateViewSqlVisitor(SqlParserContext sqlParserContext,SelectDeParser selectDeParser) {
		super(sqlParserContext.getBuffer());
        this.sqlParserContext=sqlParserContext;
        this.selectDeParser=selectDeParser;
	}

	@Override
	public void deParse(CreateView createView) {
		buffer.append("CREATE ");
		if (createView.isOrReplace()) {
			buffer.append("OR REPLACE ");
		}
		if (createView.isMaterialized()) {
			buffer.append("MATERIALIZED ");
		}
		buffer.append("VIEW ").append(createView.getView().getFullyQualifiedName());
		if (createView.getColumnNames() != null) {
			buffer.append(PlainSelect.getStringList(createView.getColumnNames(), true, true));
		}
		buffer.append(" AS ");
		createView.getSelectBody().accept(selectDeParser);
	}
	
}

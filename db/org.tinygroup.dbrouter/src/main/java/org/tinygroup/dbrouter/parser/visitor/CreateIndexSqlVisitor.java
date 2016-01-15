package org.tinygroup.dbrouter.parser.visitor;

import java.util.Iterator;

import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.create.index.CreateIndex;
import org.tinygroup.jsqlparser.statement.create.table.Index;
import org.tinygroup.jsqlparser.util.deparser.CreateIndexDeParser;

public class CreateIndexSqlVisitor extends CreateIndexDeParser {

	private SqlParserContext sqlParserContext;

	public CreateIndexSqlVisitor(SqlParserContext sqlParserContext) {
		super(sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}

	@Override
	public void deParse(CreateIndex createIndex) {
		Index index = createIndex.getIndex();

		buffer.append("CREATE ");

		if (index.getType() != null) {
			buffer.append(index.getType());
			buffer.append(" ");
		}

		buffer.append("INDEX ");
		buffer.append(index.getName());
		buffer.append(" ON ");
		Table table = createIndex.getTable();
		if (sqlParserContext.canReplaceTableName(table.getName())) {
			buffer.append(sqlParserContext.getTargetTableName());
		} else {
			buffer.append(table.getName());
		}
		sqlParserContext.getTableNames().add(table.getName());
		if (index.getColumnsNames() != null) {
			buffer.append(" (");
			for (Iterator iter = index.getColumnsNames().iterator(); iter
					.hasNext();) {
				String columnName = (String) iter.next();
				buffer.append(columnName);

				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
			buffer.append(")");
		}
	}

}

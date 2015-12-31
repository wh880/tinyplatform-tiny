package org.tinygroup.dbrouter.parser.visitor;

import java.util.Iterator;

import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.create.table.ColumnDefinition;
import org.tinygroup.jsqlparser.statement.create.table.CreateTable;
import org.tinygroup.jsqlparser.statement.create.table.Index;
import org.tinygroup.jsqlparser.util.deparser.CreateTableDeParser;

public class CreateTableSqlVisitor extends CreateTableDeParser {

	private SqlParserContext sqlParserContext;

	public CreateTableSqlVisitor(SqlParserContext sqlParserContext) {
		super(sqlParserContext.getBuffer());
		this.sqlParserContext = sqlParserContext;
	}

	@Override
	public void deParse(CreateTable createTable) {
		buffer.append("CREATE ");
		if (createTable.isUnlogged()) {
			buffer.append("UNLOGGED ");
		}
		Table table=createTable.getTable();
		buffer.append("TABLE ");
		if(sqlParserContext.canReplaceTableName(table.getName())){
			buffer.append(sqlParserContext.getTargetTableName());
		}else{
			buffer.append(table.getName());
		}
		if (createTable.getSelect() != null) {
			buffer.append(" AS ").append(createTable.getSelect().toString());
		} else {
			if (createTable.getColumnDefinitions() != null) {
				buffer.append(" (");
				for (Iterator<ColumnDefinition> iter = createTable
						.getColumnDefinitions().iterator(); iter.hasNext();) {
					ColumnDefinition columnDefinition = iter.next();
					buffer.append(columnDefinition.getColumnName());
					buffer.append(" ");
					buffer.append(columnDefinition.getColDataType().toString());
					if (columnDefinition.getColumnSpecStrings() != null) {
						for (String s : columnDefinition.getColumnSpecStrings()) {
							buffer.append(" ");
							buffer.append(s);
						}
					}

					if (iter.hasNext()) {
						buffer.append(", ");
					}
				}

				if (createTable.getIndexes() != null) {
					for (Iterator<Index> iter = createTable.getIndexes()
							.iterator(); iter.hasNext();) {
						buffer.append(", ");
						Index index = iter.next();
						buffer.append(index.toString());
					}
				}

				buffer.append(")");
			}
		}
	}

}

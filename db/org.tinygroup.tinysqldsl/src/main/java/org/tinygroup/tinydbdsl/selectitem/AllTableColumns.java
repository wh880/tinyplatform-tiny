package org.tinygroup.tinydbdsl.selectitem;

/**
 * All the columns of a table (as in "SELECT TableName.* FROM ...")
 */
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;

public class AllTableColumns implements SelectItem {

	private Table table;

	public AllTableColumns() {
	}

	public AllTableColumns(Table tableName) {
		this.table = tableName;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public void accept(SelectItemVisitor selectItemVisitor) {
		  selectItemVisitor.visit(this);
	}

	public String toString() {
		return table + ".*";
	}
}

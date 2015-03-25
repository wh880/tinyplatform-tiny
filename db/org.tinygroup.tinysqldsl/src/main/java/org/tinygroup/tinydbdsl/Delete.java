package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.delete.DeleteBody;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Delete extends StatementParser implements Statement {

	private DeleteBody deleteBody;

	private Delete() {
		deleteBody = new DeleteBody();
	}

	public static Delete delete(Table table) {
		Delete delete = new Delete();
		delete.getDeleteBody().setTable(table);
		return delete;
	}

	public Delete where(Condition condition) {
		deleteBody.setWhere(condition);
		return this;
	}

	public DeleteBody getDeleteBody() {
		return deleteBody;
	}

	@Override
	protected void parserStatementBody() {
		parser(deleteBody);
	}

	@Override
	public String toString() {
		return sql();
	}

}

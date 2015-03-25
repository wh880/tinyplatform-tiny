package org.tinygroup.tinydbdsl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.base.Value;
import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.JdbcParameter;
import org.tinygroup.tinydbdsl.update.UpdateBody;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Update extends StatementParser implements Statement {

	private UpdateBody updateBody;

	private Update() {
		updateBody = new UpdateBody();
	}

	public static Update update(Table table) {
		Update update = new Update();
		update.getUpdateBody().setTables(Collections.singletonList(table));
		return update;
	}

	public Update set(Value... values) {
		List<Column> columns = new ArrayList<Column>();
		List<Expression> expressions = new ArrayList<Expression>();
		for (Value value : values) {
			columns.add(value.getColumn());
			expressions
					.add(new Condition(new JdbcParameter(), value.getValue()));
		}
		updateBody.setColumns(columns);
		updateBody.setExpressions(expressions);
		return this;
	}

	public Update where(Condition condition) {
		updateBody.setWhere(condition);
		return this;
	}

	public UpdateBody getUpdateBody() {
		return updateBody;
	}

	@Override
	protected void parserStatementBody() {
		parser(updateBody);
	}

	@Override
	public String toString() {
		return sql();
	}

}

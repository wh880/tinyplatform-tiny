package org.tinygroup.tinysqldsl.expression;

import org.tinygroup.tinysqldsl.base.FragmentSql;
import org.tinygroup.tinysqldsl.base.StatementSqlBuilder;

/**
 * 表达式的sql片段
 * 
 * @author renhui
 */
public class FragmentExpressionSql extends FragmentSql implements Expression {

	public FragmentExpressionSql(String fragment) {
		super(fragment);
	}

	public void builderExpression(StatementSqlBuilder builder) {
		builder.appendSql(getFragment());
	}

}

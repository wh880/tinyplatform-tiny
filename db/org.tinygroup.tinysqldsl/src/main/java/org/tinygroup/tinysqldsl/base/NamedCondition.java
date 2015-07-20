package org.tinygroup.tinysqldsl.base;

import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.expression.JdbcNamedParameter;

public class NamedCondition implements Expression {

	private JdbcNamedParameter namedParameter;

	private Object value;

	public NamedCondition(String name, Object value) {
		this.namedParameter = new JdbcNamedParameter(name);
		this.value = value;
	}

	public NamedCondition(JdbcNamedParameter namedParameter, Object value) {
		super();
		this.namedParameter = namedParameter;
		this.value = value;
	}

	public JdbcNamedParameter getNamedParameter() {
		return namedParameter;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return namedParameter.toString();
	}

	public void builderExpression(StatementSqlBuilder builder) {
		namedParameter.builderExpression(builder);
		builder.addParamValue(value);
	}

}

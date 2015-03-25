package org.tinygroup.tinydbdsl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.StatementBody;
import org.tinygroup.tinydbdsl.expression.conditional.ConditionExpressionList;
import org.tinygroup.tinydbdsl.visitor.StatementVisitor;
import org.tinygroup.tinydbdsl.visitor.impl.StatementDeParser;

/**
 * select结构的解析器
 * 
 * @author renhui
 * 
 */
public abstract class StatementParser {

	private List<Object> values;

	protected StringBuilder stringBuilder;

	private StatementVisitor visitor;

	private boolean parsed;
	
	public StatementParser() {
		this(new StringBuilder(), new ArrayList<Object>());
	}

	public StatementParser(StringBuilder stringBuilder, List<Object> values) {
		super();
		this.values = values;
		this.stringBuilder = stringBuilder;
		visitor = new StatementDeParser(stringBuilder, values);
	}

	public void parser(StatementBody statementBody) {
		statementBody.accept(visitor);
	}

	public List<Object> getValues() {
		return values;
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	public String sql() {
		if (parsed) {
			return stringBuilder.toString();
		}
		parserStatementBody();
		parsed=true;
		return stringBuilder.toString();
	}

	protected abstract void parserStatementBody();
	
	public static Condition and(Condition... conditions) {
		return conditional(" and ", conditions);
	}

	private static Condition conditional(String comma, Condition... conditions) {
		Assert.assertNotNull(conditions, "conditions must not null");
		Assert.assertTrue(conditions.length >= 2, "conditions 长度必须大于等于2");
		ConditionExpressionList expressionList = new ConditionExpressionList();
		expressionList.setComma(comma);
		expressionList.setUseBrackets(false);
		List<Object> values = new ArrayList<Object>();
		for (Condition condition : conditions) {
			expressionList.addExpression(condition.getExpression());
			Collections.addAll(values, condition.getValues());
		}
		return new Condition(expressionList, values);
	}

	public static Condition or(Condition... conditions) {
		return conditional(" or ", conditions);
	}

}

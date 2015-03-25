package org.tinygroup.tinydbdsl.expression.conditional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.tinydbdsl.expression.Expression;
import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.ExpressionVisitor;

public class ConditionExpressionList implements Expression{
	private List<Expression> expressions;

	private String comma = ",";
	
	private boolean useBrackets=true;
	
	private boolean useComma=true;

	public ConditionExpressionList() {
		expressions = new ArrayList<Expression>();
	}

	public ConditionExpressionList(List<Expression> expressions) {
		this.expressions = expressions;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public String getComma() {
		return comma;
	}

	public void setComma(String comma) {
		this.comma = comma;
	}
	
	public boolean isUseBrackets() {
		return useBrackets;
	}

	public void setUseBrackets(boolean useBrackets) {
		this.useBrackets = useBrackets;
	}

	public boolean isUseComma() {
		return useComma;
	}

	public void setUseComma(boolean useComma) {
		this.useComma = useComma;
	}

	public void setExpressions(List<Expression> list) {
		expressions = list;
	}

	public void addExpression(Expression expression) {
		if (expressions == null) {
			expressions = new ArrayList<Expression>();
		}
		expressions.add(expression);
	}

	public static ExpressionList expressionList(Expression expr) {
		return new ExpressionList(Collections.singletonList(expr));
	}

	public String toString() {
		return DslUtil.getStringList(expressions, useComma, useBrackets, comma);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

}

package org.tinygroup.tinydbdsl.operator;

import org.tinygroup.tinydbdsl.expression.Function;
import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;

public abstract class ColumnOperator extends SimpleBinaryOperater implements
		StatisticsOperater {

	public Function sum() {
		return new Function("sum", ExpressionList.expressionList(this));
	}

	public Function count() {
		return new Function("count", ExpressionList.expressionList(this));
	}

	public Function avg() {
		return new Function("avg", ExpressionList.expressionList(this));
	}

	public Function max() {
		return new Function("max", ExpressionList.expressionList(this));
	}

	public Function min() {
		return new Function("min", ExpressionList.expressionList(this));
	}

}

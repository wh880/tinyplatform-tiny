package org.tinygroup.tinydbdsl.expression;

/**
 * A basic class for binary expressions, that is expressions having a left
 * member and a right member which are in turn expressions.
 */
public abstract class BinaryExpression implements Expression {

	private Expression leftExpression;
	private Expression rightExpression;
	private boolean not = false;
	
	public BinaryExpression() {
		super();
	}

	public BinaryExpression(Expression leftExpression,
			Expression rightExpression) {
		this(leftExpression, rightExpression, false);
	}

	public BinaryExpression(Expression leftExpression,
			Expression rightExpression, boolean not) {
		super();
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.not = not;
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public boolean isNot() {
		return not;
	}


	public String toString() {
		return (not ? "NOT " : "") + getLeftExpression() + " " + getStringExpression() + " " + getRightExpression();
	}

	public abstract String getStringExpression();
}

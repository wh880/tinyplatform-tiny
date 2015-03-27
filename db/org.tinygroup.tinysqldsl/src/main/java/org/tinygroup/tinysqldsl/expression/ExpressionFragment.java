package org.tinygroup.tinysqldsl.expression;

import org.tinygroup.tinysqldsl.base.SqlFragment;
import org.tinygroup.tinysqldsl.visitor.ExpressionVisitor;

/**
 * 表达式的sql片段
 * @author renhui
 *
 */
public class ExpressionFragment extends SqlFragment implements Expression {

	public ExpressionFragment(String fragment) {
		super(fragment);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
             expressionVisitor.visit(this);
	}

}

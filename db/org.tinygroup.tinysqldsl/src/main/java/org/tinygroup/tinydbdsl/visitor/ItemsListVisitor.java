package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.expression.relational.ExpressionList;
import org.tinygroup.tinydbdsl.expression.relational.MultiExpressionList;
import org.tinygroup.tinydbdsl.formitem.SubSelect;

public interface ItemsListVisitor {

    void visit(SubSelect subSelect);

    void visit(ExpressionList expressionList);

    void visit(MultiExpressionList multiExprList);
}

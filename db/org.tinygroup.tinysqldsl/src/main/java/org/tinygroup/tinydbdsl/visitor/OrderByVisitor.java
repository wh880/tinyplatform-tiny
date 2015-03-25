package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.select.OrderByElement;

public interface OrderByVisitor {

	void visit(OrderByElement orderBy);
}

package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.PlainSelect;
import org.tinygroup.tinydbdsl.SetOperationList;

/**
 * 
 * @author renhui
 *
 */
public interface SelectVisitor {

	void visit(PlainSelect plainSelect);

	void visit(SetOperationList setOpList);

}

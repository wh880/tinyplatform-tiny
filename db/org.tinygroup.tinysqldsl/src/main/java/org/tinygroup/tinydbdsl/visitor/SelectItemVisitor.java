package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.base.Column;
import org.tinygroup.tinydbdsl.expression.Function;
import org.tinygroup.tinydbdsl.selectitem.AllColumns;
import org.tinygroup.tinydbdsl.selectitem.AllTableColumns;
import org.tinygroup.tinydbdsl.selectitem.CustomSelectItem;
import org.tinygroup.tinydbdsl.selectitem.Distinct;
import org.tinygroup.tinydbdsl.selectitem.SelectExpressionItem;
import org.tinygroup.tinydbdsl.selectitem.Top;

/**
 * selectitem的访问者
 * @author renhui
 *
 */
public interface SelectItemVisitor {

	void visit(AllColumns allColumns);

	void visit(AllTableColumns allTableColumns);

	void visit(SelectExpressionItem selectExpressionItem);
	
	void visit(Distinct distinct);
	
	void visit(Top top);
	
	void visit(CustomSelectItem selectItem);
	
	void visit(Function function);

	void visit(Column column);
}

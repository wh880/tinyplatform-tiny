package org.tinygroup.tinydbdsl.visitor;

import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.formitem.FromItemList;
import org.tinygroup.tinydbdsl.formitem.LateralSubSelect;
import org.tinygroup.tinydbdsl.formitem.SubJoin;
import org.tinygroup.tinydbdsl.formitem.SubSelect;
import org.tinygroup.tinydbdsl.formitem.ValuesList;

/**
 * fromitem的访问者
 * @author renhui
 *
 */
public interface FromItemVisitor {

	void visit(Table tableName);

	void visit(SubSelect subSelect);

	void visit(SubJoin subjoin);

	void visit(LateralSubSelect lateralSubSelect);

	void visit(ValuesList valuesList);
	
	void visit(FromItemList fromItemList);
}

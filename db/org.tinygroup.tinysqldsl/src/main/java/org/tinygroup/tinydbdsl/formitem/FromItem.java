package org.tinygroup.tinydbdsl.formitem;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * An item in a "SELECT [...] FROM item1" statement. (for example a table or a
 * sub-select)
 */
public interface FromItem {
	
	void accept(FromItemVisitor fromItemVisitor);

	Alias getAlias();

	void setAlias(Alias alias);

}

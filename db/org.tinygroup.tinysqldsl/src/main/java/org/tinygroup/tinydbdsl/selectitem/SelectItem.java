package org.tinygroup.tinydbdsl.selectitem;

import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;


/**
 * Anything between "SELECT" and "FROM"<BR>
 * @author renhui
 *
 */
public interface SelectItem {
	
	public void accept(SelectItemVisitor selectItemVisitor);

}

package org.tinygroup.tinydbdsl.selectitem;

import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;

/**
 * All the columns (as in "SELECT * FROM ...")
 */
public class AllColumns implements SelectItem {


    public String toString() {
        return "*";
    }

    public void accept(SelectItemVisitor selectItemVisitor) {
		  selectItemVisitor.visit(this);
	}
}

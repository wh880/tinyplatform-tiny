package org.tinygroup.tinydbdsl.formitem;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.select.Join;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * A table created by "(tab1 join tab2)".
 */
public class SubJoin implements FromItem {

	private FromItem left;
	private Join join;
	private Alias alias;
	
	public SubJoin(FromItem left, Join join, Alias alias) {
		super();
		this.left = left;
		this.join = join;
		this.alias = alias;
	}

	public FromItem getLeft() {
		return left;
	}

	public void setLeft(FromItem l) {
		left = l;
	}

	public Join getJoin() {
		return join;
	}

	public void setJoin(Join j) {
		join = j;
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public String toString() {
		return "(" + left + " " + join + ")"
				+ ((alias != null) ? alias.toString() : "");
	}

	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}
}

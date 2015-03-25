package org.tinygroup.tinydbdsl.formitem;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * A lateral subselect followed by an alias.
 * 
 * @author Tobias Warneke
 */
public class LateralSubSelect implements FromItem {

	private SubSelect subSelect;
	private Alias alias;
	

	public LateralSubSelect(SubSelect subSelect, Alias alias) {
		super();
		this.subSelect = subSelect;
		this.alias = alias;
	}

	public void setSubSelect(SubSelect subSelect) {
		this.subSelect = subSelect;
	}

	public SubSelect getSubSelect() {
		return subSelect;
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public String toString() {
		return "LATERAL" + subSelect.toString()
				+ ((alias != null) ? alias.toString() : "");
	}

	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}
}

package org.tinygroup.tinysqldsl.formitem;

import org.tinygroup.tinysqldsl.base.Alias;
import org.tinygroup.tinysqldsl.base.SqlFragment;
import org.tinygroup.tinysqldsl.visitor.FromItemVisitor;

/**
 * fromitem的sql片段
 * @author renhui
 *
 */
public class FromItemFragment extends SqlFragment implements FromItem {

	private Alias alias;

	public FromItemFragment(String fragment, Alias alias) {
		super(fragment);
		this.alias = alias;
	}

	public FromItemFragment(String fragment) {
		super(fragment);
	}

	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}

	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return getFragment() + ((alias != null) ? alias.toString() : "");
	}

}

package org.tinygroup.tinysqldsl.selectitem;

import org.tinygroup.tinysqldsl.base.SqlFragment;
import org.tinygroup.tinysqldsl.visitor.SelectItemVisitor;

/**
 * select 与 from 之间的特殊化sql片段
 * @author renhui
 *
 */
public class SelectItemFragment extends SqlFragment implements SelectItem {

	public SelectItemFragment(String fragment) {
		super(fragment);
	}
	
	public void accept(SelectItemVisitor selectItemVisitor) {
		selectItemVisitor.visit(this);
	}

}

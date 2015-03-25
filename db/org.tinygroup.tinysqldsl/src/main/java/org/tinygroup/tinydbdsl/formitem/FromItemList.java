package org.tinygroup.tinydbdsl.formitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * FromItem列表
 * @author renhui
 *
 */
public class FromItemList implements FromItem {

	private List<FromItem> fromItems;

	public FromItemList() {
		super();
		fromItems = new ArrayList<FromItem>();
	}

	public FromItemList(List<FromItem> fromItems) {
		this.fromItems = fromItems;
	}

	public FromItemList(FromItem... fromItems) {
		this.fromItems = new ArrayList<FromItem>();
		Collections.addAll(this.fromItems, fromItems);
	}

	public List<FromItem> getFromItems() {
		return fromItems;
	}

	public void setFromItems(List<FromItem> fromItems) {
		this.fromItems = fromItems;
	}

	public Alias getAlias() {
		return new Alias();
	}

	public void setAlias(Alias alias) {

	}

	@Override
	public String toString() {
		return DslUtil.getStringList(fromItems, true, false);
	}

	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}

}

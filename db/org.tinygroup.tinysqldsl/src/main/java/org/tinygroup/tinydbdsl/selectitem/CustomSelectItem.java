package org.tinygroup.tinydbdsl.selectitem;

import org.tinygroup.tinydbdsl.visitor.SelectItemVisitor;

/**
 * 格式化的selectItem
 * @author renhui
 *
 */
public class CustomSelectItem implements SelectItem {
	
	private SelectItem[] items;
	
	private String format;

	public CustomSelectItem(String format, SelectItem... items) {
		super();
		this.format = format;
		this.items = items;
	}

	public SelectItem[] getItems() {
		return items;
	}

	public String getFormat() {
		return format;
	}
	
	public void accept(SelectItemVisitor selectItemVisitor) {
		  selectItemVisitor.visit(this);
	}

	@Override
	public String toString() {
		Object[] args=new Object[items.length];
		for (int i = 0; i < args.length; i++) {
			args[i]=items[i];
		}
		return String.format(format, args);
	}
	
}

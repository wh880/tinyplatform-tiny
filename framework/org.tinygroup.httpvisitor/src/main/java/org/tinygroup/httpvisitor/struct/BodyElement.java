package org.tinygroup.httpvisitor.struct;

/**
 * Body组装元素
 * @author yancheng11334
 *
 */
public class BodyElement {

	final String name;
	
	final Object element;

	public BodyElement(String name, Object element) {
		super();
		this.name = name;
		this.element = element;
	}

	public String getName() {
		return name;
	}

	public Object getElement() {
		return element;
	}
	
	public String getType(){
		return element==null?"":element.getClass().getName();
	}
	
}

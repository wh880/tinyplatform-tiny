package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.BodyElement;
import org.tinygroup.httpvisitor.BodyElementMode;

/**
 * 正文分段的抽象类
 * @author yancheng11334
 *
 */
public abstract class AbstractBodyElement implements BodyElement{

	private String name;
	private Object element;
	private String contentType;
	private String charset;
	BodyElementMode type;
	
	public AbstractBodyElement(Object element){
		this(null,element,null,null);
	}
	
	public AbstractBodyElement(Object element,String charset){
		this(null,element,null,charset);
	}
	
	public AbstractBodyElement(Object element,String contentType,
			String charset){
		this(null,element,contentType,charset);
	}
	
	public AbstractBodyElement(String name, Object element, String contentType,
			String charset) {
		super();
		this.name = name;
		this.element = element;
		this.contentType = contentType;
		this.charset = charset;
	}
	
	public String getName() {
		return name;
	}
	public Object getElement() {
		return element;
	}
	public String getContentType() {
		return contentType;
	}
	public String getCharset() {
		return charset;
	}
	
}

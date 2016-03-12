package org.tinygroup.httpvisitor;

/**
 * HTTP正文的分段
 * @author yancheng11334
 *
 */
public interface BodyElement {

	public String getName();
	
	public Object getElement();
	
	public String getContentType();
	
	public String getCharset();
	
	public BodyElementMode getType();
}

package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.BodyElementMode;

public class StringElement extends AbstractBodyElement{

	public StringElement(String text) {
		super(text);
	}
	
	public StringElement(String name, String text, String contentType,
			String charset) {
		super(name,text,contentType,charset);
	}

	public BodyElementMode getType() {
		return BodyElementMode.STRING;
	}

}

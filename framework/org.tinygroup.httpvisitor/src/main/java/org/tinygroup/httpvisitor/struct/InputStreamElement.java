package org.tinygroup.httpvisitor.struct;

import java.io.InputStream;

import org.tinygroup.httpvisitor.BodyElementMode;

public class InputStreamElement extends AbstractBodyElement{

	public InputStreamElement(InputStream input) {
		super(input);
	}
	
	public InputStreamElement(String name, InputStream input, String contentType,
			String charset) {
		super(name,input,contentType,charset);
	}

	public BodyElementMode getType() {
		return BodyElementMode.INPUTSTREAM;
	}

}

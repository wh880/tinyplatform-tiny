package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.BodyElementMode;

public class ByteArrayElement extends AbstractBodyElement{

	public ByteArrayElement(byte[] data) {
		super(data);
	}
	
	public ByteArrayElement(String name, byte[] data, String contentType,
			String charset) {
		super(name,data,contentType,charset);
	}

	public BodyElementMode getType() {
		return BodyElementMode.BYTEARRAY;
	}

}

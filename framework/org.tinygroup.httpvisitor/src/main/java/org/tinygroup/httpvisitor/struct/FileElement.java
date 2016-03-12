package org.tinygroup.httpvisitor.struct;

import java.io.File;

import org.tinygroup.httpvisitor.BodyElementMode;

public class FileElement extends AbstractBodyElement{

	public FileElement(File file) {
		super(file);
	}
	
	public FileElement(String name, File file, String contentType,
			String charset) {
		super(name,file,contentType,charset);
	}

	public BodyElementMode getType() {
		return BodyElementMode.FILE;
	}

}

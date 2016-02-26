package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.Header;

public class SimpleHeader implements Header{

	private String name;
	
	private String value;
	
	public SimpleHeader(){
		super();
	}
	
	public SimpleHeader(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}

package org.tinygroup.xmlparser.ea;

import org.apache.commons.lang.StringUtils;

public class StdfieldPair {

	private String type;
	
	private String length;
	
	public StdfieldPair(String type, String length) {
		super();
		this.type = type;
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return type+length;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StdfieldPair) {
			if (StringUtils.equals(((StdfieldPair) obj).getType(), getType()) && StringUtils.equals(((StdfieldPair) obj).getLength(), getLength())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
}

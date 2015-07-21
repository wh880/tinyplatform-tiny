package org.tinygroup.xmlparser.ea;

import org.apache.commons.lang.StringUtils;

public class BizTypePair {

	private String type;
	
	private String length;
	
	public BizTypePair(String type, String length) {
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
		if (obj instanceof BizTypePair) {
			if (StringUtils.equals(((BizTypePair) obj).getType(), getType()) && StringUtils.equals(((BizTypePair) obj).getLength(), getLength())) {
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

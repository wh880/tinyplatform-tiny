package org.tinygroup.pageflowbasiccomponent;

import org.tinygroup.flow.ComponentInterface;

public abstract class AbstractWriteComponent implements ComponentInterface {

	protected String  resultKey;
	
	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

}

package org.tinygroup.codegen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("macro-define")
public class MacroDefine {
	@XStreamAlias("macro-path")
	@XStreamAsAttribute
	private String marcoPath;

	public String getMarcoPath() {
		return marcoPath;
	}

	public void setMarcoPath(String marcoPath) {
		this.marcoPath = marcoPath;
	}
	
}

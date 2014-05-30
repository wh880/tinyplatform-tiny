package org.tinygroup.codegen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("macro-define")
public class MacroDefine {
	@XStreamAlias("macro-path")
	@XStreamAsAttribute
	private String macroPath;

	public String getMacroPath() {
		return macroPath;
	}

	public void setMacroPath(String macroPath) {
		this.macroPath = macroPath;
	}

}

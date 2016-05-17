package org.tinygroup.mbean.config;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("tiny-mode-info")
public class TinyModeInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@XStreamAsAttribute
	@XStreamAlias("mode-name")
	private String tinyModeName;
	
	@XStreamAsAttribute
	private String from;
	
	@XStreamAsAttribute
	private String value;
	
	public String getTinyModeName() {
		return tinyModeName;
	}

	public void setTinyModeName(String tinyModeName) {
		this.tinyModeName = tinyModeName;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

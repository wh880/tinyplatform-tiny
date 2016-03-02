package org.tinygroup.remoteconfig.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Version implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1108803685220424646L;
	/**
	 * 唯一标识
	 */
	private String name;
	/**
	 * 版本名
	 */
	private String version;
	
	private List<Environment> environment = new ArrayList<Environment>();

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Environment> getEnvironment() {
		return environment;
	}

	public void setEnvironment(List<Environment> environment) {
		this.environment = environment;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}

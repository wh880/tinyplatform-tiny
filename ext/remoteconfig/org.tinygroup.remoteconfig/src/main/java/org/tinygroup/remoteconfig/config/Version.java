package org.tinygroup.remoteconfig.config;

import java.util.List;

public class Version {
	/**
	 * 唯一标识
	 */
	private String name;
	/**
	 * 版本名
	 */
	private String version;
	
	private List<Environment> environment;

	
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

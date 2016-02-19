package org.tinygroup.remoteconfig.config;

import java.util.List;

public class Environment {
	/**
	 * 唯一标识
	 */
	private String name;
	
	/**
	 * 环境名
	 */
	private String environment;
	
	private List<Module> modules;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	
	
}

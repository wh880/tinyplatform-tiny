package org.tinygroup.remoteconfig.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Environment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7042099267751885508L;

	/**
	 * 唯一标识
	 */
	private String name;
	
	/**
	 * 环境名
	 */
	private String environment;
	
	private List<Module> modules = new ArrayList<Module>();
	
	
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

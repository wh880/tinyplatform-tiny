package org.tinygroup.remoteconfig.config;

import java.util.List;

public class Module {
	/**
	 * 唯一标识
	 */
	private String name;
	/**
	 * 模块名
	 */
	private String moduleName;
	
	private List<Module> subModules;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<Module> getSubModules() {
		return subModules;
	}
	public void setSubModules(List<Module> subModules) {
		this.subModules = subModules;
	}
	
}

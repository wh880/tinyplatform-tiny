package org.tinygroup.remoteconfig.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 58712306119580740L;
	/**
	 * 唯一标识
	 */
	private String name;
	/**
	 * 模块名
	 */
	private String moduleName;
	
	private List<Module> subModules = new ArrayList<Module>();
	
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

/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter.pojo;

import java.io.Serializable;

import org.tinygroup.remoteconfig.config.ConfigPath;

/**
 * @author yanwj
 *
 */
public class ConfigServiceItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8853330158938582679L;
	String node;
	String value;
	ConfigPath configPath;
	
	public ConfigServiceItem(String node, String value ,ConfigPath configPath) {
		this.node = node;
		this.value = value;
		this.configPath = configPath;
	}
	
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public ConfigPath getConfigPath() {
		return configPath;
	}

	public void setConfigPath(ConfigPath configPath) {
		this.configPath = configPath;
	}
	
}

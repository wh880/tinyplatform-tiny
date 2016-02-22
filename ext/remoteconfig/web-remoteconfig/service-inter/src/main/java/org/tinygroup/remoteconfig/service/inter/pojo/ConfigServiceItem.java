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
	String key;
	String value;
	ConfigPath configPath;
	
	public ConfigServiceItem(String key, String value ,ConfigPath configPath) {
		this.key = key;
		this.value = value;
		this.configPath = configPath;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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

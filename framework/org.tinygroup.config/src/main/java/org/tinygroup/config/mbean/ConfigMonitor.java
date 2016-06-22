package org.tinygroup.config.mbean;

import java.util.Map;

import org.tinygroup.config.ConfigurationManager;
import org.tinygroup.config.util.ConfigurationUtil;

public class ConfigMonitor implements ConfigMonitorMBean{

	public Map<String, String> getConfigurations() {
		ConfigurationManager configurationManager = ConfigurationUtil.getConfigurationManager();
		return configurationManager.getConfiguration();
	}

	public String getConfigration(String key) {
		ConfigurationManager configurationManager = ConfigurationUtil.getConfigurationManager();
		return configurationManager.getConfiguration(key);
	}

	
}

	
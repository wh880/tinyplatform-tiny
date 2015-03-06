package org.tinygroup.weblayer.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.BasicTinyConfig;

public abstract class AbstractBasicTinyConfig implements BasicTinyConfig {

	protected String configName;

	protected  Map<String, String> parameterMap = new HashMap<String, String>();

	protected static Logger logger = LoggerFactory
			.getLogger(AbstractBasicTinyConfig.class);

	public String getConfigName() {
		return configName;
	}

	public String getInitParameter(String name) {
		return parameterMap.get(name);
	}

	public Iterator<String> getInitParameterNames() {
		return parameterMap.keySet().iterator();
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}
}

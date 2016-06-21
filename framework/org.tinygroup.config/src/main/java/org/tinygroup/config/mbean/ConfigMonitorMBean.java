package org.tinygroup.config.mbean;

import java.util.Map;

public interface ConfigMonitorMBean {

	/**
	 * 
	 * @description：获取配置信息
	 * @author: qiucn
	 * @version: 2016年6月21日下午2:34:08
	 */
	public Map<String,String> getConfigurations();
	
	/**
	 * 
	 * @description：获取指定配置信息
	 * @author: qiucn
	 * @version: 2016年6月21日下午2:42:20
	 */
	public String getConfigration(String key);
}

	
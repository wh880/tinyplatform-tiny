package org.tinygroup.remoteconfig.manager;

import java.util.Map;

public interface ConfigItemReader {

	/**
	 * 配置项获取
	 * 
	 * @param key 配置中心，配置项变量名
	 * @return 配置项变量值
	 */
	String get(String key);

	/**
	 * 验证配置项是否存在
	 * 
	 * @param key 配置中心，配置项变量名
	 * @return true:存在;false:不存在
	 */
	boolean exists(String key);

	/**
	 * 获取指定环境配置项合集
	 * 
	 * @return key:配置项变量名;value:变量值
	 */
	Map<String, String> getAll();
}

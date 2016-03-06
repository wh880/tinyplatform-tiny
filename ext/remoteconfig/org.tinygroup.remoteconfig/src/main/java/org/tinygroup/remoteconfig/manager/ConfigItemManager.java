package org.tinygroup.remoteconfig.manager;

import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;

/**
 * 此方法中，所有的configPath指向的module都必须是叶子module
 * 
 * @author chenjiao
 * 
 */
public interface ConfigItemManager {

	/**
	 * 写入配置项
	 * 该接口，包含写入和修改操作
	 * 
	 * @param key 变量名
	 * @param value 变量值
	 * @param configPath 配置项写入位置，此参数根据自身属性，指向唯一一份远程配置
	 */
	void set(String key, String value, ConfigPath configPath);

	/**
	 * 删除配置项
	 * 
	 * @param key 变量名
	 * @param configPath 配置项位置
	 */
	void delete(String key, ConfigPath configPath);

	/**
	 * 获取配置项
	 * 
	 * @param key 变量名
	 * @param configPath 配置项位置
	 * @return 变量值
	 */
	String get(String key, ConfigPath configPath);

	/**
	 * 验证配置项是否存在
	 * 
	 * @param key 变量名
	 * @param configPath 配置项位置
	 * @return true:存在;false:不存在
	 */
	boolean exists(String key, ConfigPath configPath);

	/**
	 * 获取指定环境配置项合集
	 * 
	 * @param 需要获取的配置项位置
	 * @return key:配置项变量名;value:变量值
	 */
	Map<String, String> getAll(ConfigPath configPath);

}

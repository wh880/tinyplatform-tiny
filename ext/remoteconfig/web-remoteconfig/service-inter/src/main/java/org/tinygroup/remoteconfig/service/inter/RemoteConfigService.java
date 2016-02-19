/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter;

import java.util.List;
import java.util.Map;

import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;

/**
 * @author yanwj
 *
 */
public interface RemoteConfigService {

	/**
	 * 获取节点配置
	 * 
	 * @param key
	 * @return
	 */
	public String get(ConfigServiceItem item);

	/**
	 * 设置节点信息
	 * 
	 * @param key
	 * @return
	 */
	public void add(ConfigServiceItem item);
	
	/**
	 * 设置节点信息
	 * 
	 * @param key
	 * @return
	 */
	public void set(String oldId ,ConfigServiceItem item);
	
	/**
	 * 获取此节点下所有子节点
	 * 
	 * @param key
	 * @param recursion 是否递归
	 * @return
	 */
	public Map<String ,String> getAll(ConfigServiceItem item);

	/**
	 * 单个删除，如果存在子节点，则删除失败，抛异常
	 * 
	 * @param key
	 * @return
	 */
	public void delete(ConfigServiceItem item);
	
	/**
	 * 批量删除，每个节点都会返回操作状态
	 * 单和节点如果存在子节点，则删除失败，抛异常
	 * 
	 * @param keys
	 * @return
	 */
	public void deletes(List<ConfigServiceItem> keys);
	
	/**
	 * 判断是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean isExit(ConfigServiceItem item);
	
}

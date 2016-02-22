/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter;

import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;

/**
 * @author Administrator
 *
 */
public interface RemoteConfigItemService {
	
	public void add(String key ,String value ,ConfigPath entity);
	
	public void set(String key ,String value ,ConfigPath entity);
	
	public void delete(String key ,ConfigPath entity);
	
	public String get(String key ,ConfigPath entity);
	
	public Map<String ,String> getAll(ConfigPath entity);
	
}

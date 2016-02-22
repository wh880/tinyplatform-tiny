/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter;

import java.util.List;

import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;

/**
 * @author Administrator
 *
 */
public interface RemoteConfigModuleService {
	
	public void add(Module module ,ConfigPath entity);
	
	public void set(Module oldModule ,Module newModule ,ConfigPath entity);
	
	public void delete(ConfigPath entity);
	
	public Module get(ConfigPath entity);
	
	List<Module> query(ConfigPath entity);
	
}

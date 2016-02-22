/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter;

import java.util.List;

import org.tinygroup.remoteconfig.config.Environment;

/**
 * @author Administrator
 *
 */
public interface RemoteConfigEnvService {
	
	public void add(Environment environment ,String versionId ,String productId);
	
	public void set(Environment oldEnvironment ,Environment newEnvironment ,String versionId ,String productId);
	
	public void delete(Environment environment ,String versionId ,String productId);
	
	public Environment get(Environment environment ,String versionId ,String productId);
	
	List<Environment> query(Environment environment ,String versionId ,String productId);
	
}

/**
 * 
 */
package org.tinygroup.remoteconfig.service.inter;

import java.util.List;

import org.tinygroup.remoteconfig.config.Version;

/**
 * @author Administrator
 *
 */
public interface RemoteConfigVersionService {
	
	public void add(Version version ,String productId);
	
	public void set(Version oldVersion ,Version newVersion ,String productId);
	
	public void delete(Version version ,String productId);
	
	public Version get(Version version ,String productId);
	
	List<Version> query(Version version ,String productId);
	
}

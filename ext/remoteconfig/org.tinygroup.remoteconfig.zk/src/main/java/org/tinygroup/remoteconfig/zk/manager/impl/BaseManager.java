/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import org.tinygroup.remoteconfig.manager.ConfigItemManager;

/**
 * @author Administrator
 *
 */
public class BaseManager {

	ConfigItemManager configItemManager;

	public void setConfigItemManager(ConfigItemManager configItemManager) {
		this.configItemManager = configItemManager;
	}
	
}

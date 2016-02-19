package org.tinygroup.remoteconfig.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.tinygroup.remoteconfig.RemoteConfigManageClient;

/**
 * @author yanwj
 *
 */
public class ZKRemoteConfigWebClient implements InitializingBean{
	
	RemoteConfigManageClient remoteConfigManageClient;
	
	public void setRemoteConfigManageClient(
			RemoteConfigManageClient remoteConfigManageClient) {
		this.remoteConfigManageClient = remoteConfigManageClient;
	}
	
	public void afterPropertiesSet() throws Exception {
		remoteConfigManageClient.start();
	}

}

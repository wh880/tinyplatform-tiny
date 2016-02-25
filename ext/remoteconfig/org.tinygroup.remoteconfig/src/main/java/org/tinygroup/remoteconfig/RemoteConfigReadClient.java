package org.tinygroup.remoteconfig;

import org.tinygroup.remoteconfig.manager.ConfigItemReader;


/**
 * 这个类是客户端工程使用(具体使用远程配置的工程) 所以只有只读方法
 * 
 * @author chenjiao
 * 
 */
public interface RemoteConfigReadClient extends  ConfigItemReader{

	/**
	 * 客户端初始化
	 * 
	 */
	public void start();
	
	/**
	 * 客户端停止
	 * 
	 */
	public void stop();

}

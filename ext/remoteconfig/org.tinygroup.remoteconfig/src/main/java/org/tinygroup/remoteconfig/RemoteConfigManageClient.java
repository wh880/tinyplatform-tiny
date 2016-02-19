package org.tinygroup.remoteconfig;



/**
 * 管理客户端端
 * @author chenjiao
 *
 */
public interface RemoteConfigManageClient {

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

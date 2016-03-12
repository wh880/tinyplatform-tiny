package org.tinygroup.httpvisitor;

/**
 * 授权信息接口
 * @author yancheng11334
 *
 */
public interface AuthScope {

	public String getHost();
	
	public int getPort();
	
	public String getRealm();
	
	public String getScheme();
}

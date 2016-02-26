package org.tinygroup.httpvisitor;

import java.util.Date;

/**
 * 模拟HTTP的cookie(统一底层的具体实现)
 * @author yancheng11334
 *
 */
public interface Cookie {

	public String getName();
	
	public String getValue();
	
	public Date getExpiryDate() ;
	
	public String getPath();
	
	public String getDomain();
	
	public boolean isSecure();
}

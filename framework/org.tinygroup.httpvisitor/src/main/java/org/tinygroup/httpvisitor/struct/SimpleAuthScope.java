package org.tinygroup.httpvisitor.struct;

import org.tinygroup.httpvisitor.AuthScope;

/**
 * 简易验证信息
 * @author yancheng11334
 *
 */
public class SimpleAuthScope implements AuthScope{

	 /** The authentication scheme the credentials apply to. */
    private String scheme;

    /** The realm the credentials apply to. */
    private String realm;

    /** The host the credentials apply to. */
    private String host;

    /** The port the credentials apply to. */
    private int port;
    
    public SimpleAuthScope(){
       this(null, 0, null, null);
    }
    
    public SimpleAuthScope(
            final String host,
            final int port) {
    	this(host, port, null, null);
    }
    
    public SimpleAuthScope(
            final String host,
            final int port,
            final String realm,
            final String scheme) {
    	super();
    	this.host = host;
    	this.port = port;
    	this.realm = realm;
    	this.scheme = scheme;
    }

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
   
}

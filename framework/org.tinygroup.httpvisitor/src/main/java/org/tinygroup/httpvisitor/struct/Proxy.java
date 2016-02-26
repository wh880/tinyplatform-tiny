package org.tinygroup.httpvisitor.struct;

/**
 * 代理信息类
 * @author yancheng11334
 *
 */
public class Proxy {

	private String host;
	private int port;
	private String proxyName;
	private String password;

	public Proxy(String host, int port, String proxyName, String password) {
		super();
		this.host = host;
		this.port = port;
		this.proxyName = proxyName;
		this.password = password;
	}

	public Proxy(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getProxyName() {
		return proxyName;
	}

	public String getPassword() {
		return password;
	}

}

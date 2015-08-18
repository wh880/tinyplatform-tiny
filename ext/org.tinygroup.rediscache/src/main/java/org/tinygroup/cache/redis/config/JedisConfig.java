package org.tinygroup.cache.redis.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("jedis-config")
public class JedisConfig {

    /**
     * 唯一标识,必填项
     */
	@XStreamAsAttribute
	private String id;
	
	/**
	 * redis转换编码，默认utf-8，选填项
	 */
	@XStreamAsAttribute
	private String charset;
	
	/**
	 * redis服务器地址，必填项
	 */
	@XStreamAsAttribute
	private String host;
	
	/**
	 * redis服务器地址，必填项
	 */
	@XStreamAsAttribute
	private int port;
	
	/**
	 * 客户端超时时间，非必填项
	 */
	@XStreamAsAttribute
	private int timeout;
	
	/**
	 * 连接密码，有密码必填；无密码不填
	 */
	@XStreamAsAttribute
	private String password;
	
	/**
	 * 数据库物理序号，默认是0，非必填项
	 */
	@XStreamAsAttribute
	private int database;
	
	/**
	 * 客户端名称，非必填项
	 */
	@XStreamAsAttribute
	@XStreamAlias("client-name")
	private String clientName;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
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

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public String toString() {
		return "JedisConfig [id=" + id + ", charset=" + charset + ", host="
				+ host + ", port=" + port + ", timeout=" + timeout
				+ ", password=" + password + ", database=" + database
				+ ", clientName=" + clientName + "]";
	}

}

package org.tinygroup.jedis.config;

import java.util.List;

import org.tinygroup.commons.tools.StringUtil;

import redis.clients.jedis.Protocol;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("jedis-sentinel-config")
public class JedisSentinelConfig {
	/**
	 * 所监听的主从集群配置的mastername
	 */
	@XStreamAsAttribute
	@XStreamAlias("master-name")
	private String masterName;
	/**
	 * sentinel服务器信息,结构为 ip:port 可以填写多个，多条信息以逗号分隔
	 */
	@XStreamAsAttribute
	private String sentinels;

	@XStreamAsAttribute
	@XStreamAlias("pool-config")
	private String poolConfig;
	/**
	 * sentinel的密码，有密码必填；无密码不填
	 */
	@XStreamAsAttribute
	private String password;
	/**
	 * 数据库物理序号，默认是0，非必填项
	 */
	@XStreamAsAttribute
	private int database;
	/**
	 * 客户端超时时间，非必填项
	 */
	@XStreamAsAttribute
	private int timeout;
	/**
	 * 读端列表，实际是该集群中的从服务器
	 */
	@XStreamImplicit
	private List<JedisConfig> jedisConfigList;

	public List<JedisConfig> getJedisConfigList() {
		return jedisConfigList;
	}

	public void setJedisConfigList(List<JedisConfig> jedisConfigList) {
		this.jedisConfigList = jedisConfigList;
	}

	public String getMasterName() {
		return masterName;
	}

	public int getDatabase() {
		if (database == 0) {

		}
		return database;
	}

	public void setDatabase(int database) {
		if (database == 0) {
			this.database = Protocol.DEFAULT_DATABASE;
		} else {
			this.database = database;
		}

	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		if (timeout == 0) {
			this.timeout = Protocol.DEFAULT_TIMEOUT;
		} else {
			this.timeout = timeout;
		}
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getSentinels() {
		return sentinels;
	}

	public void setSentinels(String sentinels) {
		this.sentinels = sentinels;
	}

	public String getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(String poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (StringUtil.isBlank(password)) {
			password = null;
		} else {
			this.password = password;
		}
	}

}

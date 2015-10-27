package org.tinygroup.jedis.config;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.tools.StringUtil;

import redis.clients.jedis.Protocol;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("shard-sentinel-config")
public class ShardSentinelConfig {
	@XStreamAsAttribute
	private String pattern;
	/**
	 * 所监听的主从集群配置的mastername
	 */
	@XStreamAsAttribute
	@XStreamAlias("master-name")
	private String masterName;

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
	 * 该集群中的所有服务器
	 */
	@XStreamImplicit
	private List<JedisConfig> jedisConfigList;

	
	public List<JedisConfig> getJedisConfigList() {
		if (jedisConfigList == null) {
			this.jedisConfigList = new ArrayList<JedisConfig>();
		}
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
			this.database = Protocol.DEFAULT_DATABASE;
		}
		return database;
	}

	public void setDatabase(int database) {
			this.database = database;
		}

	public int getTimeout() {
		if (timeout == 0) {
			this.timeout = Protocol.DEFAULT_TIMEOUT;
		}
		return timeout;
	}

	public void setTimeout(int timeout) {

		this.timeout = timeout;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
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

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}

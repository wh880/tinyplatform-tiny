package org.tinygroup.jedis.config;

import java.util.List;

import org.tinygroup.commons.tools.StringUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("shard-jedis-sentinel-configs")
public class ShardJedisSentinelConfigs {
	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String pool;

	@XStreamImplicit
	private List<ShardJedisSentinelConfig> jedisShardSentinelConfigsList;

	public List<ShardJedisSentinelConfig> getJedisShardSentinelConfigsList() {
		return jedisShardSentinelConfigsList;
	}

	public void setJedisShardSentinelConfigsList(
			List<ShardJedisSentinelConfig> jedisShardSentinelConfigsList) {
		this.jedisShardSentinelConfigsList = jedisShardSentinelConfigsList;
	}

	public String getId() {
		if (StringUtil.isBlank(id)) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

}

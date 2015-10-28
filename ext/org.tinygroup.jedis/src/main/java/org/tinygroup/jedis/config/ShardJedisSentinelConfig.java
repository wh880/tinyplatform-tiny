package org.tinygroup.jedis.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("shard-jedis-sentinel-config")
public class ShardJedisSentinelConfig {
	
	@XStreamImplicit
	private List<ShardSentinelConfigs> shardSentinelConfigs;

	public List<ShardSentinelConfigs> getShardSentinelConfigs() {
		if(shardSentinelConfigs==null){
			throw new RuntimeException("未配置任何redis信息");
		}
		return shardSentinelConfigs;
	}

	public void setShardSentinelConfigs(
			List<ShardSentinelConfigs> shardSentinelConfigs) {
		this.shardSentinelConfigs = shardSentinelConfigs;
	}

	

}

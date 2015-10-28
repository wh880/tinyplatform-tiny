package org.tinygroup.jedis.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("shard-sentinel-configs")
public class ShardSentinelConfigs {
	/**
	 * sentinel服务器信息,结构为 ip:port 可以填写多个，多条信息以逗号分隔
	 */
	@XStreamAsAttribute
	private String sentinels;
	
	@XStreamImplicit
	private List<ShardSentinelConfig> shardSentinelConfigLists;

	public List<ShardSentinelConfig> getShardSentinelConfigLists() {
		if(shardSentinelConfigLists==null){
			throw new RuntimeException("未配置任何redis信息");
		}
		return shardSentinelConfigLists;
	}

	public void setShardSentinelConfigLists(
			List<ShardSentinelConfig> shardSentinelConfigLists) {
		this.shardSentinelConfigLists = shardSentinelConfigLists;
	}

	public String getSentinels() {
		return sentinels;
	}

	public void setSentinels(String sentinels) {
		this.sentinels = sentinels;
	}

	
	
	

}

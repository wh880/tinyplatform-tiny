package org.tinygroup.jedis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.config.ShardJedisSentinelConfig;
import org.tinygroup.jedis.config.ShardJedisSentinelConfigs;
import org.tinygroup.jedis.config.ShardSentinelConfig;
import org.tinygroup.jedis.config.ShardSentinelConfigs;
import org.tinygroup.jedis.shard.TinyShardedJedisSentinelPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;


public class ShardJedisSentinelManagerImpl implements ShardJedisSentinelManager {

	private Map<String, Map<String, ShardSentinelConfig>> map = new HashMap<String, Map<String, ShardSentinelConfig>>();
	private TinyShardedJedisSentinelPool pool;
	public void addJedisSentinelConfigs(ShardJedisSentinelConfigs configs) {
		for (ShardJedisSentinelConfig config : configs
				.getJedisShardSentinelConfigsList()) {
			addJedisSentinelConfig(config);
		}

	}

	private void addJedisSentinelConfig(ShardJedisSentinelConfig config) {
		for (ShardSentinelConfigs shardSentinelConfigs : config
				.getShardSentinelConfigs()) {
			addShardSentinelConfigs(shardSentinelConfigs);
		}

	}

	private void addShardSentinelConfigs(ShardSentinelConfigs config) {
		String sentinels = config.getSentinels();
		List<ShardSentinelConfig> shardSentinelConfigLists = config
				.getShardSentinelConfigLists();
		if (shardSentinelConfigLists.size() == 0) {
			return;
		}
		Map<String, ShardSentinelConfig> sentinelMaster = map.get(sentinels);
		if (sentinelMaster == null) {
			sentinelMaster = new HashMap<String, ShardSentinelConfig>();
			map.put(sentinels, sentinelMaster);
		}
		for(ShardSentinelConfig shardSentinelConfig:config.getShardSentinelConfigLists()){
			String masterName = shardSentinelConfig.getMasterName();
			if(!sentinelMaster.containsKey(sentinelMaster)){
				sentinelMaster.put(masterName, shardSentinelConfig);
			}
		}
	}

	public void removeJedisSentinelConfigs(ShardJedisSentinelConfigs configs) {
		// TODO Auto-generated method stub

	}

	public void init(GenericObjectPoolConfig poolConfig){
		destroy();
		pool = new TinyShardedJedisSentinelPool(map, poolConfig);
	}
	
	public void destroy(){
		if(pool!=null){
			pool.destroy();
		}
	}
	
	public ShardedJedis getWriteShardedJedis() {
		return pool.getResource();
	}
	

	public ShardedJedis getReadShardedJedis() {
		return pool.getResource();
	}

	public Jedis getWriteJedis(String key) {
		// TODO Auto-generated method stub
		return pool.getResource().getShard(key);
	}

	public Jedis getReadJedis(String key) {
		// TODO Auto-generated method stub
		return pool.getResource().getReadShard(key);
	}

}

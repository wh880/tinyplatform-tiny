package org.tinygroup.jedis;

import org.tinygroup.jedis.shard.TinyShardJedis;

/**
 * JedisManager
 * 
 * @author yancheng11334
 * 
 */
public interface ShardJedisSentinelManager {

	void setFailOverTime(int failOverTime);
	
	TinyShardJedis getShardedJedis();

	void destroy();

	void returnResourceObject(TinyShardJedis resource);

	void returnResource(TinyShardJedis resource);

	void returnBrokenResource(TinyShardJedis resource);

}

package org.tinygroup.jedis.shard;

import java.util.List;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;

public class TinyShardReadJedis extends ShardedJedis{
	
	public TinyShardReadJedis(List<JedisShardInfo> shards) {
		super(shards);
	}

	public TinyShardReadJedis(List<JedisShardInfo> shards, Hashing algo,
			Pattern keyTagPattern) {
		super(shards, algo, keyTagPattern);
	}
	public Jedis getShard(String key) {
		JedisShardInfo j = getShardInfo(key);
	    return super.getShard(key);
	}
}

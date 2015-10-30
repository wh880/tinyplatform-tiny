package org.tinygroup.jedis.shard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.tinygroup.jedis.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;

public class TinyShardJedis extends ShardedJedis {

	Map<JedisShardInfo, List<Jedis>> readMap = new HashMap<JedisShardInfo, List<Jedis>>();

	/**
	 * 
	 * @param shards
	 *            列表中的实例必须是TinyJedisShardInfo
	 * @param algo
	 * @param keyTagPattern
	 */
	public TinyShardJedis(List<JedisShardInfo> shards, Hashing algo,
			Pattern keyTagPattern) {
		super(shards, algo, keyTagPattern);
		for (JedisShardInfo info : shards) {
			if (info instanceof TinyJedisShardInfo) {
				TinyJedisShardInfo tinyJedis = (TinyJedisShardInfo) info;
				readMap.put(tinyJedis, tinyJedis.createAllReadResource());
			} else {
				throw new RuntimeException(
						"TinyShardJedis构造函数传入的JedisShardInfo必须是TinyJedisShardInfo");
			}
		}

	}

	public Jedis getShard(String key) {
		return super.getShard(key);
	}

	public Jedis getReadShard(String key) {
		TinyJedisShardInfo info = (TinyJedisShardInfo) super.getShardInfo(key);
		List<Jedis> list = readMap.get(info);
		return JedisUtil.choose(list);
	}

	public void close() {
		super.close();
		for(List<Jedis> list:readMap.values()){
			for(Jedis jedis:list){
				jedis.close();
			}
		}

	}

}

package org.tinygroup.jedis.shard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.jedis.config.JedisConfig;
import org.tinygroup.jedis.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class TinyJedisShardInfo extends JedisShardInfo {
	List<JedisConfig> allRedisConfig = new ArrayList<JedisConfig>();
	Map<String, JedisShardInfo> map = new HashMap<String, JedisShardInfo>();

	
	/**
	 * host port是写服务器的,其他的是读服务器的
	 * @param host
	 * @param port
	 * @param timeout
	 * @param list
	 */
	public TinyJedisShardInfo(String host, int port, int timeout,
			List<JedisConfig> list) {
		super(host, port, timeout);
		for (JedisConfig config : list) {
			String simpleString = config.toSimpleString();
			if(simpleString.equals(JedisUtil.toSimpleString(host, port))){
				continue;
			}
			map.put(simpleString,
					JedisUtil.createJedisShardInfo(config));
		}
	}

	public Jedis createReadResource() {
		 return new Jedis(map.get("a"));
	}
	
	
	public List<Jedis> createAllReadResource(){
		List<Jedis> list =new ArrayList<Jedis>();
		for(JedisShardInfo info:map.values()){
			list.add(new Jedis(info));
		}
		return list;
	}
}

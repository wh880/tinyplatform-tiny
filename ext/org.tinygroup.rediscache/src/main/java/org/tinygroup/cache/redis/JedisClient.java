package org.tinygroup.cache.redis;

import org.tinygroup.cache.exception.CacheException;
import org.tinygroup.cache.redis.config.JedisConfig;
import org.tinygroup.commons.tools.StringUtil;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

public class JedisClient {

	private JedisConfig jedisConfig;
	
	private RedisCacheManager redisCacheManager;
	
	private String region;
	
	public JedisClient(String region){
		this.region = region;
	}

	public JedisConfig getJedisConfig() {
		return jedisConfig;
	}

	public void setJedisConfig(JedisConfig jedisConfig) {
		this.jedisConfig = jedisConfig;
	}

	public RedisCacheManager getRedisCacheManager() {
		return redisCacheManager;
	}

	public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}
	
	public JedisPool createJedisPool(){
		try{
			jedisConfig = redisCacheManager.getJedisManager().getJedisConfig(
					region);
			
			if(jedisConfig==null){
			   throw new NullPointerException(String.format("根据region:[%s]没有找到匹配的JedisConfig配置对象", region));	
			}

			// 设置默认参数
			String host = StringUtil.isBlank(jedisConfig.getHost()) ? Protocol.DEFAULT_HOST
					: jedisConfig.getHost();
			int port = jedisConfig.getPort() <= 0 ? Protocol.DEFAULT_PORT
					: jedisConfig.getPort();
			int timeout = jedisConfig.getTimeout() < 0 ? Protocol.DEFAULT_TIMEOUT
					: jedisConfig.getTimeout();
			int database = jedisConfig.getDatabase() < 0 ? Protocol.DEFAULT_DATABASE
					: jedisConfig.getDatabase();
			
			// 实例化jedis连接池
			JedisPool jedisPool = new JedisPool(redisCacheManager.getJedisManager()
					.getJedisPoolConfig(region), host, port, timeout,
					jedisConfig.getPassword(), database,
					jedisConfig.getClientName());
			
			return jedisPool;
		}catch(Exception e){
			throw new CacheException(e);
		}

	}
	
	public String getCharset(){
		return jedisConfig.getCharset()==null?"utf-8":jedisConfig.getCharset();
	}
}

package org.tinygroup.cache.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.cache.exception.CacheException;
import org.tinygroup.cache.redis.config.JedisConfig;
import org.tinygroup.cache.redis.util.SerializeUtil;
import org.tinygroup.commons.tools.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

/**
 * 基于redis的缓存实现方案
 * 
 * @author yancheng11334
 * 
 */
public class RedisCache implements Cache {

	private RedisCacheManager redisCacheManager;
	private JedisPool jedisPool;
	private JedisConfig jedisConfig;

	public void init(String region) {

		try {
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
			jedisPool = new JedisPool(redisCacheManager.getJedisManager()
					.getJedisPoolConfig(region), host, port, timeout,
					jedisConfig.getPassword(), database,
					jedisConfig.getClientName());
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	public Object get(String key) {
		checkJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] bkey = getByteKey(key);
			byte[] result = jedis.get(bkey);
			if(result==null){
			  return null;	
			}else{
			  return SerializeUtil.unserialize(result);
			}
			
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 注意：设置的对象必须实现序列化接口
	 */
	public void put(String key, Object object) {
		checkJedisPool();
		checkSerializable(object);
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] bkey = getByteKey(key);
			jedis.set(bkey, SerializeUtil.serialize(object));
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 注意：设置的对象必须实现序列化接口
	 */
	public void putSafe(String key, Object object) {
		if (key == null || object == null) {
			return;
		}
		put(key, object);
	}

	/**
	 * 注意：设置的对象必须实现序列化接口
	 */
	public void put(String groupName, String key, Object object) {
		checkJedisPool();
		checkSerializable(object);
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] bgroup = getByteKey(groupName);
			byte[] bkey = getByteKey(key);
			jedis.hset(bgroup, bkey, SerializeUtil.serialize(object));
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	public Object get(String groupName, String key) {
		checkJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] bgroup = getByteKey(groupName);
			byte[] bkey = getByteKey(key);
			byte[] result = jedis.hget(bgroup, bkey);
			if(result==null){
			  return null;	
			}else{
			  return SerializeUtil.unserialize(result);
			}
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	public Set<String> getGroupKeys(String group) {
		checkJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.hkeys(group);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	public void cleanGroup(String group) {
		checkJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(group);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	public void clear() {
		if(jedisPool!=null){
		   jedisPool.close();
		}
	}

	public void remove(String key) {
		checkJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	public void remove(String group, String key) {
		checkJedisPool();
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hdel(group, key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			closeJedis(jedis);
		}
	}

	public String getStats() {
		checkJedisPool();
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("JedisPool[");
			sb.append("numActive = ").append(jedisPool.getNumActive());
			sb.append(",numIdle = ").append(jedisPool.getNumIdle());
			sb.append(",numWaiters = ").append(jedisPool.getNumWaiters());
			sb.append("]");
			return sb.toString();
		} catch (Exception e) {
			throw new CacheException(e);
		} 
	}

	public int freeMemoryElements(int numberToFree) {
		throw new CacheException("RedisCache不支持freeMemoryElements方法.");
	}

	public void destroy() {
		if(jedisPool!=null){
		   jedisPool.destroy();
		}
	}

	public void setCacheManager(CacheManager manager) {
		if (!(manager instanceof RedisCacheManager)) {
			throw new CacheException("CacheManager不是RedisCacheManager的实例.");
		}
		redisCacheManager = (RedisCacheManager) manager;
	}

	private byte[] getByteKey(String key) {
		try {
			return key.getBytes(jedisConfig.getCharset()==null?"utf-8":jedisConfig.getCharset());
		} catch (UnsupportedEncodingException e) {
			throw new CacheException(e);
		}
	}

	private void checkJedisPool() {
		if (jedisPool == null || jedisPool.isClosed()) {
			throw new CacheException("JedisPool没有初始化或者已经关闭.");
		}
	}

	private void checkSerializable(Object object) {
		if (!(object instanceof Serializable)) {
			throw new CacheException("对象必须实现Serializable接口");
		}
	}

	private void closeJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

}

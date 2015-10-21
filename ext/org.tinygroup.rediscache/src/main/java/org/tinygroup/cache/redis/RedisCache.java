package org.tinygroup.cache.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.cache.exception.CacheException;
import org.tinygroup.cache.redis.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 基于redis的缓存实现方案
 * 
 * @author yancheng11334
 * 
 */
public class RedisCache implements Cache {

	private RedisCacheManager redisCacheManager;
	private JedisPool jedisPool;
	private JedisClient jedisClient;

	public void init(String region) {

		try {
			//目前tiny的框架机制不能保证初始化时，redis的配置文件一定能加载完成，init需要考虑这种场景
			//比如Session缓存配置这种场景：最高优先级的ConfigurationFileProcessor会调用cache的init接口，而此时JedisConfigsFileProcessor尚未完成加载。
			jedisClient = new JedisClient(region,redisCacheManager);
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
	
	/**
	 * 从jedis连接池获得Jedis客户端
	 * @return
	 */
	public Jedis getJedis(){
		checkJedisPool();
		return jedisPool.getResource();
	}
	
	/**
	 * 获得当前jedis的配置资源
	 * @return
	 */
	public JedisClient getJedisClient(){
		return jedisClient;
	}

	private byte[] getByteKey(String key) {
		try {
			return key.getBytes(jedisClient.getCharset());
		} catch (UnsupportedEncodingException e) {
			throw new CacheException(e);
		}
	}

	private void checkJedisPool() {
		if(jedisPool==null){
		   jedisPool = jedisClient.getJedisPool();
		}
		if (jedisPool.isClosed()) {
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

	public Object[] get(String[] keys) {
		List<Object> objs = new ArrayList<Object>();
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				objs.add(get(keys[i]));
			}
		}
		return objs.toArray();
	}

	public Object[] get(String group, String[] keys) {
		List<Object> objs = new ArrayList<Object>();
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				objs.add(get(group ,keys[i]));
			}
		}
		return objs.toArray();
	}

	public void remove(String[] keys) {
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				remove(keys[i]);
			}
		}
	}

	public void remove(String group, String[] keys) {
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				remove(group ,keys[i]);
			}
		}	
	}
	
}

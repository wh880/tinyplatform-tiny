package org.tinygroup.jedis.shard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.tinygroup.jedis.util.JedisUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Tuple;
import redis.clients.util.Hashing;

public class TinyShardJedis extends ShardedJedis {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(TinyShardJedis.class);
	private Map<JedisShardInfo, List<Jedis>> readMap = new HashMap<JedisShardInfo, List<Jedis>>();
	private Map<JedisShardInfo, List<Jedis>> failReadMap = new HashMap<JedisShardInfo, List<Jedis>>();
	private FailOverThread failTestThread = new FailOverThread();
	private boolean writeState = false;

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
		LOGGER.logMessage(LogLevel.DEBUG,
				"创建TinyShardJedis:JedisShardInfo长度为:{}", shards.size());
		for (JedisShardInfo info : shards) {
			if (info instanceof TinyJedisShardInfo) {
				TinyJedisShardInfo tinyJedis = (TinyJedisShardInfo) info;
				readMap.put(tinyJedis, tinyJedis.createAllReadResource());
			} else {
				throw new RuntimeException(
						"TinyShardJedis构造函数传入的JedisShardInfo必须是TinyJedisShardInfo");
			}
		}
		failTestThread.setDaemon(true);
		failTestThread.start();

	}

	public Jedis getShard(String key) {
		writeState = true;
		return super.getShard(key);
	}

	public Jedis getReadShard(String key) {
		if (writeState) {
			return getShard(key);
		}
		TinyJedisShardInfo info = (TinyJedisShardInfo) super.getShardInfo(key);
		List<Jedis> currentlist = readMap.get(info);
		List<Jedis> failList = new ArrayList<Jedis>();
		if (failReadMap.containsKey(info)) {
			failList = failReadMap.get(info);
		} else {
			failReadMap.put(info, failList);
		}
		List<Jedis> list = JedisUtil.newList(currentlist, failList);
		return JedisUtil.choose(list, failList);
	}

	public void resetWriteState() {
		writeState = false;
	}

	public Collection<Jedis> getAllReadShards() {
		List<Jedis> totalList = new ArrayList<Jedis>();
		for (List<Jedis> list : readMap.values()) {
			totalList.addAll(list);
		}
		return totalList;
	}

	public Map<JedisShardInfo, List<Jedis>> getReadShardsMap() {
		return readMap;
	}

	public Collection<Jedis> getAllShards() {
		return super.getAllShards();
	}

	// private List<Jedis> getValiableJedis(List<Jedis> list){
	// List<Jedis> newList = new ArrayList<Jedis>();
	// for(Jedis j:list){
	// try {
	// j.connect();
	// newList.add(j);
	// } catch (JedisConnectionException ex) {
	// LOGGER.logMessage(LogLevel.WARN, "jedis连接失败:{}",j.clientGetname());
	// }
	// }
	// return newList;
	// }

	public void close() {
		super.close();
		failTestThread.stopTry();
		for (List<Jedis> list : readMap.values()) {
			for (Jedis jedis : list) {
				jedis.close();
			}
		}
	}

	public String get(String key) {
		Jedis j = getReadShard(key);
		return j.get(key);
	}

	public String echo(String string) {
		Jedis j = getReadShard(string);
		return j.echo(string);
	}

	public Boolean exists(String key) {
		Jedis j = getReadShard(key);
		return j.exists(key);
	}

	public String type(String key) {
		Jedis j = getReadShard(key);
		return j.type(key);
	}

	public Long ttl(String key) {
		Jedis j = getReadShard(key);
		return j.ttl(key);
	}

	public Boolean getbit(String key, long offset) {
		Jedis j = getReadShard(key);
		return j.getbit(key, offset);
	}

	public String getrange(String key, long startOffset, long endOffset) {
		Jedis j = getReadShard(key);
		return j.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) {
		Jedis j = getReadShard(key);
		return j.getSet(key, value);
	}

	// TODO:check
	public List<String> blpop(String arg) {
		Jedis j = getReadShard(arg);
		return j.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) {
		Jedis j = getReadShard(key);
		return j.blpop(timeout, key);
	}

	public List<String> brpop(String arg) {
		Jedis j = getReadShard(arg);
		return j.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) {
		Jedis j = getReadShard(key);
		return j.brpop(timeout, key);
	}

	public String substr(String key, int start, int end) {
		Jedis j = getReadShard(key);
		return j.substr(key, start, end);
	}

	public String hget(String key, String field) {
		Jedis j = getReadShard(key);
		return j.hget(key, field);
	}

	public List<String> hmget(String key, String... fields) {
		Jedis j = getReadShard(key);
		return j.hmget(key, fields);
	}

	public Boolean hexists(String key, String field) {
		Jedis j = getReadShard(key);
		return j.hexists(key, field);
	}

	public Long hlen(String key) {
		Jedis j = getReadShard(key);
		return j.hlen(key);
	}

	public Set<String> hkeys(String key) {
		Jedis j = getReadShard(key);
		return j.hkeys(key);
	}

	public List<String> hvals(String key) {
		Jedis j = getReadShard(key);
		return j.hvals(key);
	}

	public Map<String, String> hgetAll(String key) {
		Jedis j = getReadShard(key);
		return j.hgetAll(key);
	}

	public Long strlen(final String key) {
		Jedis j = getReadShard(key);
		return j.strlen(key);
	}

	public Long llen(String key) {
		Jedis j = getReadShard(key);
		return j.llen(key);
	}

	public List<String> lrange(String key, long start, long end) {
		Jedis j = getReadShard(key);
		return j.lrange(key, start, end);
	}

	// check
	// ltrim(String key, long start, long end)
	public String lindex(String key, long index) {
		Jedis j = getReadShard(key);
		return j.lindex(key, index);
	}

	public Set<String> smembers(String key) {
		Jedis j = getReadShard(key);
		return j.smembers(key);
	}

	public Long scard(String key) {
		Jedis j = getReadShard(key);
		return j.scard(key);
	}

	public Boolean sismember(String key, String member) {
		Jedis j = getReadShard(key);
		return j.sismember(key, member);
	}

	public String srandmember(String key) {
		Jedis j = getReadShard(key);
		return j.srandmember(key);
	}

	public List<String> srandmember(String key, int count) {
		Jedis j = getReadShard(key);
		return j.srandmember(key, count);
	}

	public Set<String> zrange(String key, long start, long end) {
		Jedis j = getReadShard(key);
		return j.zrange(key, start, end);
	}

	public Long zrank(String key, String member) {
		Jedis j = getReadShard(key);
		return j.zrank(key, member);
	}

	public Long zrevrank(String key, String member) {
		Jedis j = getReadShard(key);
		return j.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) {
		Jedis j = getReadShard(key);
		return j.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Jedis j = getReadShard(key);
		return j.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Jedis j = getReadShard(key);
		return j.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) {
		Jedis j = getReadShard(key);
		return j.zcard(key);
	}

	public Double zscore(String key, String member) {
		Jedis j = getReadShard(key);
		return j.zscore(key, member);
	}

	public Long zcount(String key, double min, double max) {
		Jedis j = getReadShard(key);
		return j.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) {
		Jedis j = getReadShard(key);
		return j.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis j = getReadShard(key);
		return j.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Jedis j = getReadShard(key);
		return j.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		Jedis j = getReadShard(key);
		return j.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		Jedis j = getReadShard(key);
		return j.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		Jedis j = getReadShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zlexcount(final String key, final String min, final String max) {
		return getReadShard(key).zlexcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min,
			final String max) {
		return getReadShard(key).zrangeByLex(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min,
			final String max, final int offset, final int count) {
		return getReadShard(key).zrangeByLex(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		return getReadShard(key).zrevrangeByLex(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min,
			int offset, int count) {
		return getReadShard(key).zrevrangeByLex(key, max, min, offset, count);
	}

	public Long bitcount(final String key) {
		Jedis j = getReadShard(key);
		return j.bitcount(key);
	}

	public Long bitcount(final String key, long start, long end) {
		Jedis j = getReadShard(key);
		return j.bitcount(key, start, end);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		Jedis j = getReadShard(key);
		return j.hscan(key, cursor);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<String> sscan(String key, int cursor) {
		Jedis j = getReadShard(key);
		return j.sscan(key, cursor);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<Tuple> zscan(String key, int cursor) {
		Jedis j = getReadShard(key);
		return j.zscan(key, cursor);
	}

	public ScanResult<Entry<String, String>> hscan(String key,
			final String cursor) {
		Jedis j = getReadShard(key);
		return j.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, final String cursor) {
		Jedis j = getReadShard(key);
		return j.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, final String cursor) {
		Jedis j = getReadShard(key);
		return j.zscan(key, cursor);
	}

	public long pfcount(String key) {
		Jedis j = getReadShard(key);
		return j.pfcount(key);
	}

	public void resetState() {
		super.resetState();
	}

	public Set<String> keys(String keyPattern) {
		Set<String> set = null;
		for (JedisShardInfo info : readMap.keySet()) {
			List<Jedis> list = readMap.get(info);
			List<Jedis> failList = failReadMap.get(info);
			Jedis jedis = JedisUtil.choose(list, failList);
			if (set == null) {
				set = jedis.keys(keyPattern);
			} else {
				set.addAll(jedis.keys(keyPattern));
			}
		}
		return set;
	}

	public void flushAll() {
		Collection<Jedis> jedisSet = getAllShards();
		for (Jedis jedis : jedisSet) {
			jedis.flushAll();
		}
	}

	public void flushDB() {
		Collection<Jedis> jedisSet = getAllShards();
		for (Jedis jedis : jedisSet) {
			jedis.flushDB();
		}
	}

	public int deleteMatchKey(String keyPattern) {
		Set<String> keySet = this.keys(keyPattern);
		int delCount = 0;
		if (keySet != null && keySet.size() > 0) {
			for (String key : keySet) {
				delCount += this.del(key);
			}
		}
		return delCount;
	}

	/**
	 * 轮询失败的服务器
	 * 
	 */
	class FailOverThread extends Thread {
		boolean stop = false;
		
		public void stopTry(){
			stop = true;
		}
		public void run() {
			while (!stop) {
				try {
					sleep(JedisUtil.getFailOverTime());
				} catch (Exception e) {
				}
				testFailJedisMap();
			}
		}
	}

	private void testFailJedisMap() {
		LOGGER.logMessage(LogLevel.DEBUG, "开始轮询连接失败的服务器列表");
		for (List<Jedis> list : failReadMap.values()) {
			List<Jedis> newList = JedisUtil.copy(list);
			for (Jedis j : newList) {
				LOGGER.logMessage(LogLevel.DEBUG, "开始尝试连接的服务器:{}:{}", j
						.getClient().getHost(), j.getClient().getPort());
				boolean sucess = testFailJedis(j);
				if (sucess) {
					LOGGER.logMessage(LogLevel.DEBUG, "连接成功,从fail列表中删除");
					list.remove(j);
				}
				LOGGER.logMessage(LogLevel.DEBUG, "连接服务器:{}:{}结束", j
						.getClient().getHost(), j.getClient().getPort());
			}
		}
		LOGGER.logMessage(LogLevel.DEBUG, "轮询连接失败的服务器列表结束");
	}

	private boolean testFailJedis(Jedis j) {

		try {
			j.disconnect();
			j.connect();
			if (j.ping().equals("PONG")) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
}

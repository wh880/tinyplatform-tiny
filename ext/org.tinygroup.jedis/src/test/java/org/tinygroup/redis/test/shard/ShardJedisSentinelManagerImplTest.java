package org.tinygroup.redis.test.shard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;

public class ShardJedisSentinelManagerImplTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory.getManager();
		for(int i = 0 ; i < 1000 ; i ++){
			// ===================
			test(manager, "ShardJedisSentinelManagerImplTest"+i+i+i);
			// ===================
		}
		for(int i = 0 ; i < 1000 ; i ++){
			// ===================
			testReadAndWrite(manager, "WriteAndRead"+i+i+i);
			// ===================
		}
		testReadMethod(manager.getShardedJedis());
		manager.destroy();

	}

	private static void test(ShardJedisSentinelManager manager, String key) {
		System.out.println("===================");
		//直接通过TinyShardJedis操作的话，实际是对主从数据库中的主服务器进行操作(即对写服务器进行操作)
		TinyShardJedis jedis = manager.getShardedJedis();
		TinyShardJedis jedis2 = manager.getShardedJedis();
		jedis.del(key);
		jedis.set(key, key);
		System.out.println(jedis.get(key));
		System.out.println("manager.getShardedJedis():" + jedis2);
		Assert.assertEquals(key, jedis.get(key));
		manager.returnResource(jedis);
		manager.returnResource(jedis2);
		System.out.println("===================");
	}

	private static void testReadMethod(TinyShardJedis jedis){
		jedis.del("a1");
		jedis.del("a2");
		jedis.del("a3");
		jedis.hset("a2", "111","222");
		jedis.hdel("a2", "111","222");
		jedis.hdel("a3", "111","222");
		jedis.del("alist");
		
		jedis.set("a1", "ABCDEFG");
		jedis.hset("a3", "111", "ABC");
		jedis.hset("a3", "222", "DEFG");
		jedis.hset("a2", "111", "ABC");
		jedis.hset("a2", "222", "DEFG");
		
		jedis.rpush("alist", "A","B","C","D","E","F","G");
		
		jedis.sadd("s1", "AAA");
		jedis.sadd("s2", "AAA");
		jedis.sadd("s2", "BBB");
		
		jedis.zadd("z1", 1, "Z3");
		jedis.zadd("z1", 3, "Z2");
		jedis.zadd("z1", 5, "Z1");
		
		jedis.del("a999");
		jedis.resetWriteState();
		
		testget(jedis);
		testEcho(jedis);
		testExists(jedis);
		testGetKeyTag(jedis);
		testGetrange(jedis);
		testLlen(jedis);
		testHexists(jedis);
		testHGet(jedis);
		testHGetAll(jedis);
		testHKeys(jedis);
		testHMGet(jedis);
		testHvals(jedis);
		testLindex(jedis);
		testLrange(jedis);
		testScard(jedis);
		testSismember(jedis);
		testSmembers(jedis);
		testSrandmember(jedis);
		testSort(jedis);
		testStrlen(jedis);
		testSubstr(jedis);
		testTtl(jedis);
		testZcard(jedis);
		testZcount(jedis);
		testZlexcount(jedis);
		testZrange(jedis);
		testZrangeByLex(jedis);
		testZrangeByScore(jedis);
		testZrank(jedis);
		testZscore(jedis);
	}
	
	private static void testget(TinyShardJedis jedis) {
		Assert.assertEquals("ABCDEFG", jedis.getReadShard("a1").get("a1"));
	}
	
	private static void testEcho(TinyShardJedis jedis) {
		Assert.assertEquals("a1", jedis.getReadShard("a1").echo("a1"));
	}
	
	private static void testExists(TinyShardJedis jedis) {
		Assert.assertTrue(jedis.exists("a1"));
		Assert.assertFalse(jedis.exists("a999"));
	}
	
	private static void testGetKeyTag(TinyShardJedis jedis) {
		Assert.assertEquals("a1" ,jedis.getKeyTag("a1"));
	}
	
	private static void testGetrange(TinyShardJedis jedis){
		Assert.assertEquals("DEF" ,jedis.getReadShard("a1").getrange("a1", 3, 5));
		Assert.assertEquals("DEFG" ,jedis.getReadShard("a1").getrange("a1", 3, 10));
		Assert.assertEquals("ABCDEFG" ,jedis.getReadShard("a1").getrange("a1", -99, 99));
		Assert.assertEquals("ABCDEFG" ,jedis.getReadShard("a1").get("a1"));
	}
	
	private static void testLlen(TinyShardJedis jedis){
		Assert.assertEquals(new Long(7) ,jedis.getReadShard("alist").llen("alist"));
	}
	
	private static void testHexists(TinyShardJedis jedis){
		Assert.assertTrue(jedis.getReadShard("a2").hexists("a2", "111"));
		Assert.assertTrue(jedis.getReadShard("a3").hexists("a3", "222"));
	}
	
	private static void testHGet(TinyShardJedis jedis){
		Assert.assertEquals("ABC", jedis.getReadShard("a2").hget("a2", "111"));
	}
	
	private static void testHGetAll(TinyShardJedis jedis){
		Assert.assertEquals("ABC", jedis.getReadShard("a2").hgetAll("a2").get("111"));
		Assert.assertEquals("DEFG", jedis.getReadShard("a2").hgetAll("a2").get("222"));
	}
	
	private static void testHKeys(TinyShardJedis jedis){
		Assert.assertEquals(2, jedis.getReadShard("a2").hkeys("a2").size());
	}
	
	private static void testHMGet(TinyShardJedis jedis){
		List<String> values = jedis.getReadShard("a2").hmget("a2" ,"111" ,"222");
		Assert.assertEquals("ABC", values.get(0));
		Assert.assertEquals("DEFG", values.get(1));
	}
	
	private static void testHvals(TinyShardJedis jedis){
		List<String> values = jedis.getReadShard("a2").hvals("a2");
		Assert.assertEquals("ABC", values.get(0));
		Assert.assertEquals("DEFG", values.get(1));
	}
	
	private static void testLindex(TinyShardJedis jedis){
		Assert.assertEquals("A", jedis.getReadShard("alist").lindex("alist", 0));
	}
	
	private static void testLrange(TinyShardJedis jedis){
		List<String> values = jedis.getReadShard("alist").lrange("alist", 3, 5);
		Assert.assertEquals("D", values.get(0));
		Assert.assertEquals("E", values.get(1));
	}
	
	private static void testScard(TinyShardJedis jedis){
		Assert.assertEquals(new Long(1),jedis.getReadShard("s1").scard("s1"));
		Assert.assertEquals(new Long(2),jedis.getReadShard("s2").scard("s2"));
	}
	
	private static void testSismember(TinyShardJedis jedis){
		Assert.assertTrue(jedis.getReadShard("s1").sismember("s1" ,"AAA"));
		Assert.assertFalse(jedis.getReadShard("s2").sismember("s2" ,"CCC"));
	}
	
	private static void testSmembers(TinyShardJedis jedis){
		Set<String> values = jedis.getReadShard("s2").smembers("s2");
		Assert.assertEquals("AAA", values.toArray()[1]);
		Assert.assertEquals("BBB", values.toArray()[0]);
	}
	
	private static void testSrandmember(TinyShardJedis jedis){
		Assert.assertEquals("AAA", jedis.getReadShard("s1").srandmember("s1"));
	}
	
	private static void testSort(TinyShardJedis jedis){
//		List<String> values = jedis.getReadShard("alist").sort("alist");
//		Assert.assertEquals("A", values.toArray()[0]);
	}
	
	private static void testStrlen(TinyShardJedis jedis){
		Assert.assertEquals(new Long(7), jedis.getReadShard("a1").strlen("a1"));
	}
	
	private static void testSubstr(TinyShardJedis jedis){
		Assert.assertEquals("DEF", jedis.getReadShard("a1").substr("a1", 3, 5));
		Assert.assertEquals("ABCDEFG", jedis.getReadShard("a1").get("a1"));
	}
	
	private static void testTtl(TinyShardJedis jedis){
		Assert.assertEquals(new Long(-1), jedis.getReadShard("a1").ttl("a1"));
	}
	
	private static void testZcard(TinyShardJedis jedis){
		Assert.assertEquals(new Long(3), jedis.getReadShard("z1").zcard("z1"));
	}
	
	private static void testZcount(TinyShardJedis jedis){
		Assert.assertEquals(new Long(2), jedis.getReadShard("z1").zcount("z1", 2, 6));
	}
	
	private static void testZlexcount(TinyShardJedis jedis){
		Assert.assertEquals(new Long(3), jedis.getReadShard("z1").zlexcount("z1", "-", "+"));
	}
	
	private static void testZrange(TinyShardJedis jedis){
		Set<String> values = jedis.getReadShard("z1").zrange("z1", 0, 2);
		Assert.assertEquals("Z3", values.toArray()[0]);
		Assert.assertEquals("Z2", values.toArray()[1]);
	}
	
	private static void testZrangeByLex(TinyShardJedis jedis){
		Set<String> values = jedis.getReadShard("z1").zrangeByLex("z1", "-", "+");
		Assert.assertEquals("Z3", values.toArray()[0]);
		Assert.assertEquals("Z2", values.toArray()[1]);
	}
	
	private static void testZrangeByScore(TinyShardJedis jedis){
		Set<String> values = jedis.getReadShard("z1").zrangeByScore("z1", "1", "5");
		Assert.assertEquals("Z3", values.toArray()[0]);
		Assert.assertEquals("Z2", values.toArray()[1]);
	}
	
	private static void testZrank(TinyShardJedis jedis){
		Assert.assertEquals(new Long(2), jedis.getReadShard("z1").zrank("z1", "Z1"));
	}
	
	private static void testZscore(TinyShardJedis jedis){
		Assert.assertEquals(5d, jedis.getReadShard("z1").zscore("z1" ,"Z1"));
	}
	
	private static void testReadAndWrite(ShardJedisSentinelManager manager,
			String key) {
		System.out.println("===================");
		TinyShardJedis shardedJedis = manager.getShardedJedis();
		//shardedJedis.getShard获得的链接操作数据和直接通过TinyShardJedis是一样
		Jedis write = shardedJedis.getShard(key);
		//只有这个种情况，是对读服务器进行服务器进行操作
		Jedis read = shardedJedis.getReadShard(key);
		System.out.println(write);
		System.out.println(read);
		Assert.assertSame(write, read);
		manager.returnResource(shardedJedis);
		System.out.println("===================");
	}
}

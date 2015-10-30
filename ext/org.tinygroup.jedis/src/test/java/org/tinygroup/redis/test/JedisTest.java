package org.tinygroup.redis.test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {
//		Jedis j = new Jedis("127.0.0.1", 1111);
//		System.out.println(j.asking());
//		j.connect();
//		System.out.println(j);
//		System.out.println(j.isConnected());
		Jedis j2 = new Jedis("192.168.51.29", 11111);
		j2.get("a");
		j2.connect();
		System.out.println(j2);
		System.out.println(j2.isConnected());
	}
}

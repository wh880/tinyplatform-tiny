package org.tinygroup.redis.test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {
		Jedis j2 = new Jedis("192.168.51.29", 11111);
		j2.auth(IJedisConstant.PASSWOPD);
		j2.get("a");
		j2.connect();
		System.out.println(j2);
		System.out.println(j2.isConnected());
	}
}

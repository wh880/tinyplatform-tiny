package org.tinygroup.redis.test;

import redis.clients.jedis.Jedis;

public class ConnectTest {
	public static void main(String[] args) {
		Jedis j = new Jedis("192.168.51.29", 11112);
		int times = 1000;
		for (int i = 0; i < times; i++) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			try {

				j.connect();
				System.out.println("连接成功");
			} catch (Exception e) {
				System.out.println("连接失败");
			}
			long time = System.currentTimeMillis();
			try {
				System.out.println("--"+j.ping());
				if(j.ping().equals("PONG")){
					System.out.println("获取成功");
					System.out.println((System.currentTimeMillis() - time));
				}else{
					System.out.println((System.currentTimeMillis() - time));
					System.out.println("获取失败");
					j.disconnect();
				}
			} catch (Exception e) {
				System.out.println((System.currentTimeMillis() - time));
				System.out.println("获取失败");
				j.disconnect();
			}
		}

	}
}

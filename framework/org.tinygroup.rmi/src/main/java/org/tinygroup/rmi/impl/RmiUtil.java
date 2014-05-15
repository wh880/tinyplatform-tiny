package org.tinygroup.rmi.impl;

public class RmiUtil {
	public static String getName(String name, String id) {
		return name + "|" + id;
	}

	public static void start(Runnable r) {
		Thread t = new Thread(r);
		t.start();
	}
}

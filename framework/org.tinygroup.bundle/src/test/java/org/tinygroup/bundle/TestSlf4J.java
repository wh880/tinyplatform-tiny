package org.tinygroup.bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlf4J {
	static Logger loger = LoggerFactory.getLogger(TestSlf4J.class);

	public static void main(String[] args) {
		loger.debug("haha");
		loger.error("aaa");
	}
}

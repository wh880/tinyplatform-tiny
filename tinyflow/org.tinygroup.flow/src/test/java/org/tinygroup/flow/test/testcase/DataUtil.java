package org.tinygroup.flow.test.testcase;

public class DataUtil {
	public static final int defaultValue = 0;
	private static int i = defaultValue;
	
	public static int defaultValue(){
		return defaultValue;
	}
	
	public static void plus(int j) {
		i = i+j;
	}

	public static void imsub(int j) {
		i = i-j;
	}

	public static int getData() {
		return i;
	}

	public static void reset() {
		i = 0;
	}
}

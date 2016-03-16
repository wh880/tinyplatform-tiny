package org.tinygroup.servicehttpchannel.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestMethod {
	public static void main(String[] args) {
		try {
			Method m = TestMethod.class.getMethod("test",null);
			System.out.println(m.getReturnType());
			System.out.println(m.getGenericReturnType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Method m = TestMethod.class.getMethod("test2",null);
			System.out.println(m.getReturnType());
			System.out.println(m.getGenericReturnType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> test(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		return list;
	}
	
	public void test2(){
	}
}

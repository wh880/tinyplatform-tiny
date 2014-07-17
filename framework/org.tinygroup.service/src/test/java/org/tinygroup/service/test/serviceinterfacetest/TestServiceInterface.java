package org.tinygroup.service.test.serviceinterfacetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.service.test.serviceinterfacetest.service.User;
import org.tinygroup.service.util.ServiceTestUtil;

import junit.framework.TestCase;

public class TestServiceInterface  extends TestCase{
	
	
	public void testVoid(){
		Context context = new ContextImpl();
		context.put("name", "testuser");
		ServiceTestUtil.execute("VoidInterfaceImpl2", context);
	}
	
	public void testVoidString(){
		Context context = new ContextImpl();
		context.put("firstName", "ren");
		context.put("secondName", "hui");
		ServiceTestUtil.execute("VoidInterfaceImpl", context);
		assertEquals("ren_hui", context.get("result"));
	}
	
	public void testList(){
		Context context = new ContextImpl();
		List<String> list=new ArrayList<String>();
		list.add("123");
		list.add("456");
		context.put("list", list);
		context.put("name", "789");
		ServiceTestUtil.execute("TestList", context);
		List<String> resultList=context.get("result");
		assertEquals(3,resultList.size());
		
	}
	public void testMap(){
		Context context = new ContextImpl();
		Map<String, String> map=new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		context.put("map", map);
		context.put("name", "key3");
		context.put("value", "value3");
		ServiceTestUtil.execute("TestMap", context);
		Map<String, String>  resultMap=context.get("result");
		assertEquals(3,resultMap.size());
	}
	
	public void testUser(){
		Context context = new ContextImpl();
		context.put("name", "renhui");
		context.put("age", 30);
		context.put("sex", true);
		ServiceTestUtil.execute("UserInterfaceImpl", context);
		User user=context.get("result");
		assertEquals("renhui", user.getName());
	}
	
	public void testAbstract(){
		Context context = new ContextImpl();
		context.put("name", "renhui");
		context.put("age", 30);
		context.put("sex", true);
		try {
			ServiceTestUtil.execute("AbstractUserInterface", context);
		} catch (Exception e) {
			//执行的服务找不到,抛出异常认为是正确的
			return;
		}
		fail();//不能执行到这里
	}
	
}

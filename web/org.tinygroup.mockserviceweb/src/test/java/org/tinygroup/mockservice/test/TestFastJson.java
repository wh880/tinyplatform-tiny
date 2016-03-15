package org.tinygroup.mockservice.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TestFastJson {
	public static void main(String[] args) {
		testSingle();
		testList();
	}
	private static void testList() {
		String s = JSON.toJSONString(getObjList(),
				SerializerFeature.DisableCircularReferenceDetect);
		try {
			Method m = TestFastJson.class.getMethod("test",null);
			List<Obj> list = JSON.parseObject(s, m.getGenericReturnType());
			for(Obj obj:list){
				assertSingle(obj);
			}
		} catch (Exception e) {
			Assert.fail();
		}
	}
	private static void testSingle() {
		String s = JSON.toJSONString(getObj(),
				SerializerFeature.DisableCircularReferenceDetect);
		Obj object = JSON.parseObject(s, Obj.class);
		assertSingle(object);
	}
	private static void assertSingle(Obj object) {
		Assert.assertEquals(3, object.getList2().size());
		Assert.assertEquals(3, object.getArray2().length);
		Assert.assertEquals(2, object.getMap2().size());
		Assert.assertEquals(4, object.getList1().size());
	}
	
	public static Obj getObj(){
		Obj obj  = new Obj();
		
		Obj2 obj2A = new Obj2(1,"a");
		Obj2 obj2B = new Obj2(2,"b");
		Obj2 obj2C = new Obj2(3,"c");
		obj.getList2().add(obj2A);
		obj.getList2().add(obj2B);
		obj.getList2().add(obj2C);
		
		Obj2 obj2D = new Obj2(4,"d");
		Obj2 obj2E = new Obj2(5,"e");
		Obj2 obj2F = new Obj2(6,"f");
		Obj2[] array  = new Obj2[]{obj2D,obj2E,obj2F};
		obj.setArray2(array);
		
		Obj2 obj2G = new Obj2(7,"g");
		Obj2 obj2H = new Obj2(8,"h");
		obj.getMap2().put(obj2G.getName(), obj2G);
		obj.getMap2().put(obj2H.getName(), obj2H);
		
		Obj1 obj1A = new Obj1("a", "a1", 1);
		Map<String, String> map1  = new HashMap<String, String>();
		map1.put("a11", "a11");
		map1.put("a12", "a12");
		obj1A.setMap(map1);
		
		Obj1 obj1B = new Obj1("b", "b1", 2);
		Map<String, String> map2  = new HashMap<String, String>();
		map2.put("a21", "a21");
		map2.put("a22", "a22");
		obj1B.setMap(map2);
		
		Obj1 obj1C = new Obj1("c", "c1", 3);
		Map<String, String> map3  = new HashMap<String, String>();
		map3.put("a31", "a31");
		map3.put("a32", "a32");
		obj1C.setMap(map3);
		
		Obj1 obj1D = new Obj1("d", "d1", 4);
		Map<String, String> map4  = new HashMap<String, String>();
		map4.put("a41", "a41");
		map4.put("a42", "a42");
		obj1D.setMap(map4);
		
		obj.getList1().add(obj1A);
		obj.getList1().add(obj1B);
		obj.getList1().add(obj1C);
		obj.getList1().add(obj1D);
		
		return obj;
	}
	
	public List<Obj> test(){
		List<Obj> list = new ArrayList<Obj>();
		list.add(getObj());
		list.add(getObj());
		return list;
	}
	
	public static List<Obj> getObjList(){
		List<Obj> list = new ArrayList<Obj>();
		list.add(getObj());
		list.add(getObj());
		return list;
	}

}

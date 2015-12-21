/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.ObjectContainSimpleObjectArray;
import org.tinygroup.context2object.test.generator2.config.SimpleObject;


public class TestArrayObject extends BaseTestCast2{

//	public void testCollection1(){
//		Context context = new ContextImpl();
//		String[] names = new String[]{"a","b","c","d"};
//		Integer[] lengths = new Integer[]{new Integer(1) ,new Integer(2) ,new Integer(3) ,new Integer(4) };
//		boolean[] flags = new boolean[]{true , false , false,true};
//		int[] length2s = new int[]{5 ,6 ,7, 8};
//		context.put("name", names);
//		context.put("length", lengths);
//		context.put("flag", flags);
//		context.put("length2", length2s);
//		SimpleObject[] simpleObjects = (SimpleObject[]) generator.getObjectArray(null, SimpleObject.class.getName(), this.getClass().getClassLoader(), context);
//		
//		assertNotNull(simpleObjects);
//		
//		assertTrue(simpleObjects.length == 4);
//		
//		assertEquals("a", simpleObjects[0].getName());
//		assertEquals("b", simpleObjects[1].getName());
//		assertEquals("c", simpleObjects[2].getName());
//		assertEquals("d", simpleObjects[3].getName());
//		
//		assertEquals(new Integer("1"), simpleObjects[0].getLength());
//		assertEquals(new Integer("2"), simpleObjects[1].getLength());
//		assertEquals(new Integer("3"), simpleObjects[2].getLength());
//		assertEquals(new Integer("4"), simpleObjects[3].getLength());
//		
//		assertTrue(simpleObjects[0].getFlag());
//		assertFalse(simpleObjects[1].getFlag());
//		assertFalse(simpleObjects[2].getFlag());
//		assertTrue(simpleObjects[3].getFlag());
//		
//		assertTrue(5 == simpleObjects[0].getLength());
//		assertTrue(6 == simpleObjects[1].getLength());
//		assertTrue(7 == simpleObjects[2].getLength());
//		assertTrue(8 == simpleObjects[3].getLength());
//	}
	
}

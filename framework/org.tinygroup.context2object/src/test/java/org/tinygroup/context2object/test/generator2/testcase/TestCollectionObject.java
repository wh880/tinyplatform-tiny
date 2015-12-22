/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import java.util.Collection;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.SimpleObject;


public class TestCollectionObject extends BaseTestCast2{

	public void testCollection1(){
		Context context = new ContextImpl();
		String[] names = new String[]{"a","b","c","d"};
		Integer[] lengths = new Integer[]{new Integer(1) ,new Integer(2) ,new Integer(3) ,new Integer(4) };
		Boolean[] flags = new Boolean[]{true , false , false,true};
		Integer[] length2s = new Integer[]{5 ,6 ,7, 8};
		context.put("name", names);
		context.put("length", lengths);
		context.put("flag", flags);
		context.put("length2", length2s);
		Collection<SimpleObject> simpleObjects = generator.getObjectCollection(null, List.class.getName(), SimpleObject.class.getName(), this.getClass().getClassLoader(), context);
		
		assertNotNull(simpleObjects);
		
		assertTrue(simpleObjects.size() == 4);
		SimpleObject[] objs = simpleObjects.toArray(new SimpleObject[0]);
		
		assertEquals("a", objs[0].getName());
		assertEquals("b", objs[1].getName());
		assertEquals("c", objs[2].getName());
		assertEquals("d", objs[3].getName());
		
		assertEquals(new Integer("1"), objs[0].getLength());
		assertEquals(new Integer("2"), objs[1].getLength());
		assertEquals(new Integer("3"), objs[2].getLength());
		assertEquals(new Integer("4"), objs[3].getLength());
		
		assertTrue(objs[0].getFlag());
		assertFalse(objs[1].getFlag());
		assertFalse(objs[2].getFlag());
		assertTrue(objs[3].getFlag());
		
		assertTrue(5 == objs[0].getLength2());
		assertTrue(6 == objs[1].getLength2());
		assertTrue(7 == objs[2].getLength2());
		assertTrue(8 == objs[3].getLength2());
	}
	
}

/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.SimpleListArrayObject;

/**
 * @author Administrator
 *
 */
public class TestSimpleListArrayObject extends BaseTestCast2 {

//	public void testCollection1(){
//		Context context = new ContextImpl();
//		String[] names = new String[]{"a","b"};
//		Integer[] ints = new Integer[]{new Integer(1) ,new Integer(2)};
//		Boolean[] flagArray = new Boolean[]{Boolean.TRUE ,Boolean.FALSE};
//		List<String> nameLists = new ArrayList<String>();
//		nameLists.add("a");
//		nameLists.add("b");
//		context.put("nameArray", names);
//		context.put("nameList", nameLists);
//		context.put("lengthArray", ints);
//		context.put("flagArray", flagArray);
//		
//		SimpleListArrayObject simpleObjects = (SimpleListArrayObject) generator.getObject(null,null , SimpleListArrayObject.class.getName(), this.getClass().getClassLoader(), context);
//		
//		assertNotNull(simpleObjects);
//		
//		assertTrue(simpleObjects.getNameArray().length == 2);
//		assertTrue(simpleObjects.getNameList().size() == 2);
//		assertTrue(simpleObjects.getLengthArray().length == 2);
//		assertTrue(simpleObjects.getFlagArray().length == 2);
//		
//		
//		assertEquals("a", simpleObjects.getNameArray()[0]);
//		assertEquals("b", simpleObjects.getNameArray()[1]);
//		assertEquals("a", simpleObjects.getNameList().get(0));
//		assertEquals("b", simpleObjects.getNameList().get(1));
//		
//		assertEquals(new Integer(1), simpleObjects.getLengthArray()[0]);
//		assertEquals(new Integer(2), simpleObjects.getLengthArray()[1]);
//		
//		assertEquals(Boolean.TRUE, simpleObjects.getFlagArray()[0]);
//		assertEquals(Boolean.FALSE, simpleObjects.getFlagArray()[1]);
//		
//	}
	
}

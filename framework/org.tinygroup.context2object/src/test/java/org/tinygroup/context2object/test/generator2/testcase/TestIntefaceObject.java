/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.IntefaceObject;


/**
 * @author Administrator
 *
 */
public class TestIntefaceObject extends BaseTestCast2 {

	public void testArray(){
		Context context = new ContextImpl();
		String[] nameLists = new String[]{"a","b","c"};
		String[] nameArrays = new String[]{"q","w","e"};
		context.put("cats.name", nameLists);
		context.put("catsArray.name", nameArrays);
		context.put("cat.name", "a");
		context.put("name", "z");
		
		IntefaceObject obj = (IntefaceObject) generator.getObject(null, null, IntefaceObject.class.getName(), this.getClass().getClassLoader(), context);
		
		assertTrue(3 == obj.getCats().size());
		assertEquals("a", obj.getCats().get(0).getName());
		assertEquals("b", obj.getCats().get(1).getName());
		assertEquals("c", obj.getCats().get(2).getName());
		
		assertTrue(3 == obj.getCatsArray().length);
		assertEquals("q", obj.getCatsArray()[0].getName());
		assertEquals("w", obj.getCatsArray()[1].getName());
		assertEquals("e", obj.getCatsArray()[2].getName());
		
		assertEquals("a", obj.getCat().getName());
		assertEquals("z", obj.getName());
	}
	
}

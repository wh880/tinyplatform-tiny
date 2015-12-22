/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.ObjectContainSimpleObjectList;

/**
 * @author Administrator
 *
 */
public class TestObjectContainSimpleObjectList extends BaseTestCast2 {

	public void testCollection(){
		Context context = new ContextImpl();
		String[] names = new String[]{"a","b","c","d"};
		context.put("simpleObjectList.name", names);
		context.put("simpleObjectList.flag", new String[]{"true","false","true","false"});
		ObjectContainSimpleObjectList object = (ObjectContainSimpleObjectList) generator.getObject(null, null, ObjectContainSimpleObjectList.class.getName(), this.getClass().getClassLoader(), context);
	
		assertNotNull(object);
		assertTrue(object.getSimpleObjectList().size() == 4);
		assertEquals("a", object.getSimpleObjectList().get(0).getName());
		assertEquals("b", object.getSimpleObjectList().get(1).getName());
		assertEquals("c", object.getSimpleObjectList().get(2).getName());
		assertEquals("d", object.getSimpleObjectList().get(3).getName());
		assertEquals(Boolean.TRUE, object.getSimpleObjectList().get(0).getFlag());
		assertEquals(Boolean.FALSE, object.getSimpleObjectList().get(1).getFlag());
		assertEquals(Boolean.TRUE, object.getSimpleObjectList().get(2).getFlag());
		assertEquals(Boolean.FALSE, object.getSimpleObjectList().get(3).getFlag());
	}
	
}

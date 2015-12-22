package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.ObjectContainSimpleObject;

public class TestObjectContainSimpleObject extends BaseTestCast2{
	
	public void testObjectContainSimple(){
		Context context = new ContextImpl();
		context.put("name", "name");
		context.put("simpleObject.name", "1");
		context.put("simpleObject.length", new Integer(5));
		context.put("simpleObject.length2", "2");
		context.put("simpleObject.flag", true);
		ObjectContainSimpleObject object = (ObjectContainSimpleObject) generator.getObject(null, null,
				ObjectContainSimpleObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertEquals("name",object.getName());
		assertTrue( 2 == object.getSimpleObject().getLength2());
		assertEquals( Integer.valueOf(5),object.getSimpleObject().getLength());
		assertEquals(Boolean.TRUE,object.getSimpleObject().getFlag());
		
	}
}

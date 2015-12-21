/**
 * 
 */
package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.ParentObject;

/**
 * @author Administrator
 *
 */
public class TestParentObject extends BaseTestCast2 {
	public void testParentObjectSimpleNoVarName() {
		Context context = new ContextImpl();
		context.put("simpleObject.name", "name");
		context.put("simpleObject.length", "1");
		context.put("simpleObject.length2", "2");
		context.put("simpleObject.flag", "true");
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertEquals("name", parentObj.getSimpleObject().getName());
		assertEquals(2, parentObj.getSimpleObject().getLength2());
		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
				.getLength());
		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
	}

	public void testParentObjectSimpleWithDefaultName() {
		Context context = new ContextImpl();
		context.put("parentObject.simpleObject.name", "name");
		context.put("parentObject.simpleObject.length", "1");
		context.put("parentObject.simpleObject.length2", "2");
		context.put("parentObject.simpleObject.flag", "true");
		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
				ParentObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertEquals("name", parentObj.getSimpleObject().getName());
		assertEquals(2, parentObj.getSimpleObject().getLength2());
		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
				.getLength());
		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
	}

	public void testParentObjectSimpleWithVarName() {
		Context context = new ContextImpl();
		context.put("abc.simpleObject.name", "name");
		context.put("abc.simpleObject.length", "1");
		context.put("abc.simpleObject.length2", "2");
		context.put("abc.simpleObject.flag", "true");
		ParentObject parentObj = (ParentObject) generator.getObject("abc",
				null, ParentObject.class.getName(), this.getClass()
						.getClassLoader(), context);
		assertEquals("name", parentObj.getSimpleObject().getName());
		assertEquals(2, parentObj.getSimpleObject().getLength2());
		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
				.getLength());
		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
	}
}

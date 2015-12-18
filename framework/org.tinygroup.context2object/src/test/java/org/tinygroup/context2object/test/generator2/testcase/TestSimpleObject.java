package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.ChildObject;
import org.tinygroup.context2object.test.generator2.config.ParentObject;
import org.tinygroup.context2object.test.generator2.config.SimpleObject;

public class TestSimpleObject extends BaseTestCast2 {
	
//	public void testSimpleProperty() {
//		Context context = new ContextImpl();
//		context.put("bean.name", "name");
//		context.put("bean.length", "1");
//		context.put("bean.length2", "2");
//		context.put("bean.flag", "true");
//		SimpleObject bean = (SimpleObject) generator.getObject("bean", null,
//				SimpleObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//		assertEquals("name", bean.getName());
//		assertEquals(2, bean.getLength2());
//		assertEquals(Integer.valueOf(1), bean.getLength());
//		assertEquals(Boolean.TRUE, bean.getFlag());
//	}
//
//	public void testSimpleProperty2() {
//		Context context = new ContextImpl();
//		context.put("simpleObject.name", "name");
//		context.put("simpleObject.length", "1");
//		context.put("simpleObject.length2", "2");
//		context.put("simpleObject.flag", "true");
//		SimpleObject bean = (SimpleObject) generator.getObject(null, null,
//				SimpleObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//		assertEquals("name", bean.getName());
//		assertEquals(2, bean.getLength2());
//		assertEquals(Integer.valueOf(1), bean.getLength());
//		assertEquals(Boolean.TRUE, bean.getFlag());
//	}
//
//	public void testSimpleProperty3() {
//		Context context = new ContextImpl();
//		context.put("name", "name");
//		context.put("length", "1");
//		context.put("length2", "2");
//		context.put("flag", "true");
//		SimpleObject bean = (SimpleObject) generator.getObject(null, null,
//				SimpleObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//		assertEquals("name", bean.getName());
//		assertEquals(2, bean.getLength2());
//		assertEquals(Integer.valueOf(1), bean.getLength());
//		assertEquals(Boolean.TRUE, bean.getFlag());
//	}
//
//	public void testSubObj1() {
//		Context context = new ContextImpl();
//		context.put("simpleObject.name", "name");
//		context.put("simpleObject.length", "1");
//		context.put("simpleObject.length2", "2");
//		context.put("simpleObject.flag", "true");
//		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
//				ParentObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//		assertEquals("name", parentObj.getSimpleObject().getName());
//		assertEquals(2, parentObj.getSimpleObject().getLength2());
//		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
//				.getLength());
//		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
//	}
//
//	public void testSubObj2() {
//		Context context = new ContextImpl();
//		context.put("parentObject.simpleObject.name", "name");
//		context.put("parentObject.simpleObject.length", "1");
//		context.put("parentObject.simpleObject.length2", "2");
//		context.put("parentObject.simpleObject.flag", "true");
//		ParentObject parentObj = (ParentObject) generator.getObject(null, null,
//				ParentObject.class.getName(), this.getClass().getClassLoader(),
//				context);
//		assertEquals("name", parentObj.getSimpleObject().getName());
//		assertEquals(2, parentObj.getSimpleObject().getLength2());
//		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
//				.getLength());
//		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
//	}
//
//	public void testSubObj3() {
//		Context context = new ContextImpl();
//		context.put("abc.simpleObject.name", "name");
//		context.put("abc.simpleObject.length", "1");
//		context.put("abc.simpleObject.length2", "2");
//		context.put("abc.simpleObject.flag", "true");
//		ParentObject parentObj = (ParentObject) generator.getObject("abc",
//				null, ParentObject.class.getName(), this.getClass()
//						.getClassLoader(), context);
//		assertEquals("name", parentObj.getSimpleObject().getName());
//		assertEquals(2, parentObj.getSimpleObject().getLength2());
//		assertEquals(Integer.valueOf(1), parentObj.getSimpleObject()
//				.getLength());
//		assertEquals(Boolean.TRUE, parentObj.getSimpleObject().getFlag());
//	}

}

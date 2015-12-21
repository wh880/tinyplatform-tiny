package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.SimpleObject;

public class TestSimpleObject extends BaseTestCast2 {

	public void testSimpleProperty() {
		Context context = new ContextImpl();
		context.put("bean.name", "name");
		context.put("bean.length", "1");
		context.put("bean.length2", "2");
		context.put("bean.flag", "true");
		SimpleObject bean = (SimpleObject) generator.getObject("bean", null,
				SimpleObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertMethod(bean);
	}

	public void testSimpleProperty2() {
		Context context = new ContextImpl();
		context.put("simpleObject.name", "name");
		context.put("simpleObject.length", "1");
		context.put("simpleObject.length2", "2");
		context.put("simpleObject.flag", "true");
		SimpleObject bean = (SimpleObject) generator.getObject(null, null,
				SimpleObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertMethod(bean);
	}

	public void testSimpleProperty3() {
		Context context = new ContextImpl();
		context.put("name", "name");
		context.put("length", "1");
		context.put("length2", "2");
		context.put("flag", "true");
		SimpleObject bean = (SimpleObject) generator.getObject(null, null,
				SimpleObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertMethod(bean);
	}

	private void assertMethod(SimpleObject bean) {
		assertEquals("name", bean.getName());
		assertEquals(2, bean.getLength2());
		assertEquals(Integer.valueOf(1), bean.getLength());
		assertEquals(Boolean.TRUE, bean.getFlag());
	}

}

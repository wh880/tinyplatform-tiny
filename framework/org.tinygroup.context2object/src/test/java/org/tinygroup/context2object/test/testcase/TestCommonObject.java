package org.tinygroup.context2object.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.bean.CommonObject;

public class TestCommonObject extends BastTestCast{

	public void testCommonObject() {
		Context context = new ContextImpl();
		context.put("field.in", "1");
		context.put("field.name", "name");
		CommonObject obj = (CommonObject) generator.getObject("field", null,
				CommonObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertNotNull(obj);
		assertEquals( Integer.valueOf("1"),obj.getIn());
		assertEquals( "name",obj.getName());
	}
	
	
	public void testCommonObject2() {
		Context context = new ContextImpl();
		context.put("field.in", "1");
		CommonObject obj = (CommonObject) generator.getObject("field", null,
				CommonObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertNotNull(obj);
		assertEquals( Integer.valueOf("1"),obj.getIn());
	}
}

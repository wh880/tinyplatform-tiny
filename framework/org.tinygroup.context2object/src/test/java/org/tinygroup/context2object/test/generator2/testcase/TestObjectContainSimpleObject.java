package org.tinygroup.context2object.test.generator2.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.generator2.config.SimpleObject;

public class TestObjectContainSimpleObject extends BaseTestCast2{
	public void testObjectContainSimple(){
		Context context = new ContextImpl();
		context.put("bean.name", "name");
		context.put("bean.length", "1");
		context.put("bean.length2", "2");
		context.put("bean.flag", "true");
		SimpleObject bean = (SimpleObject) generator.getObject("bean", null,
				SimpleObject.class.getName(), this.getClass().getClassLoader(),
				context);
		assertEquals( "name",bean.getName());
		assertEquals( 2,bean.getLength2());
		assertEquals( Integer.valueOf(1),bean.getLength());
		assertEquals( Boolean.TRUE,bean.getFlag());
	}
}

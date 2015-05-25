package org.tinygroup.context2object.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.test.bean.BeanField;

public class TestBeanNoField extends BastTestCast {
	
	public void testRun() {
		Context context = new ContextImpl();
		context.put("name", "name1");
		context.put("field.name", "name2");
		BeanField bean = (BeanField) generator.getObject(null, null,
				BeanField.class.getName(), this.getClass().getClassLoader(),
				context);
		assertEquals(bean.getName(), "name1");
		assertEquals(bean.getField().getName(), "name2");
	}
}

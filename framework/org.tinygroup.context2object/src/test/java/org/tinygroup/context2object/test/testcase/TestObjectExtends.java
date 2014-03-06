package org.tinygroup.context2object.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.CatChild;

public class TestObjectExtends extends BastTestCast{
	public void testObjectArray() {
		Context context = new ContextImpl();
		context.put("name", "name1");
		context.put("nickName", "nickName");
		CatChild c = (CatChild) generator.getObject(null,null, CatChild.class.getName(), context);
		assertEquals(c.getNickName(), "nickName");
		System.out.println(c.getNickName());
	}
}

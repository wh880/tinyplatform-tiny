package org.tinygroup.context2object.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context2object.CatChild;

public class TestNullString extends BastTestCast{
	public void testObjectArray() {
		Context context = new ContextImpl();
		context.put("name", "name1");
		context.put("nickName", "");
		CatChild c = (CatChild) generator.getObject(null,null, CatChild.class.getName(), context);
		assertEquals(c.getNickName(), "");
		System.out.println(c.getNickName().equals(""));
	}
}

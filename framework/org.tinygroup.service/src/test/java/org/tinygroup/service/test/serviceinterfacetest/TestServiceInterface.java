package org.tinygroup.service.test.serviceinterfacetest;

import junit.framework.TestCase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.service.util.ServiceTestUtil;

public class TestServiceInterface  extends TestCase{
	
	
	public void testServiceInterface(){
		
		Context context = new ContextImpl();
		Context paramContext=ContextFactory.getContext();
		paramContext.put("firstName", "ren");
		paramContext.put("secondName", "hui");
		context.put("context", paramContext);
		ServiceTestUtil.execute("testService", context);
		Context resultContext=context.get("result");
		assertEquals("ren", resultContext.get("firstName"));
		assertEquals("hui", resultContext.get("secondName"));
		
		ServiceTestUtil.execute("testAliasService", context);
		resultContext=context.get("result");
		assertEquals("ren", resultContext.get("firstName"));
		assertEquals("hui", resultContext.get("secondName"));
	}
	
}

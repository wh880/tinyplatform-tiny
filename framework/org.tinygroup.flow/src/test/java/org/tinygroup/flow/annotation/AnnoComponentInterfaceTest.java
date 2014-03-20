package org.tinygroup.flow.annotation;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;

import junit.framework.TestCase;

public class AnnoComponentInterfaceTest extends TestCase {

	protected void setUp() throws Exception {
		AbstractTestUtil.init(null, true);
	}

	public void testComponentInterface(){
		FlowExecutor flowExecutor=SpringUtil.getBean(FlowExecutor.FLOW_BEAN);
		Context context=new ContextImpl();
		context.put("name", "tiny");
		context.put("resultKey", "result");
		flowExecutor.execute("helloworld", context);
		assertEquals("Hello, tiny", context.get("result"));
		
	}
	
	
	
}

package org.tinygroup.flow.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;

public class TestEl extends AbstractFlowComponent {

	public void setUp() throws Exception {
		super.setUp();
		DataUtil.reset();
	}
	
	// c = aa+bb-5 = 2
	// c<a
	public void testEL() {
		Context context = new ContextImpl();
		context.put("aa", 3);
		context.put("bb", 4);
		flowExecutor.execute("testEl", "begin", context);
		assertEquals(0, DataUtil.getData());
	}
	
	public void testEL1() {
		Context context = new ContextImpl();
		context.put("aa", "3");
		context.put("bb", 4);
		flowExecutor.execute("testEl", "begin", context);
		assertEquals(0, DataUtil.getData());
	}
	
	// c = aa+bb-5 = 4
	// c>a
	public void testEL2() {  
		Context context = new ContextImpl();
		context.put("aa", 3);
		context.put("bb", 6);
		flowExecutor.execute("testEl", "begin", context);
		assertEquals(10, DataUtil.getData());
	}
}
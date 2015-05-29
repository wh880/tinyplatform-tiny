package org.tinygroup.flow.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;
import org.tinygroup.flow.test.Exception.ExceptionNew0;

public class TestExtendFlow extends AbstractFlowComponent {
	public void setUp() throws Exception {
		super.setUp();
		DataUtil.reset();
	}
	public void testExtendFlowParent() {
		Context context = new ContextImpl();
		try{
			flowExecutor.execute("testExtendFlowParent", "begin", context);
			assertEquals(10, DataUtil.getData());
		}catch (ExceptionNew0 e) {
			assertTrue(true);
		}
	}
	public void testExtendFlowChild() {
		Context context = new ContextImpl();
		try{
			flowExecutor.execute("testExtendFlowChild", "begin", context);
			assertEquals(30, DataUtil.getData());
		}catch (ExceptionNew0 e) {
			assertTrue(true);
		}
	}
	
	public void testExtendFlowChild2() {
		Context context = new ContextImpl();
		try{
			flowExecutor.execute("testExtendFlowChild2", "begin", context);
			assertEquals(23, DataUtil.getData());
		}catch (ExceptionNew0 e) {
			
			assertTrue(true);
		}
	}
}

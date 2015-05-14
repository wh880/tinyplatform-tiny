package org.tinygroup.flow.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.flow.component.AbstractFlowComponent;
import org.tinygroup.flow.config.Flow;
import org.tinygroup.flow.test.Exception.ExceptionNew0;
import org.tinygroup.flow.test.Exception.ExceptionNew1;
import org.tinygroup.flow.test.Exception.ExceptionNew2;
import org.tinygroup.flow.test.Exception.ExceptionNew3InOtherNode;
import org.tinygroup.flow.test.Exception.ExceptionNew4InOtherFlow;

public class TestExceptionNew extends AbstractFlowComponent {
	
	public void setUp() throws Exception {
		super.setUp();
		DataUtil.reset();
	}
	public void testExceptionNew0() {
		Context context = new ContextImpl();
		try{
			context.put("exceptionNo", 0);
			flowExecutor.execute("testExceptionNew", "begin", context);
			assertTrue(false);
		}catch (ExceptionNew0 e) {
			assertTrue(true);
		}
		

	}
	

	public void testExceptionNew1() {
		Context context = new ContextImpl();
		context.put("exceptionNo", 1);
		flowExecutor.execute("testExceptionNew", "begin", context);
		assertEquals(1, DataUtil.getData());
		Throwable throwable=context.get("throwableObject");
		assertTrue(throwable instanceof ExceptionNew1);
		Flow flow=context.get(FlowExecutor.EXCEPTION_DEAL_FLOW);
		assertEquals("testExceptionNew",flow.getId());

	}

	public void testExceptionNew2() {
		Context context = new ContextImpl();
		context.put("exceptionNo", 2);
		flowExecutor.execute("testExceptionNew", "begin", context);
		assertEquals(2, DataUtil.getData());
		Throwable throwable=context.get("throwableObject");
		assertTrue(throwable instanceof ExceptionNew2);
		Flow flow=context.get(FlowExecutor.EXCEPTION_DEAL_FLOW);
		assertEquals("testExceptionNew",flow.getId());

	}
	
	public void testExceptionNew3() {
		Context context = new ContextImpl();
		context.put("exceptionNo", 3);
		flowExecutor.execute("testExceptionNew", "begin", context);
		assertEquals(3, DataUtil.getData());
		Throwable throwable=context.get("throwableObject");
		assertTrue(throwable instanceof ExceptionNew3InOtherNode);
		Flow flow=context.get(FlowExecutor.EXCEPTION_DEAL_FLOW);
		assertEquals("testExceptionNew",flow.getId());

	}
	
	public void testExceptionNew32() {
		Context context = new ContextImpl();
		context.put("exceptionNo", 3);
		flowExecutor.execute("testExceptionNew", context);
		assertEquals(3, DataUtil.getData());
		Throwable throwable=context.get("throwableObject");
		assertTrue(throwable instanceof ExceptionNew3InOtherNode);
		Flow flow=context.get(FlowExecutor.EXCEPTION_DEAL_FLOW);
		assertEquals("testExceptionNew",flow.getId());

	}
	
	public void testExceptionNew4() {
		Context context = new ContextImpl();
		context.put("exceptionNo", 4);
		flowExecutor.execute("testExceptionNew", "begin", context);
		assertEquals(4, DataUtil.getData());
		Throwable throwable=context.get("throwableObject");
		assertTrue(throwable instanceof ExceptionNew4InOtherFlow);
		Flow flow=context.get(FlowExecutor.EXCEPTION_DEAL_FLOW);
		assertEquals(FlowExecutor.EXCEPTION_DEAL_FLOW,flow.getId());

	}
	public void testExceptionNewNoException() {
		Context context = new ContextImpl();
		context.put("exceptionNo", 5);
		assertTrue(true);
	}
}



package org.tinygroup.flow.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;

public class TestSumFlow extends AbstractFlowComponent {

	public void setUp() throws Exception {
		super.setUp();
	}

	public void testSumFlow() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = a + sum
		flowExecutor.execute("testSumFlow", "begin", context);
		assertEquals(4, context.get("sum"));
		context.put("sum", 5);
		flowExecutor.execute("testSumFlow", "sumComponent_1", context);
		assertEquals(6, context.get("sum"));

	}
	
	public void testSumFlowNode() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 1);
		context.put("sum", 5);
		flowExecutor.execute("testSumFlow", "sumComponent_1", context);
		assertEquals(6, context.get("sum"));

	}
	
	public void testSumFlow2() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		context.put("c", 5);
		flowExecutor.execute("testSumFlow2",  context);
		assertEquals(8, context.get("sum"));

	}
	
	public void testSumFlowChild() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = a + sum
		flowExecutor.execute("testSumFlowChild", "begin", context);
		assertEquals(4, context.get("sum"));
		context.put("sum", 5);
		flowExecutor.execute("testSumFlowChild", "sumComponent_1", context);
		assertEquals(6, context.get("sum"));

	}
	
	public void testSumFlowChild2() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = b + sum
		flowExecutor.execute("testSumFlowChild2", "begin", context);
		assertEquals(5, context.get("sum"));
		flowExecutor.execute("testSumFlowChild2", "sumComponent_1", context);
		assertEquals(7, context.get("sum"));

	}

	public void testSumFlowChild3() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = b + sum
		//sum = sum + sum
		flowExecutor.execute("testSumFlowChild3", "begin", context);
		assertEquals(10, context.get("sum"));

	}
	
	public void testSumFlowGrandson() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		flowExecutor.execute("testSumFlowGrandson", "begin", context);
		assertEquals(105, context.get("sum"));

	}
	
	
	public void testSumFlowEl() {
		Context context = new ContextImpl();
		context.put("a", 11);
		context.put("b", 2);
		flowExecutor.execute("testSumFlowEl", "begin", context);
		assertEquals(24, context.get("sum"));

	}
	
	public void testSumFlowEl2() {
		Context context = new ContextImpl();
		context.put("a", 2);
		context.put("b", 12);
		flowExecutor.execute("testSumFlowEl", "begin", context);
		assertEquals(26, context.get("sum"));

	}
	
	public void testSumFlowError() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		try {
			flowExecutor.execute("testSumFlow", "sumComponent_1", context);
		} catch (Exception e) {
			assertTrue(true);
		}
		
	

	}
}

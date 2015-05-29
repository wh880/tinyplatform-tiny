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
	//执行父流程
	public void testExtendFlowParent() {
		Context context = new ContextImpl();
		try{
			flowExecutor.execute("testExtendFlowParent", "begin", context);
			assertEquals(10, DataUtil.getData());
		}catch (ExceptionNew0 e) {
			assertTrue(true);
		}
	}
	//执行子流程，子流程仅仅覆盖了父流程的节点2
	//但子流程的节点2无后续节点，直接采用父流程的节点2的后续节点执行
	public void testExtendFlowChild() {
		Context context = new ContextImpl();
		try{
			flowExecutor.execute("testExtendFlowChild", "begin", context);
			assertEquals(30, DataUtil.getData());
		}catch (ExceptionNew0 e) {
			assertTrue(true);
		}
	}
	//执行子流程，子流程仅仅覆盖父流程的节点2
	//节点2有自己的后续节点，后续节点为结束节点
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

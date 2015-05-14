package org.tinygroup.flow.test.testcase;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;

public class TestDataDeal extends AbstractFlowComponent {
	
	public void setUp() throws Exception {
		super.setUp();
		DataUtil.reset();
	}

	public void testTestDataDeal() {
		Context context = new ContextImpl();
		flowExecutor.execute("testDataDeal", "begin", context);
		assertEquals(DataUtil.defaultValue, DataUtil.getData());

	}

	public void testTestDataDeal2() {
		Context context = new ContextImpl();
		flowExecutor.execute("testDataDeal2", "begin", context);
		assertEquals(DataUtil.defaultValue+1-2+3, DataUtil.getData());
	}
	
	public void testOtherFlowNode(){
		Context context = new ContextImpl();
		flowExecutor.execute("testOtherFlowNode", "begin", context);
		assertEquals(3, DataUtil.getData());
	}
}



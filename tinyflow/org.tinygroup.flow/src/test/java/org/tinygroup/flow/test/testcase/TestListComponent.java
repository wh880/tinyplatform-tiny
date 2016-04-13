package org.tinygroup.flow.test.testcase;

import java.util.ArrayList;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;

public class TestListComponent extends AbstractFlowComponent {

	public void setUp() throws Exception {
		super.setUp();
	}

	public void testList() {
		Context context = new ContextImpl();
		context.put("ii", 1);
		ArrayList<String> object = new ArrayList<String>();
		object.add("a");
		object.add("b");
		context.put("list", object);
		context.put("str", "aa");
		//sum = a + b
		//sum = a + sum
		flowExecutor.execute("ListFlow", "begin", context);
		assertEquals(context.get("ii1"), 1);
		ArrayList<String> object1 =context.get("list1");
		assertEquals(object.size(), object1.size());
		assertEquals("aa", context.get("str1"));

	}
	
	public void testList2() {
		Context context = new ContextImpl();
		ArrayList<String> object = new ArrayList<String>();
		object.add("a");
		object.add("b");
		context.put("list", object);
		//sum = a + b
		//sum = a + sum
		flowExecutor.execute("ListFlow2", "begin", context);
		assertEquals(1,context.get("ii1"));
		ArrayList<String> object1 =context.get("list1");
		assertEquals(object.size(), object1.size());
		assertEquals("s12", context.get("str1"));

	}
}

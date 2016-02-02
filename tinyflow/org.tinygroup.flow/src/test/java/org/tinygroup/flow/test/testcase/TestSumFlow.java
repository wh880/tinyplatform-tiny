/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
		int sum=context.get("sum");
		assertEquals(4,sum );
		context.put("sum", 5);
		flowExecutor.execute("testSumFlow", "sumComponent_1", context);
		sum=context.get("sum");
		assertEquals(6,sum);

	}
	
	public void testSumFlowNode() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 1);
		context.put("sum", 5);
		flowExecutor.execute("testSumFlow", "sumComponent_1", context);
		int sum=context.get("sum");
		assertEquals(6, sum);

	}
	
	public void testSumFlow2() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		context.put("c", 5);
		flowExecutor.execute("testSumFlow2",  context);
		int sum=context.get("sum");
		assertEquals(8, sum);

	}
	
	public void testSumFlowChild() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = a + sum
		flowExecutor.execute("testSumFlowChild", "begin", context);
		int sum=context.get("sum");
		assertEquals(4, sum);
		context.put("sum", 5);
		flowExecutor.execute("testSumFlowChild", "sumComponent_1", context);
		sum=context.get("sum");
		assertEquals(6, sum);

	}
	
	public void testSumFlowChild2() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = b + sum
		flowExecutor.execute("testSumFlowChild2", "begin", context);
		int sum=context.get("sum");
		assertEquals(5, sum);
		flowExecutor.execute("testSumFlowChild2", "sumComponent_1", context);
		sum=context.get("sum");
		assertEquals(7, sum);

	}

	public void testSumFlowChild3() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		//sum = a + b
		//sum = b + sum
		//sum = sum + sum
		flowExecutor.execute("testSumFlowChild3", "begin", context);
		int sum=context.get("sum");
		assertEquals(10, sum);

	}
	
	public void testSumFlowGrandson() {
		Context context = new ContextImpl();
		context.put("a", 1);
		context.put("b", 2);
		flowExecutor.execute("testSumFlowGrandson", "begin", context);
		int sum=context.get("sum");
		assertEquals(105, sum);

	}
	
	
	public void testSumFlowEl() {
		Context context = new ContextImpl();
		context.put("a", 11);
		context.put("b", 2);
		flowExecutor.execute("testSumFlowEl", "begin", context);
		int sum=context.get("sum");
		assertEquals(24, sum);

	}
	
	public void testSumFlowEl2() {
		Context context = new ContextImpl();
		context.put("a", 2);
		context.put("b", 12);
		flowExecutor.execute("testSumFlowEl", "begin", context);
		int sum=context.get("sum");
		assertEquals(26, sum);

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

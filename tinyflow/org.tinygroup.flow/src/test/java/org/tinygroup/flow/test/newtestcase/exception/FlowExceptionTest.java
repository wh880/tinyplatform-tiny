package org.tinygroup.flow.test.newtestcase.exception;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;
import org.tinygroup.flow.test.newtestcase.exception.component.ComponentException5;
 
/**
 * 流程编排异常处理的测试用例
 * 
 * @author zhangliang08072
 * @version $Id: FlowExceptionTest.java, v 0.1 2016年4月28日 上午12:00:03 zhangliang08072 Exp $
 */
public class FlowExceptionTest extends AbstractFlowComponent {


	/**
	 * 分支处理异常
	 * 异常生成switch组件抛出org.tinygroup.flow.test.newtestcase.exception.component.ComponentException1
	 * 然后被“测试流程异常1”节点处理
	 */
	public void testException1(){
		Context context = new ContextImpl();
		context.put("exceptionNo", 1);
		flowExecutor.execute("flowExceptionTest", "begin", context);
		assertEquals(1, Integer.valueOf(context.get("result").toString()).intValue());
	}
	
	/**
	 * 分支处理异常
	 * 异常生成switch组件抛出org.tinygroup.flow.test.newtestcase.exception.component.ComponentException2
	 * 然后被“测试流程异常2”节点处理
	 */
	public void testException2(){
		Context context = new ContextImpl();
		context.put("exceptionNo", 2);
		flowExecutor.execute("flowExceptionTest", "begin", context);
		assertEquals(2, Integer.valueOf(context.get("result").toString()).intValue());
	}
	
	/**
	 * 流程内异常节点处理异常
	 * 异常生成switch组件抛出org.tinygroup.flow.test.newtestcase.exception.component.ComponentException3
	 * 然后被“测试流程异常3”节点处理
	 */
	public void testException3(){
		Context context = new ContextImpl();
		context.put("exceptionNo", 3);
		flowExecutor.execute("flowExceptionTest", "begin", context);
		assertEquals(3, Integer.valueOf(context.get("result").toString()).intValue());
	}
	
	/**
	 * 异常流程处理异常
	 * 异常生成switch组件抛出org.tinygroup.flow.test.newtestcase.exception.component.ComponentException4
	 * 然后被流程ID为"exceptionProcessFlow"的流程的“测试流程异常4”节点处理
	 */
	public void testException4(){
		Context context = new ContextImpl();
		context.put("exceptionNo", 4);
		flowExecutor.execute("flowExceptionTest", "begin", context);
		assertEquals(4, Integer.valueOf(context.get("result").toString()).intValue());
	}
	
	/**
	 * 异常处理未定义
	 * 异常生成switch组件抛出org.tinygroup.flow.test.newtestcase.exception.component.ComponentException5
	 * 没有任何流程处理该异常，直接向外层调用抛出
	 */
	public void testException5(){
		Context context = new ContextImpl();
		context.put("exceptionNo", 5);
		try {
			flowExecutor.execute("flowExceptionTest", "begin", context);
		} catch (ComponentException5 e) {
			assertTrue(true);
		}
	}
}

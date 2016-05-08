package org.tinygroup.flow.test.newtestcase.inherit;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.flow.component.AbstractFlowComponent;
 
/**
 * 流程继承和重入测试用例
 * 
 * @author zhangliang08072
 * @version $Id: FlowInheritTest.java, v 0.1 2016年4月28日 上午12:00:03 zhangliang08072 Exp $
 */
public class FlowInheritTest extends AbstractFlowComponent {


	/**
	 * 重入
	 */
	public void testFlowInherit1(){
		Context context = new ContextImpl();
		context.put("resultStr", "");
		flowExecutor.execute("flowInheritTest", "flowInheritComponent_1", context);
		assertEquals("bbb", context.get("resultStr").toString());
		
//		Context context2 = new ContextImpl();
//		context2.put("resultStr", "");
//		Event e = Event.createEvent("flowInheritTest","flowInheritComponent_1", context2);
//		cepcore.process(e);
//		assertEquals("bbb", e.getServiceRequest().getContext().get("resultStr").toString());
	}
	
	//子流程纯继承不做任何覆盖
	public void testFlowInherit2(){
		Context context = new ContextImpl();
		context.put("resultStr", "");
		flowExecutor.execute("sonOfFlowInheritTest", "begin", context);
		assertEquals("aaabbb", context.get("resultStr").toString());
		
		Context context2 = new ContextImpl();
		context2.put("resultStr", "");
		Event e = Event.createEvent("sonOfFlowInheritTest", context2);
		cepcore.process(e);
		assertEquals("aaabbb", e.getServiceRequest().getContext().get("resultStr").toString());
	}
	
	//子流程流程属性做一些覆盖，具体参见流程各属性含义，各个属性均作  
	//TODO 目前无能测试的可被子流程覆盖的流程属性
	
	//子流程覆盖节点
	public void testFlowInherit3(){
		Context context = new ContextImpl();
		context.put("resultStr", "");
		flowExecutor.execute("sonOfFlowInheritTest2", "begin", context);
		assertEquals("cccddd", context.get("resultStr").toString());
		
		Context context2 = new ContextImpl();
		context2.put("resultStr", "");
		Event e = Event.createEvent("sonOfFlowInheritTest2", context2);
		cepcore.process(e);
		assertEquals("cccddd", e.getServiceRequest().getContext().get("resultStr").toString());
	}
	
	
	//子流程覆盖分支
	public void testFlowInherit4(){
		Context context = new ContextImpl();
		context.put("resultStr", "");
		flowExecutor.execute("sonOfFlowInheritTest3", "begin", context);
		assertEquals("cccfffbbb", context.get("resultStr").toString());
		
		Context context2 = new ContextImpl();
		context2.put("resultStr", "");
		Event e = Event.createEvent("sonOfFlowInheritTest3", context2);
		cepcore.process(e);
		assertEquals("cccfffbbb", e.getServiceRequest().getContext().get("resultStr").toString());
	}
	
}

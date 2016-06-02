package org.tinygroup.flow.test.newtestcase.nullelnode;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.flow.component.AbstractFlowComponent;
 
/**
 * 
 * @description：节点有多个后续节点时，空条件的节点放最后执行
 * @author: qiucn
 * @version: 2016年5月31日 上午10:19:31
 */
public class FlowNullElNodeComponentTest extends AbstractFlowComponent {

	public void testNullElNode(){
		Context context = new ContextImpl();
		context.put("a", 1);
		flowExecutor.execute("nullElNodeTest", context);
		assertEquals(2, Integer.valueOf(context.get("sum").toString()).intValue());
		
		Context context2 = new ContextImpl();
		context2.put("a", 1);
		Event e = Event.createEvent("nullElNodeTest", context2);
		cepcore.process(e);
		assertEquals(2, Integer.valueOf(e.getServiceRequest().getContext().get("sum").toString()).intValue());
	}
}

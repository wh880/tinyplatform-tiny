package org.tinygroup.flow.test.newtestcase.exception;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.flow.component.AbstractFlowComponent;
import org.tinygroup.flow.exception.FlowRuntimeException;
import org.tinygroup.flow.exception.errorcode.FlowExceptionErrorCode;
 
/**
 * 
 * @description：节点未配置后续节点
 * @author: qiucn
 * @version: 2016年5月10日 上午11:05:45
 */
public class FlowNextNodeExceptionTest extends AbstractFlowComponent{

	/**
	 * 
	 * @description：节点未配置后续节点
	 * @author: qiucn
	 * @version: 2016年5月10日 上午11:05:45
	 */
	public void testflowProperty(){
		try {
			Context context = new ContextImpl();
			context.put("el", "");
			context.put("str", "");
			flowExecutor.execute("flowNextNodeTestFlow", context);
		} catch (FlowRuntimeException e) {
			assertEquals(FlowExceptionErrorCode.FLOW_NEXT_NODE_NOT_FOUND_EXCEPTION, e.getErrorCode().toString());
		}
	}
}

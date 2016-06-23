package org.tinygroup.flow.mbean;

import org.tinygroup.flow.FlowExecutor;

public class FlowMonitor implements FlowMonitorMBean{

	FlowExecutor flowExecutor;
	
	public FlowExecutor getFlowExecutor() {
		return flowExecutor;
	}

	public void setFlowExecutor(FlowExecutor flowExecutor) {
		this.flowExecutor = flowExecutor;
	}
	
	public Integer getFlowServiceTotal() {
		return flowExecutor.getFlowIdMap().size();
	}
	
	public boolean isExistFlowService(String flowId) {
		if(flowExecutor.getFlow(flowId)!=null){
			return true;
		}
		return false;
	}

	public Integer getComponentTotal() {
		flowExecutor.getComponentDefines();
		return null;
	}

	public boolean isExistComponent(String componentName) {
		if(flowExecutor.getComponentDefine(componentName)!=null){
			return true;
		}
		return false;
	}

}

	
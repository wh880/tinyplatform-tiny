package org.tinygroup.flow.mbean;

import org.tinygroup.flow.FlowExecutor;

public class FlowMonitor implements FlowMonitorMBean{

	FlowExecutor flowExecutor;
	FlowExecutor pageFlowExecutor;
	
	public FlowExecutor getFlowExecutor() {
		return flowExecutor;
	}

	public void setFlowExecutor(FlowExecutor flowExecutor) {
		this.flowExecutor = flowExecutor;
	}
	
	public FlowExecutor getPageFlowExecutor() {
		return pageFlowExecutor;
	}

	public void setPageFlowExecutor(FlowExecutor pageFlowExecutor) {
		this.pageFlowExecutor = pageFlowExecutor;
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

	public Integer getPageFlowServiceTotal() {
		return pageFlowExecutor.getFlowIdMap().size();
	}

	public boolean isExistPageFlowService(String pageflowId) {
		if(pageFlowExecutor.getFlow(pageflowId)!=null){
			return true;
		}
		return false;
	}

}

	
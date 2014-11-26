package org.tinygroup.flowbasicservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tinygroup.flow.FlowExecutor;
import org.tinygroup.flow.config.Flow;

public class FlowService {
	private FlowExecutor executor;

	public FlowExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(FlowExecutor executor) {
		this.executor = executor;
	}
	
	public List<Flow> getFlows(){
		Map<String,Flow> map = executor.getFlowIdMap();
		List<Flow> list = new ArrayList<Flow>();
		for(Flow flow:map.values()){
			list.add(flow);
		}
		return list;
	}
	
	public Flow getFlow(String id){
		return executor.getFlow(id);
	}
	
	
	

}

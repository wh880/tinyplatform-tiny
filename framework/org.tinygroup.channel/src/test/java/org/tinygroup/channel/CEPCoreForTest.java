package org.tinygroup.channel;

import org.tinygroup.cepcore.*;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;

import java.util.ArrayList;
import java.util.List;

public class CEPCoreForTest implements CEPCore{
	List<EventProcessor> list = new ArrayList<EventProcessor>();
	public void setEventProcessorChoose(EventProcessorChoose chooser) {
		// TODO Auto-generated method stub
		
	}

	public String getNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNodeName(String nodeName) {
		// TODO Auto-generated method stub
		
	}

	public CEPCoreOperator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOperator(CEPCoreOperator operator) {
		// TODO Auto-generated method stub
		
	}

	public void registerEventProcessor(EventProcessor eventProcessor) {
		list.add(eventProcessor);
	}

	public void unregisterEventProcessor(EventProcessor eventProcessor) {
		// TODO Auto-generated method stub
		
	}

	public void process(Event event) {
		for(EventProcessor processor:list){
			String serviceId = event.getServiceRequest().getServiceId();
			for(ServiceInfo s : processor.getServiceInfos()){
				if(s.getServiceId().equals(serviceId)){
					processor.process(event);
					return;
				}
			}
		}
		throw new RuntimeException("找不到服务");
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public List<EventProcessor> getEventProcessors() {
		return list;
	}

	public List<ServiceInfo> getServiceInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getServiceInfosVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ServiceInfo getServiceInfo(String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEventProcessorRegisterTrigger(
			EventProcessorRegisterTrigger trigger) {
		
	}

	public void refreshEventProcessors() {
		// TODO Auto-generated method stub
		
	}

}

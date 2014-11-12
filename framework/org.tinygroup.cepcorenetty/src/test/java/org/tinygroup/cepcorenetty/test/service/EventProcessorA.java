package org.tinygroup.cepcorenetty.test.service;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;

public class EventProcessorA implements EventProcessor {
	List<ServiceInfo> list = new ArrayList<ServiceInfo>();
	
	public void process(Event event) {
		ServiceRequest r = event.getServiceRequest();
		String serviceId = r.getServiceId();
		for(ServiceInfo s:list){
			if(s.getServiceId().equals(serviceId)){
				System.out.println("execute ServiceA id:"+serviceId);
				return;
			}
		}
	}

	public void setCepCore(CEPCore cepCore) {
		
	}

	public List<ServiceInfo> getServiceInfos() {
		return list;
	}

	public String getId() {
		return EventProcessorA.class.getName();
	}

	public int getType() {
		return EventProcessorA.TYPE_LOCAL;
	}

	public void addServiceInfo(ServiceInfo s){
		list.add(s);
	}

	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> getRegex() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRead() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setRead(boolean read) {
		// TODO Auto-generated method stub
		
	}
}

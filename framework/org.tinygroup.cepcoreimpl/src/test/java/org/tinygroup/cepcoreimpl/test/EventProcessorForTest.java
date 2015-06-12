package org.tinygroup.cepcoreimpl.test;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;

public class EventProcessorForTest  implements EventProcessor{
	List<ServiceInfo> list = new ArrayList<ServiceInfo>();
	
	public void process(Event event) {
		String serviceId = event.getServiceRequest().getServiceId();
		EventExecutor.execute(serviceId);
	}

	public void setCepCore(CEPCore cepCore) {
		
	}

	public List<ServiceInfo> getServiceInfos() {
		return list;
	}

	public String getId() {
		return EventProcessorForTest.class.getName();
	}

	public int getType() {
		return 0;
	}

	public int getWeight() {
		return 0;
	}

	public List<String> getRegex() {
		return null;
	}

	public boolean isRead() {
		return false;
	}

	public void setRead(boolean read) {
		
	}

}

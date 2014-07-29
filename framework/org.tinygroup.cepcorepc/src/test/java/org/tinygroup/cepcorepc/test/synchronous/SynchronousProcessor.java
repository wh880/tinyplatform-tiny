package org.tinygroup.cepcorepc.test.synchronous;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;

public class SynchronousProcessor implements EventProcessor{
	List<ServiceInfo> list = new ArrayList<ServiceInfo>();
	public void process(Event event) {
	
		System.out.println("Synchronous processor");
	}

	public void setCepCore(CEPCore cepCore) {
		// TODO Auto-generated method stub
		
	}

	public List<ServiceInfo> getServiceInfos() {
		return list;
	}

	public String getId() {
		return SynchronousProcessor.class.getName();
	}

	public int getType() {
		return EventProcessor.TYPE_LOCAL;
	}

	public int getWeight() {
		return 0;
	}
	
	public void addService(Service s){
		list.add(s);
	}

	public List<String> getRegex() {
		// TODO Auto-generated method stub
		return null;
	}

}

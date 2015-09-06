package org.tinygroup.cepcoregovernance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.tinygroup.event.Event;

public class ServiceExecuteContainer {
	//serviceId,eventId
	
	private ConcurrentMap<String,ConcurrentMap<String,Event>> localEventMap = new ConcurrentHashMap<String,ConcurrentMap<String,Event>>();
	private ConcurrentMap<String,ConcurrentMap<String,Event>> remoteEventMap = new ConcurrentHashMap<String,ConcurrentMap<String,Event>>();
	
	public static void addLocalExecuteBefore(Event e) {
		String serviceId= e.getServiceRequest().getServiceId();
		String eventId = e.getEventId();
	}

	public static void addLocalExecuteAfter(Event e) {
		String serviceId= e.getServiceRequest().getServiceId();
		String eventId = e.getEventId();
	}
	
	
	public static void addRemoteExecuteBefore(Event e) {
		String serviceId= e.getServiceRequest().getServiceId();
		String eventId = e.getEventId();
	}

	public static void addRemoteExecuteAfter(Event e) {
		String serviceId= e.getServiceRequest().getServiceId();
		String eventId = e.getEventId();
	}
}

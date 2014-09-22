package org.tinygroup.tinynetty;

import org.tinygroup.event.Event;

public class HandlerUtil {
	public static String getServiceId(Event event){
		return event.getServiceRequest().getServiceId();
	}
}

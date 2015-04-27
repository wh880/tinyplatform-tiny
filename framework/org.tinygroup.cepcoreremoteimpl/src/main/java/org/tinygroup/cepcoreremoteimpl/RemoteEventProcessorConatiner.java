package org.tinygroup.cepcoreremoteimpl;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;

public class RemoteEventProcessorConatiner {
	private static Map<String, RemoteEventProcessor> map = new HashMap<String, RemoteEventProcessor>();
	private static Map<RemoteEventProcessor, String> map2 = new HashMap<RemoteEventProcessor, String>();

	public static void add(String name, RemoteEventProcessor processor,
			CEPCore core) {
		if (map.containsKey(name)) {
			remove(name, core);
		}
		map.put(name, processor);
		map2.put(processor, name);
		core.registerEventProcessor(processor);
	}

	public static void remove(String name, CEPCore core) {
		if (map.containsKey(name)) {
			RemoteEventProcessor processor = map.remove(name);
			map2.remove(processor);
			processor.stopConnect();
			core.unregisterEventProcessor(processor);
		}

	}
	
	public static void remove(RemoteEventProcessor processor, CEPCore core) {
		if (map2.containsKey(processor)) {
			String s = map2.remove(processor);
			map.remove(s);
			core.unregisterEventProcessor(processor);
		}

	}
	
	public static void stop(){
		for(RemoteEventProcessor processor:map.values()){
			processor.stopConnect();
		}
	}
}

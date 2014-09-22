package org.tinygroup.tinynetty.remote;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;

public class NettyEventProcessorConatiner {
	private static Map<String, NettyEventProcessor> map = new HashMap<String, NettyEventProcessor>();
	private static Map<NettyEventProcessor, String> map2 = new HashMap<NettyEventProcessor, String>();

	public static void add(String name, NettyEventProcessor processor,
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
			NettyEventProcessor processor = map.remove(name);
			map2.remove(processor);
			core.unregisterEventProcessor(processor);
		}

	}
	
	public static void remove(NettyEventProcessor processor, CEPCore core) {
		if (map2.containsKey(processor)) {
			String s = map2.remove(processor);
			map.remove(s);
			core.unregisterEventProcessor(processor);
		}

	}

}

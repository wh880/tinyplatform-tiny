package org.tinygroup.cepcoreremoteimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;

public class RemoteEventProcessorConatiner {
	private static Map<String, ProcessorInfo> map = new HashMap<String, ProcessorInfo>();

	public static void add(Node node, List<ServiceInfo> services, CEPCore core) {
		String name = node.toString();
		if (map.containsKey(name)) {
			map.get(name).increase();
			// TODO:如果之前有，则进行其他处理
		} else {
			// 如果之前没有这个处理器，则创建并存储、注册
			RemoteEventProcessor processor = new RemoteEventProcessor(node,
					services);
			map.put(name, new ProcessorInfo(processor));
			core.registerEventProcessor(processor);
		}
	}

	public static void remove(String name, CEPCore core) {
		if (map.containsKey(name)) {
			if (map.get(name).decrease() == 0) {
				RemoteEventProcessor processor = map.get(name).getProcessor();
				core.unregisterEventProcessor(processor);
				processor.stopConnect();
			}

		}
	}

	public static void stop() {
		for (ProcessorInfo processorInfo : map.values()) {
			RemoteEventProcessor processor = processorInfo.getProcessor();
			processor.stopConnect();
		}
	}

}

/**
 * @author chenjiao
 * 
 */
class ProcessorInfo {
	volatile int counter = 1;
	RemoteEventProcessor processor;

	public int getCounter() {
		return counter;
	}

	public RemoteEventProcessor getProcessor() {
		return processor;
	}

	public ProcessorInfo(RemoteEventProcessor processor) {
		super();
		this.processor = processor;
	}

	public synchronized void increase() {
		counter++;
	}

	public synchronized int decrease() {
		counter--;
		return counter;
	}

}
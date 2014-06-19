package org.tinygroup.cepcorepc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreNodeManager;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcorepc.PcCepOperator;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public abstract class PcCepCoreImpl implements CEPCore{
	private static Logger logger = LoggerFactory.getLogger(PcCepCoreImpl.class);
	private Map<String, List<EventProcessor>> serviceIdMap = new HashMap<String, List<EventProcessor>>();
	private Map<String, EventProcessor> processorMap = new HashMap<String, EventProcessor>();
	private Map<String, ServiceInfo> serviceMap = new HashMap<String, ServiceInfo>();
	private String nodeName;
	private PcCepOperator operator;

	public void startCEPCore(CEPCore cep) {
		operator.startCEPCore(cep);
	}

	public void stopCEPCore(CEPCore cep) {
		operator.stopCEPCore(cep);
	}

	public void setOperator(PcCepOperator operator) {
		this.operator = operator;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void registerEventProcessor(EventProcessor eventProcessor) {
		logger.logMessage(LogLevel.INFO, "开始 注册EventProcessor:{}",
				eventProcessor.getId());
		processorMap.put(eventProcessor.getId(), eventProcessor);

		if (EventProcessor.TYPE_CHANNEL != eventProcessor.getType()) {
			for (ServiceInfo service : eventProcessor.getServiceInfos()) {
				serviceMap.put(service.getServiceId(), service);
			}
		}
		for (ServiceInfo service : eventProcessor.getServiceInfos()) {
			String name = service.getServiceId();
			if (serviceIdMap.containsKey(name)) {
				List<EventProcessor> list = serviceIdMap.get(name);
				if (!list.contains(eventProcessor)) {
					list.add(eventProcessor);
				}
			} else {
				List<EventProcessor> list = new ArrayList<EventProcessor>();
				serviceIdMap.put(name, list);
				list.add(eventProcessor);
			}
		}
		logger.logMessage(LogLevel.INFO, "注册EventProcessor:{}完成",
				eventProcessor.getId());
	}

	public void unregisterEventProcessor(EventProcessor eventProcessor) {
		logger.logMessage(LogLevel.INFO, "开始 注销EventProcessor:{}",
				eventProcessor.getId());
		processorMap.remove(eventProcessor.getId());
		
		for (ServiceInfo service : eventProcessor.getServiceInfos()) {
			String name = service.getServiceId();
			if (serviceIdMap.containsKey(name)) {
				List<EventProcessor> list = serviceIdMap.get(name);
				if(list.contains(eventProcessor)){
					list.remove(eventProcessor);
					if(list.size()==0){
						serviceIdMap.remove(name);
						serviceMap.remove(name);
					}
				}
				
			} else {
				
			}
		}
		
		
		logger.logMessage(LogLevel.INFO, "注销EventProcessor:{}完成",
				eventProcessor.getId());
	}

	public void process(Event event) {
		ServiceRequest request = event.getServiceRequest();
		String eventNodeName = event.getServiceRequest().getNodeName();
		EventProcessor eventProcessor = getEventProcessor(request,
				eventNodeName);
		eventProcessor.process(event);

	}

	private EventProcessor getEventProcessor(ServiceRequest serviceRequest,
			String eventNodeName) {
		List<EventProcessor> list = serviceIdMap.get(serviceRequest
				.getServiceId());
		if (list == null || list.size() == 0) {
			return null;
		}
		boolean hasNotNodeName = (eventNodeName == null || ""
				.equals(eventNodeName));
		if (!hasNotNodeName) {
			for (EventProcessor e : list) {
				if (eventNodeName.equals(e.getId())) {
					return e;
				}
			}
		}
		// 如果有本地的 则直接返回本地的EventProcessor
		for (EventProcessor e : list) {
			if (e.getType() == EventProcessor.TYPE_LOGICAL) {
				return e;
			}
		}
		// 如果全是远程EventProcessor,那么需要根据负载均衡机制计算
		// TODO: 根据负载均衡机制进行计算

		return list.get(0);
	}

	public void start() {

	}

	public void stop() {

	}

	public List<ServiceInfo> getServiceInfos() {
		return null;
	}

	public void setManager(CEPCoreNodeManager manager) {

	}

	public boolean isEnableRemote() {

		return false;
	}

	public void setEnableRemote(boolean enableRemote) {
		// TODO Auto-generated method stub

	}

}

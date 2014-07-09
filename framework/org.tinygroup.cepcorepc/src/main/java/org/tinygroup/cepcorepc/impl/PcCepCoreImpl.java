package org.tinygroup.cepcorepc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreOperator;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcore.EventProcessorChoose;
import org.tinygroup.cepcore.aop.CEPCoreAopManager;
import org.tinygroup.cepcore.exception.RequestNotFoundException;
import org.tinygroup.cepcore.impl.WeightChooser;
import org.tinygroup.cepcore.util.CEPCoreUtil;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.SpringUtil;

public class PcCepCoreImpl implements CEPCore {
	private static Logger logger = LoggerFactory.getLogger(PcCepCoreImpl.class);
	private Map<String, List<EventProcessor>> serviceIdMap = new HashMap<String, List<EventProcessor>>();
	private Map<String, EventProcessor> processorMap = new HashMap<String, EventProcessor>();
	private Map<String, ServiceInfo> localServiceMap = new HashMap<String, ServiceInfo>();
	private List<ServiceInfo> localServices = new ArrayList<ServiceInfo>();
	private String nodeName;
	private CEPCoreOperator operator;
	private EventProcessorChoose chooser;

	public CEPCoreOperator getOperator() {
		return operator;
	}

	public void startCEPCore(CEPCore cep) {
		operator.startCEPCore(cep);
	}

	public void stopCEPCore(CEPCore cep) {
		operator.stopCEPCore(cep);
	}

	public void setOperator(CEPCoreOperator operator) {
		this.operator = operator;
		this.operator.setCEPCore(this);
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
		eventProcessor.setCepCore(this);
		if (EventProcessor.TYPE_REMOTE != eventProcessor.getType()) {
			for (ServiceInfo service : eventProcessor.getServiceInfos()) {
				if (!localServiceMap.containsKey(service.getServiceId())) {
					localServiceMap.put(service.getServiceId(), service);
					localServices.add(service);
				}
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
				if (list.contains(eventProcessor)) {
					list.remove(eventProcessor);
					if (list.size() == 0) {
						serviceIdMap.remove(name);
						localServiceMap.remove(name);
					}
				}

			} else {

			}
		}

		logger.logMessage(LogLevel.INFO, "注销EventProcessor:{}完成",
				eventProcessor.getId());
	}

	public void process(Event event) {
		CEPCoreAopManager aopMananger = SpringUtil
				.getBean(CEPCoreAopManager.CEPCORE_AOP_BEAN);
		// 前置Aop
		aopMananger.beforeHandle(event);
		ServiceRequest request = event.getServiceRequest();
		String eventNodeName = event.getServiceRequest().getNodeName();
		logger.logMessage(LogLevel.INFO, "请求指定的执行节点为:{0}", eventNodeName);
		EventProcessor eventProcessor = getEventProcessor(request,
				eventNodeName);
		if (EventProcessor.TYPE_LOCAL == eventProcessor.getType()) {
			// local前置Aop
			aopMananger.beforeLocalHandle(event);
			try {
				// local处理
				eventProcessor.process(event);
			} catch (RuntimeException e) {
				dealException(e, event);
				throw e;
			} catch (java.lang.Error e) {
				dealException(e, event);
				throw e;
			}
			// local后置Aop
			aopMananger.afterLocalHandle(event);
		} else {
			// remote前置Aop
			aopMananger.beforeRemoteHandle(event);
			try {
				// remote处理
				eventProcessor.process(event);
			} catch (RuntimeException e) {
				dealException(e, event);
				throw e;
			} catch (java.lang.Error e) {
				dealException(e, event);
				throw e;
			}
			// remote后置Aop
			aopMananger.afterRemoteHandle(event);
		}
		// 后置Aop
		aopMananger.afterHandle(event);
	}

	private void dealException(Throwable e, Event event) {
		CEPCoreUtil.handle(e, event);
		Throwable t = e.getCause();
		while (t != null) {
			CEPCoreUtil.handle(t, event);
			t = t.getCause();
		}
	}

	private EventProcessor getEventProcessor(ServiceRequest serviceRequest,
			String eventNodeName) {
		List<EventProcessor> list = serviceIdMap.get(serviceRequest
				.getServiceId());
		if (list == null || list.size() == 0) {
			throw new RuntimeException("没有找到合适的服务处理器");
		}
		boolean hasNotNodeName = (eventNodeName == null || ""
				.equals(eventNodeName));
		if (!hasNotNodeName) {
			for (EventProcessor e : list) {
				if (eventNodeName.equals(e.getId())) {
					return e;
				}
			}
			throw new RuntimeException("没有找到指定的服务处理器：" + eventNodeName);
		}
		// 如果有本地的 则直接返回本地的EventProcessor
		for (EventProcessor e : list) {
			if (e.getType() == EventProcessor.TYPE_LOCAL) {
				return e;
			}
		}
		// 如果全是远程EventProcessor,那么需要根据负载均衡机制计算
		return getEventProcessorChoose().choose(list);
	}

	public void start() {
		if (operator != null) {
			operator.startCEPCore(this);
		}
	}

	public void stop() {
		if (operator != null) {
			operator.stopCEPCore(this);
		}
	}

	public List<ServiceInfo> getServiceInfos() {
		return localServices;
	}

	public ServiceInfo getServiceInfo(String serviceId) {
		ServiceInfo info = localServiceMap.get(serviceId);
		if (info == null) {// 如果本地查询服务没有查询到，且未开启远程调用，则抛出服务未找到异常
			throw new RequestNotFoundException(serviceId);
		}
		return info;
	}

	public void setEventProcessorChoose(EventProcessorChoose chooser) {
		this.chooser = chooser;
	}

	private EventProcessorChoose getEventProcessorChoose() {
		if (chooser == null) {
			chooser = new WeightChooser();
		}
		return chooser;
	}

}
/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.cepcoreimpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreOperator;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcore.EventProcessorChoose;
import org.tinygroup.cepcore.EventProcessorRegisterTrigger;
import org.tinygroup.cepcore.aop.CEPCoreAopManager;
import org.tinygroup.cepcore.exception.RequestNotFoundException;
import org.tinygroup.cepcore.impl.WeightChooser;
import org.tinygroup.cepcore.util.CEPCoreUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 当服务执行远程调用时 如果有多个远程处理器可用，则根据配置的EventProcessorChoose choose进行调用
 * 如果未曾配置，则默认为chooser生成权重chooser进行处理
 * 
 * @author chenjiao
 * 
 */
public class CEPCoreImpl implements CEPCore {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CEPCoreImpl.class);
	private Map<String, List<EventProcessor>> serviceIdMap = new HashMap<String, List<EventProcessor>>();
	// 服务版本，每次注册注销都会使其+1;
	ExecutorService executor = Executors.newCachedThreadPool();
	private static int serviceVersion = 0;
	/**
	 * 存放所有的EventProcessor
	 */
	private Map<String, EventProcessor> processorMap = new HashMap<String, EventProcessor>();
	/**
	 * 为本地service建立的map，便于通过serviceId迅速找到service
	 */
	private Map<String, ServiceInfo> localServiceMap = new HashMap<String, ServiceInfo>();
	/**
	 * 为远程service建立的map，便于通过serviceId迅速找到service
	 */
	private Map<String, ServiceInfo> remoteServiceMap = new HashMap<String, ServiceInfo>();
	/**
	 * 本地service列表
	 */
	private List<ServiceInfo> localServices = new ArrayList<ServiceInfo>();
	/**
	 * 为eventProcessor每次注册时所拥有的service信息 key为eventProcessor的id
	 */
	private Map<String, List<ServiceInfo>> eventProcessorServices = new HashMap<String, List<ServiceInfo>>();

	/**
	 * 
	 */
	private Map<EventProcessor, List<String>> regexMap = new HashMap<EventProcessor, List<String>>();
	/**
	 * 存放拥有正则表达式的EventProcessor，没有的不会放入此list
	 */
	private List<EventProcessor> processorList = new ArrayList<EventProcessor>();

	private String nodeName;
	private CEPCoreOperator operator;
	private EventProcessorChoose chooser;
	private List<EventProcessorRegisterTrigger> triggers = new ArrayList<EventProcessorRegisterTrigger>();

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

	private void dealAddEventProcessor(EventProcessor eventProcessor) {
		processorMap.put(eventProcessor.getId(), eventProcessor);
		eventProcessor.setCepCore(this);

		List<ServiceInfo> servicelist = eventProcessor.getServiceInfos();
		eventProcessorServices.put(eventProcessor.getId(), servicelist);
		if (servicelist != null && !servicelist.isEmpty()) {
			if (EventProcessor.TYPE_REMOTE != eventProcessor.getType()) {
				addLocalServiceInfo(servicelist);
			} else {
				for (ServiceInfo service : servicelist) {
					remoteServiceMap.put(service.getServiceId(), service);
				}
			}
			addServiceInfos(eventProcessor, servicelist);
		}

		addRegex(eventProcessor);
	}

	private void addLocalServiceInfo(List<ServiceInfo> servicelist) {
		for (ServiceInfo service : servicelist) {
			if (!localServiceMap.containsKey(service.getServiceId())) {
				localServiceMap.put(service.getServiceId(), service);
				localServices.add(service);
			}
		}
	}

	private void addServiceInfos(EventProcessor eventProcessor,
			List<ServiceInfo> servicelist) {
		for (ServiceInfo service : servicelist) {
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
	}

	private void addRegex(EventProcessor eventProcessor) {
		if (eventProcessor.getRegex() != null
				&& !eventProcessor.getRegex().isEmpty()) {
			regexMap.put(eventProcessor, eventProcessor.getRegex());
			processorList.add(eventProcessor);
		}
	}

	public void registerEventProcessor(EventProcessor eventProcessor) {

		LOGGER.logMessage(LogLevel.INFO, "开始 注册EventProcessor:{}",
				eventProcessor.getId());
		changeVersion(eventProcessor);
		if (processorMap.containsKey(eventProcessor.getId())) {
			removeEventProcessorInfo(eventProcessor);
		}
		dealAddEventProcessor(eventProcessor);
		LOGGER.logMessage(LogLevel.INFO, "注册EventProcessor:{}完成",
				eventProcessor.getId());
	}

	private void removeEventProcessorInfo(EventProcessor eventProcessor) {
		if (!eventProcessorServices.containsKey(eventProcessor.getId())) {
			return;
		}
		List<ServiceInfo> serviceInfos = eventProcessorServices
				.get(eventProcessor.getId());
		for (ServiceInfo service : serviceInfos) {
			removeServiceInfo(eventProcessor, service);
		}

		if (eventProcessor.getRegex() != null
				&& !eventProcessor.getRegex().isEmpty()) {
			regexMap.remove(eventProcessor);
		}
	}

	private void removeServiceInfo(EventProcessor eventProcessor,
			ServiceInfo service) {
		String name = service.getServiceId();
		if (serviceIdMap.containsKey(name)) {
			localServices.remove(service);// 20150318调整代码localServices.remove(localServices。indexOf(service))，旧代码有可能是-1
			List<EventProcessor> list = serviceIdMap.get(name);
			if (list.contains(eventProcessor)) {
				list.remove(eventProcessor);
				if (list.isEmpty()) {
					serviceIdMap.remove(name);
					localServiceMap.remove(name);
				}
			}

		} else {
			// do nothing
		}
	}

	public void unregisterEventProcessor(EventProcessor eventProcessor) {

		LOGGER.logMessage(LogLevel.INFO, "开始 注销EventProcessor:{}",
				eventProcessor.getId());
		changeVersion(eventProcessor);
		processorMap.remove(eventProcessor.getId());
		removeEventProcessorInfo(eventProcessor);
		LOGGER.logMessage(LogLevel.INFO, "注销EventProcessor:{}完成",
				eventProcessor.getId());
	}

	private void changeVersion(EventProcessor eventProcessor) {
		if (eventProcessor.getType() == EventProcessor.TYPE_LOCAL) {
			LOGGER.logMessage(LogLevel.INFO,
					"本地EventProcessor变动,对CEPCORE服务版本进行变更");
			serviceVersion++;// 如果发生了本地EventProcessor变动，则改变版本
		}
	}

	public void process(Event event) {
		CEPCoreAopManager aopMananger = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean(
				CEPCoreAopManager.CEPCORE_AOP_BEAN);
		// 前置Aop
		aopMananger.beforeHandle(event);
		ServiceRequest request = event.getServiceRequest();
		String eventNodeName = event.getServiceRequest().getNodeName();
		LOGGER.logMessage(LogLevel.INFO, "请求指定的执行节点为:{0}", eventNodeName);
		//如果指定了执行节点，则判断执行节点是否是当前节点，如果是，则将执行节点变量置空
		if(!StringUtil.isBlank(eventNodeName)&&!StringUtil.isBlank(nodeName)){
			LOGGER.logMessage(LogLevel.INFO, "当前节点NodeName:{}",nodeName);
			if(Node.checkEquals(eventNodeName, nodeName)){
				eventNodeName = null;
				LOGGER.logMessage(LogLevel.INFO, "请求指定的执行节点即当前节点");
			}
		}
		EventProcessor eventProcessor = getEventProcessor(request,
				eventNodeName);
		if (EventProcessor.TYPE_LOCAL == eventProcessor.getType()) {
			// local前置Aop
			aopMananger.beforeLocalHandle(event);
			try {
				// local处理
				// eventProcessor.process(event);
				deal(eventProcessor, event);
			} catch (RuntimeException e) {
				dealException(e, event);
				throw e;
			}
			// catch (java.lang.Error e) {
			// dealException(e, event);
			// throw e;
			// }
			// local后置Aop
			aopMananger.afterLocalHandle(event);
		} else {
			// 20150527注释此代码，在deal中统一为所有执行生成new context,无论是本地还是远程
			// ServiceInfo sinfo = null;
			// List<ServiceInfo> list = eventProcessor.getServiceInfos();
			// for (ServiceInfo info : list) {
			// if (info.getServiceId().equals(request.getServiceId())) {
			// sinfo = info;
			// }
			// }
			// Context newContext = CEPCoreUtil.getContext(event, sinfo, this
			// .getClass().getClassLoader());
			// event.getServiceRequest().setContext(newContext);
			// remote前置Aop
			aopMananger.beforeRemoteHandle(event);

			try {
				// remote处理
				// eventProcessor.process(event);
				deal(eventProcessor, event);
			} catch (RuntimeException e) {
				dealException(e, event);
				throw e;
			}
			// catch (java.lang.Error e) {
			// dealException(e, event);
			// throw e;
			// }
			// remote后置Aop
			aopMananger.afterRemoteHandle(event);
		}
		// 后置Aop
		aopMananger.afterHandle(event);
	}

	private void deal(EventProcessor eventProcessor, Event event) {
		Context oldContext = event.getServiceRequest().getContext();
		ServiceParamUtil.changeEventContext(event, this, Thread.currentThread()
				.getContextClassLoader());
		if (event.getMode() == Event.EVENT_MODE_ASYNCHRONOUS) {
			Event e = getEventClone(event);
			event.setMode(Event.EVENT_MODE_ASYNCHRONOUS);
			event.setType(Event.EVENT_TYPE_RESPONSE);
			// q调整为线程池
			SynchronousDeal thread = new SynchronousDeal(eventProcessor, e);
			// thread.start();
			executor.execute(thread);

		} else {
			eventProcessor.process(event);
		}
		ServiceParamUtil.resetEventContext(event, this, oldContext);
	}

	private Event getEventClone(Event event) {
		Event e = new Event();
		e.setEventId(event.getEventId());
		e.setServiceRequest(event.getServiceRequest());
		e.setType(event.getType());
		e.setGroupMode(event.getGroupMode());
		e.setMode(event.getMode());
		e.setPriority(event.getPriority());
		return e;
	}

	private void dealException(Throwable e, Event event) {
		CEPCoreUtil.handle(e, event, this.getClass().getClassLoader());
		Throwable t = e.getCause();
		while (t != null) {
			CEPCoreUtil.handle(t, event, this.getClass().getClassLoader());
			t = t.getCause();
		}
	}

	private EventProcessor getEventProcessorByRegex(
			ServiceRequest serviceRequest) {
		String serviceId = serviceRequest.getServiceId();
		for (EventProcessor p : processorList) {
			List<String> regex = regexMap.get(p);
			if (checkRegex(regex, serviceId)) {
				return p;
			}
		}
		throw new RuntimeException("没有找到合适的服务处理器");
	}

	private boolean checkRegex(List<String> regex, String serviceId) {
		for (String s : regex) {
			Pattern pattern = Pattern.compile(s);
			Matcher matcher = pattern.matcher(serviceId);
			boolean b = matcher.matches(); // 满足时，将返回true，否则返回false
			if (b) {
				return true;
			}
		}
		return false;
	}

	private EventProcessor getEventProcessor(ServiceRequest serviceRequest,
			String eventNodeName) {
		List<EventProcessor> list = serviceIdMap.get(serviceRequest
				.getServiceId());
		if(!StringUtil.isBlank(eventNodeName)){
			return findEventProcessor(serviceRequest,eventNodeName,list);
		}
		
		if (list == null || list.isEmpty()) {
			return getEventProcessorByRegex(serviceRequest);
		}
		return getEventProcessor(serviceRequest, list);
	}
	private EventProcessor findEventProcessor(ServiceRequest serviceRequest,
			String eventNodeName,List<EventProcessor> list){
		String serviceId = serviceRequest.getServiceId();
		for(String key:processorMap.keySet()){
			if(Node.checkEquals(key, eventNodeName)){
				EventProcessor e = processorMap.get(key);
				//如果包含该服务的EventProcessor列表中存在该处理器，则返回
				if(list.contains(e)){
					return e;
				}
				if(processorList.contains(e)){
					List<String> regex = regexMap.get(e);
					if (checkRegex(regex, serviceId)) {
						return e;
					}
				}
				throw new RuntimeException("节点"+eventNodeName+"对应的事件处理器上不存在服务:"+serviceId);
			}
		}
		throw new RuntimeException("当前服务器上不存在节点:"+eventNodeName+"对应的事件处理器");
	}

	private EventProcessor getEventProcessor(ServiceRequest serviceRequest,
			 List<EventProcessor> list) {
		if (list.size() == 1) {
			return list.get(0);
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

	private ServiceInfo getLocalServiceInfo(String serviceId) {
		return localServiceMap.get(serviceId);
	}

	private ServiceInfo getRemoteServiceInfo(String serviceId) {
		return remoteServiceMap.get(serviceId);
	}

	public ServiceInfo getServiceInfo(String serviceId) {
		ServiceInfo info = getLocalServiceInfo(serviceId);
		if (info == null) {
			info = getRemoteServiceInfo(serviceId);
		}
		if (info == null) {
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

	class SynchronousDeal implements Runnable {
		Event e;
		EventProcessor eventProcessor;

		public SynchronousDeal(EventProcessor eventProcessor, Event e) {
			this.e = e;
			this.eventProcessor = eventProcessor;
		}

		public void run() {
			eventProcessor.process(e);
		}
	}

	public void addEventProcessorRegisterTrigger(
			EventProcessorRegisterTrigger trigger) {
		triggers.add(trigger);
	}

	public void refreshEventProcessors() {
		for (EventProcessor processor : processorMap.values()) {
			if (!processor.isRead()) {
				registerEventProcessor(processor);
				processor.setRead(true);
			}
		}
		// if (operator != null && operator instanceof ArOperator) {
		// ((ArOperator) operator).reReg();
		// operator.getClass().getMethod("reReg");
		// }
		if (operator != null) {
			try {
				Method m = operator.getClass().getMethod("reReg");
				if (m != null) {
					m.invoke(operator);
				}
			} catch (IllegalArgumentException e) {
				LOGGER.errorMessage(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOGGER.errorMessage(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOGGER.errorMessage(e.getMessage(), e);
			} catch (SecurityException e) {
				LOGGER.errorMessage(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				LOGGER.errorMessage(e.getMessage(), e);
			}
		}
	}

	public List<EventProcessor> getEventProcessors() {
		List<EventProcessor> processors = new ArrayList<EventProcessor>();
		for (EventProcessor processor : processorMap.values()) {
			processors.add(processor);
		}
		return processors;
	}

	public int getServiceInfosVersion() {
		return serviceVersion;
	}
}
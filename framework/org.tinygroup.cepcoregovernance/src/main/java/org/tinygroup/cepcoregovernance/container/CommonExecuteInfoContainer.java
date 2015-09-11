package org.tinygroup.cepcoregovernance.container;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.event.Event;

public class CommonExecuteInfoContainer {
	/**
	 * serviceId,所有次数
	 */
	private Map<String, Long> serviceTimeMap = new HashMap<String, Long>();

	/**
	 * serviceId,异常次数
	 */
	private Map<String, Long> serviceExceptionTimeMap = new HashMap<String, Long>();
	/**
	 * serviceId,成功次数
	 */
	private Map<String, Long> serviceSucessTimeMap = new HashMap<String, Long>();
	private Map<String, String> serviceInfo = new HashMap<String, String>();

	// TODO:单个服务越界怎么办，所有服务之和越界怎么办
	public Long getTotalTimes() {
		return getTotalValue(serviceTimeMap);
	}

	public Long getSucessTimes() {
		return getTotalValue(serviceSucessTimeMap);
	}

	public Long getExceptionTimes() {
		return getTotalValue(serviceExceptionTimeMap);
	}

	private Long getTotalValue(Map<String, Long> map) {
		Long result = new Long(0);
		for (Long value : map.values()) {
			result = value + result;
		}
		return result;
	}

	public boolean contain(String eventId) {
		return serviceInfo.containsKey(eventId);
	}

	public void addExecuteBefore(Event e) {
		String serviceId = getServiceId(e);
		serviceInfo.put(e.getEventId(), serviceId);
		addExecute(serviceId, serviceTimeMap);
	}

	public void addExecuteAfter(Event e) {
		String serviceId = getServiceId(e);
		serviceInfo.remove(e.getEventId());
		addExecute(serviceId, serviceSucessTimeMap);
	}

	public void addExecuteException(Event e, Exception t) {
		String serviceId = getServiceId(e);
		serviceInfo.remove(e.getEventId());
		addExecute(serviceId, serviceExceptionTimeMap);
	}

	private void addExecute(String serviceId, Map<String, Long> map) {

		if (map.containsKey(serviceId)) {
			map.put(serviceId, map.get(serviceId) + 1);
			return;
		}
		Long value = Long.valueOf(1);
		map.put(serviceId, value);
	}

	private String getServiceId(Event e) {
		return e.getServiceRequest().getServiceId();
	}

	public void reset() {
		serviceInfo.clear();
		serviceTimeMap.clear();
		serviceSucessTimeMap.clear();
		serviceExceptionTimeMap.clear();
	}
}

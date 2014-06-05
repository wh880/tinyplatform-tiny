package org.tinygroup.cepcorepc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;

public class DataUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2436281054217215856L;
	int i = 0;
	Map<String, ServiceInfo> services = new HashMap<String, ServiceInfo>();

	public  int plus() {
		return ++i;
	}

	public void addServices(Map<String, ServiceInfo> map) {
		int oldSize = services.size();
		int addSize = map.size();

		services.putAll(map);
		if (services.size() == (oldSize + addSize)) {
			System.out.println("============== 没有重复的 ===================");
		} else {
			System.out.println("============== 有重复的 ===================");
		}
		System.out.println("======== 之前长度:" + oldSize + "新增长度:" + addSize
				+ "之后长度:" + services.size() + " ===================");
	}

	public Map<String, ServiceInfo> getServices() {
		return services;
	}
}

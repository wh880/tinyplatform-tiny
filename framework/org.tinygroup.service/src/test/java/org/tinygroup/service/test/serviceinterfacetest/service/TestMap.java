package org.tinygroup.service.test.serviceinterfacetest.service;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.service.ServiceInterface;

public class TestMap implements ServiceInterface {

	public String getServiceId() {
		return "TestMap";
	}

	public String getCategory() {
		return "TestMap";
	}

	public String getResultKey() {
		return "result";
	}
	
	public Map<String,String>  execute(Map<String,String> map,String name,String value){
		Map<String,String> newMap=new HashMap<String, String>();
		newMap.putAll(map);
		newMap.put(name, value);
		return newMap;
	}

}

package org.tinygroup.service.test.serviceinterfacetest.service;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.service.ServiceInterface;

public class TestList implements ServiceInterface {

	public String getServiceId() {
		return "TestList";
	}

	public String getCategory() {
		return "TestList";
	}

	public String getResultKey() {
		return "result";
	}
	
	public List<String>  execute(List<String> list,String name){
		List<String> newList=new ArrayList<String>();
		newList.addAll(list);
		newList.add(name);
		return newList;
	}

}

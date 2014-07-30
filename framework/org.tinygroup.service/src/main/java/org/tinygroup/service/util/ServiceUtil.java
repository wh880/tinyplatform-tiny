package org.tinygroup.service.util;

import java.io.Serializable;
import java.util.Map;

import org.tinygroup.service.registry.ServiceRegistryItem;

public class ServiceUtil {

	public static boolean assignFromSerializable(Class<?> clazz){
		boolean isPrimitive=clazz.isPrimitive();
		if(isPrimitive){
			return true;
		}
		boolean isInterface=clazz.isInterface();
		if(isInterface){
			return true;
		}
		boolean isMapTypes=Map.class.isAssignableFrom(clazz);
		if(isMapTypes){
			return true;
		}
		boolean seriaType=Serializable.class.isAssignableFrom(clazz);
		if(seriaType){
			return true;
		}
		return false;
	}
	
	public static ServiceRegistryItem copyServiceItem(ServiceRegistryItem serviceiItem) {
		ServiceRegistryItem item = new ServiceRegistryItem();
		item.setCacheable(serviceiItem.isCacheable());
		item.setCategory(serviceiItem.getCategory());
		item.setDescription(serviceiItem.getDescription());
		item.setLocalName(serviceiItem.getLocalName());
		item.setParameters(serviceiItem.getParameters());
		item.setResults(serviceiItem.getResults());
		item.setService(serviceiItem.getService());
		return item;
	}
	
	
}

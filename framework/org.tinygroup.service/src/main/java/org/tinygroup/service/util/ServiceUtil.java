package org.tinygroup.service.util;

import java.io.Serializable;
import java.util.Map;

public class ServiceUtil {

	public static boolean assignFromSerializable(Class<?> clazz){
		boolean isPrimitive=clazz.isPrimitive();
		if(isPrimitive){
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
	
	
}

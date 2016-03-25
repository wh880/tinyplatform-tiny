package org.tinygroup.event;

import java.util.List;

public abstract class AbstractServiceInfo implements ServiceInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5685351914096465696L;

	public int compareTo(ServiceInfo o) {
		int base = o.getServiceId().compareTo(getServiceId());
		if (base != 0) {
			return base;
		}
		
		int paramBase = compareParameters(o.getParameters(), getParameters());
		if(paramBase!=0){
			return paramBase;
		}
		int resultBase = compareParameters(o.getResults(),getResults());
		return resultBase;
		
	}

	private int compareParameters(List<Parameter> oParameters,
			List<Parameter> thisParameters) {
		int lengthBase = oParameters.size() - thisParameters.size();
		if (lengthBase != 0) {
			return lengthBase;
		}
		
		for(int i = 0 ; i < oParameters.size() ; i ++){
			Parameter op = oParameters.get(i);
			Parameter p = thisParameters.get(i);
			int paramBase = op.compareTo(p);
			if(paramBase!=0){
				return paramBase;
			}
		}
		return 0;
	}
}

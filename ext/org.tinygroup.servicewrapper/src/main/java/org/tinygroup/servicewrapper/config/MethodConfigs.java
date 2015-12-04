package org.tinygroup.servicewrapper.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("method-configs")
public class MethodConfigs {
	@XStreamImplicit
	private List<MethodConfig> methodConfigs;

	public List<MethodConfig> getMethodConfigs() {
		if(methodConfigs==null){
			methodConfigs=new ArrayList<MethodConfig>();
		}
		return methodConfigs;
	}

	public void setMethodConfigs(List<MethodConfig> methodConfigs) {
		this.methodConfigs = methodConfigs;
	}

}

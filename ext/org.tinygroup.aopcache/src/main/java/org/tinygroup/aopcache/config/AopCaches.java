package org.tinygroup.aopcache.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("aop-caches")
public class AopCaches {
    @XStreamImplicit
	private List<AopCache> cacheConfigs;

	public List<AopCache> getCacheConfigs() {
		if(cacheConfigs==null){
			cacheConfigs=new ArrayList<AopCache>();
		}
		return cacheConfigs;
	}

	public void setCacheConfigs(List<AopCache> cacheConfigs) {
		this.cacheConfigs = cacheConfigs;
	}
    
}

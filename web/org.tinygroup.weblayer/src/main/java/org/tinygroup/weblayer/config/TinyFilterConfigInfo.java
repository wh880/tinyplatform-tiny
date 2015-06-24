package org.tinygroup.weblayer.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("tiny-filter")
public class TinyFilterConfigInfo extends BasicConfigInfo {
  
    @XStreamImplicit
    private List<FilterMapping> filterMappings;
    
	public List<FilterMapping> getFilterMappings() {
		if(filterMappings==null){
			filterMappings=new ArrayList<FilterMapping>();
		}
		return filterMappings;
	}

	public void setFilterMappings(List<FilterMapping> filterMappings) {
		this.filterMappings = filterMappings;
	}
	
	public void combine(TinyFilterConfigInfo configInfo){
		getParameterMap().putAll(configInfo.getParameterMap());
		getFilterMappings().addAll(configInfo.getFilterMappings());
	}
    
}
